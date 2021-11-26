package me.cpele.cptmr.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    data class Time(val hour: Long, val minute: Long)

    private val targetTimeMsFlow = MutableStateFlow<Long?>(null)

    private val timerStartedFlow = MutableStateFlow(false)

    private val runningTimeFlow = flow {
        while (true) {
            emit(System.currentTimeMillis())
            delay(1000)
        }
    }

    private val timeFlow =
        timerStartedFlow.filter { isStarted -> isStarted } // Don't emit unless started
            .combine(targetTimeMsFlow) { _, targetTimeMs -> targetTimeMs } // Keep target time
            .filterNotNull()
            .combine(runningTimeFlow) {
                /** Compute [Time] to display */
                    targetTimeMs, currentTimeMs ->
                val remainingTimeMs = targetTimeMs - currentTimeMs
                val hour = TimeUnit.MILLISECONDS.toHours(remainingTimeMs)
                val hourMs = TimeUnit.HOURS.toMillis(hour)
                val minute = TimeUnit.MILLISECONDS.toMinutes(remainingTimeMs - hourMs)
                Time(hour, minute).also {
                    Log.d(javaClass.simpleName, "Remaining time: $it")
                }
            }

    val timeLive: LiveData<Time> = timeFlow.asLiveData()

    fun startTimer() {
        timerStartedFlow.value = true
    }

    fun changeTime(hourOfDay: Int, minute: Int) {
        val now = System.currentTimeMillis()
        val minLong = minute.toLong()
        val hrLong = hourOfDay.toLong()
        val minMs = TimeUnit.MINUTES.toMillis(minLong)
        val hrMs = TimeUnit.HOURS.toMillis(hrLong)
        targetTimeMsFlow.value = now + minMs + hrMs
    }
}