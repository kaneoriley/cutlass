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

package me.oriley.cutlass

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException

class CutlassPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def variants = null;
        def plugin = project.plugins.findPlugin("android");
        if (plugin != null) {
            variants = "applicationVariants";
        } else {
            plugin = project.plugins.findPlugin("android-library");
            if (plugin != null) {
                variants = "libraryVariants";
            }
        }

        if (variants == null) {
            throw new ProjectConfigurationException("android or android-library plugin must be applied", null)
        }

        project.apply plugin: 'com.neenbedankt.android-apt'

        project.android {
            packagingOptions {
                exclude 'META-INF/services/javax.annotation.processing.Processor'
            }
        }
    }
}