package com.tes.weatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tes.weatherapp.domain.model.CurrentModel
import com.tes.weatherapp.domain.model.ForecastModel
import com.tes.weatherapp.domain.usecase.WeatherUseCase
import com.tes.weatherapp.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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


    private val _forecast = MutableStateFlow(ForecastModel(emptyList()))
    val forecast: StateFlow<ForecastModel> = _forecast.asStateFlow()

    private val _current = MutableStateFlow(CurrentModel(null, null,null, null,null, null))
    val current: StateFlow<CurrentModel> = _current.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error.asStateFlow()

    private var timer: Timer? = null


    init {
        getWeatherData()
        //startPolling(1000)
    }

    fun getWeatherData() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherUseCase.getWeatherResponseUseCase().collect { result ->
                when(result){
                    is Resource.Success ->{
                        result.data?.let {
                            _loading.value = false
                            _forecast.value = it.forecast
                            _current.value =it.current
                            _error.value = ""

                        }
                    }
                    is Resource.Loading -> {
                        _loading.value = true
                        _error.value = ""
                    }
                    is Resource.Error -> {
                        _loading.value = false
                        _error.value = "Error Occurred"
                    }
                }
            }
        }
    }

    fun startPolling(interval: Long) {
        stopPolling() // Stop any existing polling before starting

        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                getWeatherData()
                // Perform the API call
               // viewModelScope.launch {
                    //val response = weatherUseCase.getWeatherForecast()
                    // Process the API response and handle errors
               // }
            }
        }, 0, interval)
    }

    fun stopPolling() {
        timer?.cancel()
        timer = null
    }
}