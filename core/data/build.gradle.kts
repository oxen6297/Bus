import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("sb.park.core")
}

android {
    namespace = "sb.park.bus.core.data"
}

dependencies{
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
    androidTestImplementation(platform(libs.compose.platform))
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.test.manifest)
    implementation(libs.retrofit.android)
    implementation(libs.retrofit.gson)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
