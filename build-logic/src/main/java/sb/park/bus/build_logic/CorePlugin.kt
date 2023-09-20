package sb.park.bus.build_logic

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import sb.park.bus.build_logic.project.configureKotlinAndroid

internal class CorePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                configureKotlinAndroid(this)
                buildFeatures {
                    compose = true
                }
                composeOptions {
                    kotlinCompilerExtensionVersion = "1.4.3"
                }
                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }
        }
    }
}