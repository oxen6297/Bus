plugins {
    id("sb.park.feature")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "sb.park.bus.feature.main"
}


dependencies {
    implementation(projects.core.data)
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.google.material)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.databinding)
    implementation(libs.bumptech.glide)
    implementation(libs.jake.timber)
    implementation(libs.google.material)
    implementation(libs.retrofit.gson)
    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.espresso)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}