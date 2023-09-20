package sb.park.bus.build_logic

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import sb.park.bus.build_logic.project.configureKotlinAndroid

internal class ApplicationPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("dagger.hilt.android.plugin")
                apply("kotlin-kapt")
            }
            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = "sb.park.bus"
                    versionCode = 1
                    versionName = "1.0"
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 33
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