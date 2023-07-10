package com.tes.weatherapp.presentation.viewmodel

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tes.weatherapp.domain.usecase.WeatherUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {

    private var timer: Timer? = null

    fun startPolling(interval: Long) {
        stopPolling() // Stop any existing polling before starting

        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                // Perform the API call
                viewModelScope.launch {
                    //val response = weatherAPI.getWeatherForecast()
                    // Process the API response and handle errors
                }
            }
        }, 0, interval)
    }

    fun stopPolling() {
        timer?.cancel()
        timer = null
    }
}