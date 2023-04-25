package com.example.yandex_maps.ui

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class App : Application() {

    private val MAPKIT_API_KEY = "05ee083d-246d-421d-9956-0a76757d842a"

    override fun onCreate() {
        super.onCreate()

        MapKitFactory.setApiKey(MAPKIT_API_KEY)
    }

}
