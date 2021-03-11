package com.example.movieappcompose

import android.app.Application
import com.example.movieappcompose.utlis.WifiService
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        WifiService.instance.initializeWithApplicationContext(applicationContext)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}