plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlinx-serialization")
    id("com.batzalcancia.typicode.library")
}

android {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project("::core"))
}
