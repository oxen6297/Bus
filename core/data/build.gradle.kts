
plugins {
    id("sb.park.core")
}

android {
    namespace = "sb.park.bus.core.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.jake.timber)
    implementation(libs.retrofit.logging)
    implementation(libs.androidx.lifecycle.runtime)
    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.espresso)
    implementation(libs.retrofit.android)
    implementation(libs.retrofit.gson)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.room.android)
    implementation(libs.room.ktx)
    kapt(libs.room.copmiler)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlin.serialization)
}
