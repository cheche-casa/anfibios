package com.example.anfibios

import android.app.Application
import com.example.anfibios.data.AppContainer
import com.example.anfibios.data.DefaultAppContainer

class AnfibiosApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}

//Hai que engadir ó manifiesto
//android:name=".AnfibiosApplication"
