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

import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

@SuppressWarnings("unused")
@Singleton
public final class Injector<T> {

    @NonNull
    private final String mTag;

    @NonNull
    private final T mComponent;

    @NonNull
    private final Class<T> mClass;

    @NonNull
    private final Map<Class, Method> mMethodCache = new HashMap<>();

    public Injector(@NonNull T component, @NonNull Class<T> componentClass) {
        mComponent = component;
        mClass = componentClass;
        mTag = Injector.class.getSimpleName() + "<" + componentClass.getSimpleName() + ">";
        buildCache();
    }

    private void buildCache() {
        mMethodCache.clear();
        for (Method m : mClass.getDeclaredMethods()) {
            Class[] types = m.getParameterTypes();
            if (types.length == 1) {
                m.setAccessible(true);
                mMethodCache.put(types[0], m);
            }
        }
    }

    public void inject(@NonNull Object object) {
        Class currentClass = object.getClass();
        // TODO: Abort early for known base classes?
        while (currentClass != null) {
            Method m = mMethodCache.get(currentClass);
            if (m != null) {
                try {
                    Log.d(mTag, "injection target found for " + currentClass.toString());
                    m.invoke(mComponent, object);
                } catch (Exception e) {
                    Log.e(mTag, "error performing injection for " + object.toString());
                    e.printStackTrace();
                }
                break;
            } else {
                currentClass = currentClass.getSuperclass();
            }
        }
    }
}
