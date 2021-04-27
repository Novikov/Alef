package com.app.alef

import android.app.Application
import com.app.alef.di.AppComponent
import com.app.alef.di.DaggerAppComponent

class BaseApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create()
    }
}