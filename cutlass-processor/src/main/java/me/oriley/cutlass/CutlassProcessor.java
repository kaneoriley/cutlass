/*
 * Copyright (C) 2016 Kane O'Riley
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package me.oriley.cutlass;

import com.google.auto.common.SuperficialValidation;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

import static javax.tools.Diagnostic.Kind.ERROR;

@AutoService(Processor.class)
public final class CutlassProcessor extends AbstractProcessor {

    private static final String COMPONENT_NAME_TEMPLATE = "%s_GeneratedComponent";
    private static final String METHOD_NAME = "inject";
    private static final String PARAMETER_NAME = "target";
    private static final String COMMENT = "Generated code from CutlassProcessor.\nDo not modify!";

    private static final ClassName TARGET_ANNOTATION = ClassName.get("dagger", "Component");

    @Nonnull
    private Elements mElementUtils;

    @Nonnull
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        mElementUtils = env.getElementUtils();
        mFiler = env.getFiler();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Inject.class.getCanonicalName());
        types.add(CutlassComponent.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> elements, RoundEnvironment env) {
        boolean processed = false;

        for (Element element : env.getElementsAnnotatedWith(CutlassComponent.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            if (processed) {
                // TODO: Support multiple annotations
                error(element, "Only one @CutlassComponent annotation supported per project");
                break;
            }

            processed = true;
            TypeElement typeElement = (TypeElement) element;
            String componentPackage = getPackageName(typeElement);
            String componentName = String.format(COMPONENT_NAME_TEMPLATE,
                    getClassName(typeElement, componentPackage));

            AnnotationSpec componentAnnotation = null;
            AnnotationSpec scopeAnnotation = null;

            // TODO: Support all scope annotations
            for (AnnotationMirror mirror : element.getAnnotationMirrors()) {
                if (isAnnotation(mirror, CutlassComponent.class)) {
                    componentAnnotation = clone(mirror, TARGET_ANNOTATION);
                } else if (isAnnotation(mirror, Singleton.class)) {
                    scopeAnnotation = AnnotationSpec.get(mirror);
                }
            }

            Map<Name, ClassName> injectedClasses = new HashMap<>();
            for (Element injectElement : env.getElementsAnnotatedWith(Inject.class)) {
                if (!SuperficialValidation.validateElement(injectElement)) continue;

                if (injectElement.getKind() == ElementKind.FIELD) {
                    TypeElement enclosingElement = (TypeElement) injectElement.getEnclosingElement();
                    String packageName = getPackageName(enclosingElement);
                    String className = getClassName(enclosingElement, packageName);

                    Name key = enclosingElement.getQualifiedName();
                    if (!injectedClasses.containsKey(key)) {
                        injectedClasses.put(key, ClassName.get(packageName, className));
                    }
                }
            }

            if (componentAnnotation != null && !injectedClasses.isEmpty()) {
                try {
                    brewJava(componentPackage, componentName, injectedClasses, componentAnnotation,
                            scopeAnnotation).writeTo(mFiler);
                } catch (IOException e) {
                    error(element, "Failed to write file: %s.%s", componentPackage, componentName);
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    @Nonnull
    private String getPackageName(@Nonnull TypeElement type) {
        return mElementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    @Nonnull
    private String getClassName(@Nonnull TypeElement type, @Nonnull String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    @Nonnull
    private JavaFile brewJava(@Nonnull String packageName,
                              @Nonnull String componentName,
                              @Nonnull Map<Name, ClassName> injectedClasses,
                              @Nonnull AnnotationSpec componentAnnotation,
                              @Nullable AnnotationSpec scopeAnnotation) {

        TypeSpec.Builder builder = TypeSpec.interfaceBuilder(componentName)
                .addModifiers(Modifier.PUBLIC);
        if (scopeAnnotation != null) {
            builder.addAnnotation(scopeAnnotation);
        }
        builder.addAnnotation(componentAnnotation);

        for (Map.Entry<Name, ClassName> entry : injectedClasses.entrySet()) {
            builder.addMethod(MethodSpec.methodBuilder(METHOD_NAME)
                    .addParameter(ParameterSpec.builder(entry.getValue(), PARAMETER_NAME).build())
                    .addModifiers(Modifier.ABSTRACT)
                    .addModifiers(Modifier.PUBLIC)
                    .build());
        }

        return JavaFile.builder(packageName, builder.build())
                .addFileComment(COMMENT).build();
    }

    private static boolean isAnnotation(@Nonnull AnnotationMirror mirror, @Nonnull Class annotationClass) {
        String annotationName = mirror.getAnnotationType().asElement().getSimpleName().toString();
        return annotationClass.getSimpleName().equals(annotationName);
    }

    @Nonnull
    public static AnnotationSpec clone(@Nonnull AnnotationMirror annotation,@Nonnull ClassName targetClass) {
        AnnotationSpec.Builder builder = AnnotationSpec.builder(targetClass);
        Visitor visitor = new Visitor(builder);
        for (ExecutableElement executableElement : annotation.getElementValues().keySet()) {
            String name = executableElement.getSimpleName().toString();
            AnnotationValue value = annotation.getElementValues().get(executableElement);
            value.accept(visitor, new Entry(name, value));
        }
        return builder.build();
    }

    private void error(@Nonnull Element element, @Nonnull String message, @Nullable Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(ERROR, message, element);
    }

    private static final class Entry {

        @Nonnull
        private final String name;

        @Nonnull
        private final AnnotationValue value;

        Entry(@Nonnull String name, @Nonnull AnnotationValue value) {
            this.name = name;
            this.value = value;
        }
    }

    private static final class Visitor extends SimpleAnnotationValueVisitor7<AnnotationSpec.Builder, Entry> {

        @Nonnull
        private final AnnotationSpec.Builder mBuilder;

        Visitor(@Nonnull AnnotationSpec.Builder builder) {
            super(builder);
            mBuilder = builder;
        }

        @Override
        @Nonnull
        protected AnnotationSpec.Builder defaultAction(@Nonnull Object o, @Nonnull Entry entry) {
            return mBuilder.addMember(entry.name, "$L", entry.value);
        }

        @Override
        @Nonnull
        public AnnotationSpec.Builder visitAnnotation(@Nonnull AnnotationMirror a, @Nonnull Entry entry) {
            return mBuilder.addMember(entry.name, "$L", AnnotationSpec.get(a));
        }

        @Override
        @Nonnull
        public AnnotationSpec.Builder visitEnumConstant(@Nonnull VariableElement c, @Nonnull Entry entry) {
            return mBuilder.addMember(entry.name, "$T.$L", c.asType(), c.getSimpleName());
        }

        @Override
        @Nonnull
        public AnnotationSpec.Builder visitType(@Nonnull TypeMirror t, @Nonnull Entry entry) {
            return mBuilder.addMember(entry.name, "$T.class", t);
        }

        @Override
        @Nonnull
        public AnnotationSpec.Builder visitArray(@Nonnull List<? extends AnnotationValue> values,
                                                 @Nonnull Entry entry) {
            for (AnnotationValue value : values) {
                value.accept(this, new Entry(entry.name, value));
            }
            return mBuilder;
        }
    }
}
