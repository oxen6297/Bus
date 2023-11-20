import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("sb.park.core")
}

android {
    namespace = "sb.park.bus.core.data"
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.jake.timber)
    implementation(libs.retrofit.logging)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.google.material)
    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.espresso)
    implementation(libs.retrofit.android)
    implementation(libs.retrofit.gson)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
