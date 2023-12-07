plugins {
    id("sb.park.application")
}

android {
    namespace = "sb.park.bus"
}

dependencies {
    implementation(projects.feature.main)
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.google.material)
    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.espresso)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.jake.timber)
}
