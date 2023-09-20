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
    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.espresso)
}
