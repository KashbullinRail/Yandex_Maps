package com.example.yandex_maps.ui

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class App : Application() {

    //    private val MAPKIT_API_KEY = "05ee083d-246d-421d-9956-0a76757d842a"
//    val MAPKIT_API_KEY = "ea38fe74-ec10-4b11-a4a3-7eba140bfcc5"   // Коммерческий ключ
    val MAPKIT_API_KEY = "bba4f318-e183-4f6e-8124-c335f1abca8a"   // Коммерческий ключ

    override fun onCreate() {
        super.onCreate()

        MapKitFactory.setApiKey(MAPKIT_API_KEY)
    }

}
