plugins {
    id("sb.park.feature")
}

android {
    namespace = "sb.park.bus.feature.main"
}


dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(libs.androidx.core)
    implementation(libs.compose.activity)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.compose)
    implementation(platform(libs.compose.platform))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.preview)
    implementation(libs.androidx.material)
    implementation(libs.hilt.navigation)
    implementation(libs.androidx.navigation)
    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.espresso)
    androidTestImplementation(platform(libs.compose.platform))
    androidTestImplementation(libs.compose.test.ui)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.test.manifest)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}