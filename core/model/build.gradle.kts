
plugins {
    id("sb.park.core")
}

android {
    namespace = "sb.park.bus.core.model"
}

dependencies {
    implementation(libs.retrofit.gson)
}
