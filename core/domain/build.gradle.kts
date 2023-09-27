plugins {
    id("sb.park.core")
}

android {
    namespace = "sb.park.bus.domain"
}
dependencies{
    implementation(projects.core.data)
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.compose.activity)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.compose)
    implementation(platform(libs.compose.platform))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.preview)
    implementation(libs.androidx.material)
    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.espresso)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}