plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("AndroidApplication") {
            id = "sb.park.application"
            implementationClass = "sb.park.bus.build_logic.ApplicationPlugin"
        }
        register("AndroidCore") {
            id = "sb.park.core"
            implementationClass = "sb.park.bus.build_logic.CorePlugin"
        }
        register("AndroidFeature") {
            id = "sb.park.feature"
            implementationClass = "sb.park.bus.build_logic.FeaturePlugin"
        }
    }
}

