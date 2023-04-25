package com.example.yandex_maps.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yandex_maps.ui.theme.ViewState
import com.yandex.mapkit.mapview.MapView

class MainViewModel : ViewModel() {

    private var _reset = MutableLiveData<ViewState>()
    val reset: LiveData<ViewState> = _reset

    init {
        _reset.value = ViewState(false, false)
    }

    fun setReset(value: ViewState) {
        _reset.value = value
    }

}