package com.example.stopwatchapp

import android.os.CountDownTimer
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {
    var minutes = mutableIntStateOf(0)
    var seconds = mutableIntStateOf(0)
    var milliseconds = mutableIntStateOf(0)
    private var timer: CountDownTimer? = null
    private var isRunning = false

    fun startTimer() {
        if (isRunning) return
        isRunning = true
        timer = object : CountDownTimer(Long.MAX_VALUE, 10) {
            override fun onTick(millisUntilFinished: Long) {
                milliseconds.intValue++
                if (milliseconds.intValue == 100) {
                    milliseconds.intValue = 0
                    seconds.intValue++
                }
                if (seconds.intValue == 60) {
                    seconds.intValue = 0
                    minutes.intValue++
                }
            }

            override fun onFinish() {
                // Not used in this case, as we are running indefinitely
            }
        }
        timer?.start()
    }

    fun pauseTimer() {
        timer?.cancel()
        isRunning = false
    }

    fun resetTimer() {
        timer?.cancel()
        isRunning = false
        minutes.intValue = 0
        seconds.intValue = 0
        milliseconds.intValue = 0
    }
}
