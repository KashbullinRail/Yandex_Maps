package com.example.yandex_maps.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    private var _reset = MutableLiveData<Boolean?>()
    val reset: LiveData<Boolean?> = _reset

    fun setMsg(teamA: Int, teamB: Int) {
        _msg.value = if (teamA == teamB) {
            "Tie !"
        } else if (teamA > teamB) {
            "Team A won!"
        } else {
            "Team B won!"
        }
    }

    fun setReset(value: Boolean?) {
        _reset.value = value
    }
}