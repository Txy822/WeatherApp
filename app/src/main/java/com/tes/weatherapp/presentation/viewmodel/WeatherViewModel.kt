package com.tes.weatherapp.presentation.viewmodel

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tes.weatherapp.data.remote.londonweather.dto.Condition
import com.tes.weatherapp.data.remote.londonweather.dto.Current
import com.tes.weatherapp.data.remote.londonweather.dto.Forecast
import com.tes.weatherapp.domain.usecase.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {

    private val _condition = MutableStateFlow(Condition(0, "", ""))
    val condition: StateFlow<Condition> = _condition.asStateFlow()

    private val _forcast = MutableStateFlow(Forecast(emptyList()))
    val forcast: StateFlow<Forecast> = _forcast.asStateFlow()

    private val _current = MutableStateFlow(Current(null, null,null, null,null, null,null, null,null, null,null, null,null, null,null, null,null, null,null, null,null, null,null, null))
    val current: StateFlow<Current> = _current.asStateFlow()

    private var timer: Timer? = null


    init {
        getWeatherData()
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            //_condition.value = weatherUseCase.getWeatherResponseUseCase().current.condition
            _forcast.value = weatherUseCase.getWeatherResponseUseCase().forecast
            _current.value = weatherUseCase.getWeatherResponseUseCase().current
        }
    }

    fun startPolling(interval: Long) {
        stopPolling() // Stop any existing polling before starting

        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                // Perform the API call
                viewModelScope.launch {
                    //val response = weatherUseCase.getWeatherForecast()
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