package com.batzalcancia.typicode

import android.app.Application
import com.batzalcancia.core.helpers.typicodeContext
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TypicodeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        typicodeContext = this.applicationContext
    }
}
