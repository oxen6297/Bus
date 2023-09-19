plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("AndroidApplication") {
            id = "sb.park.application"
            implementationClass = "sb.park.bus.build_logic.ApplicationConventionPlugin"
        }
        register("AndroidData") {
            id = "sb.park.data"
            implementationClass = "sb.park.bus.build_logic.DataConventionPlugin"
        }
        register("AndroidFeatureMain") {
            id = "sb.park.feature.main"
            implementationClass = "sb.park.bus.build_logic.FeatureMainConventionPlugin"
        }
    }
}

