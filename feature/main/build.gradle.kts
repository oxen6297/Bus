plugins {
    id("sb.park.feature")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "sb.park.bus.feature.main"
}


dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.google.material)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.databinding)
    implementation(libs.bumptech.glide)
    implementation(libs.jake.timber)
    implementation(libs.retrofit.gson)
    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.espresso)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.github.chart)
    implementation(libs.androidx.datastore)
    implementation(libs.lottie)
    implementation(libs.gms.location)
    implementation(libs.naver.map)
}