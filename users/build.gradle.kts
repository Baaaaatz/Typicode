import com.batzalcancia.dependencies.Dependencies

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlinx-serialization")
    id("dagger.hilt.android.plugin")
    id("com.batzalcancia.typicode.library")
}

android {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project("::core"))
    implementation(Dependencies.GOOGLE_MAPS)
    implementation(Dependencies.GOOGLE_LOCATION)
}
