plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("com.batzalcancia.typicode.app")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    defaultConfig {
        applicationId = "com.batzalcancia.typicode"
        versionName = "1.0.0"
    }

    productFlavors {
        getByName("dev") {
            dimension = "environment"
            applicationId = "com.batzalcancia.typicode"
            versionCode = 1
            versionNameSuffix = "-dev+1"
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    implementation(project(":android:core"))
    implementation(project(":android:auth"))
    implementation(project(":android:users"))
}

