package sb.park.bus.build_logic

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import sb.park.bus.build_logic.project.configureKotlinAndroid

internal class ApplicationConventionPlugin: Plugin<Project> {
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

                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                dependencies {
                    add("implementation",project(":feature:main"))
                    add("implementation",libs.findLibrary("hilt-android").get())
                    add("kapt",libs.findLibrary("hilt-compiler").get())
                    add("implementation",libs.findLibrary("androidx-core").get())
                    add("implementation",libs.findLibrary("androidx-lifecycle-runtime").get())
                    add("implementation",libs.findLibrary("androidx-lifecycle-compose").get())
                    add("implementation",libs.findLibrary("compose-activity").get())
                    add("implementation",libs.findLibrary("compose-platform").get())
                    add("implementation",libs.findLibrary("compose-preview").get())
                    add("implementation",libs.findLibrary("compose-ui").get())
                    add("implementation",libs.findLibrary("compose-ui-graphics").get())
                    add("implementation",libs.findLibrary("androidx-material").get())
                    add("testImplementation",libs.findLibrary("androidx-junit").get())
                    add("androidTestImplementation",libs.findLibrary("test-junit").get())
                    add("androidTestImplementation",libs.findLibrary("test-espresso").get())
                    add("androidTestImplementation",libs.findLibrary("compose-platform").get())
                    add("androidTestImplementation",libs.findLibrary("compose-test-ui").get())
                    add("debugImplementation",libs.findLibrary("compose-ui-tooling").get())
                    add("debugImplementation",libs.findLibrary("compose-test-manifest").get())
                }
            }
        }
    }
}