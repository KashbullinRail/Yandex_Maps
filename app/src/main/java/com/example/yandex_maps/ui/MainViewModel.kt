package com.example.yandex_maps.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yandex.mapkit.mapview.MapView

class MainViewModel : ViewModel() {

    private var _reset = MutableLiveData<MapView>()
    val reset: LiveData<MapView> = _reset

    init {

    }
    fun setReset(value: MapView) {
        _reset.value = value
    }

}