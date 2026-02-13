package com.shuham.medilog


import android.app.Application
import com.shuham.medilog.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Koin with Android Context
        initKoin {
            androidLogger()
            androidContext(this@MyApplication)
        }
    }
}