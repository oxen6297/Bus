
plugins {
    id("sb.park.core")
}

android {
    namespace = "sb.park.bus.core.domain"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.data)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.github.chart)
}
