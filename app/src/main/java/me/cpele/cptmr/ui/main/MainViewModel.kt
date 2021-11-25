package me.cpele.cptmr.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    data class Time(val hour: Int, val minute: Int)

    private val _timeLive = MutableLiveData<Time>()
    val timeLive: LiveData<Time> get() = _timeLive

    fun startTimer() {
        // TODO
    }

    fun changeTime(hourOfDay: Int, minute: Int) {
        _timeLive.value = Time(hourOfDay, minute)
    }
}