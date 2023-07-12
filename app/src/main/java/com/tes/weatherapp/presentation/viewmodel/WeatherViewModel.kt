package com.tes.weatherapp.presentation.viewmodel

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tes.weatherapp.data.remote.londonweather.dto.Condition
import com.tes.weatherapp.data.remote.londonweather.dto.Current
import com.tes.weatherapp.data.remote.londonweather.dto.Forecast
import com.tes.weatherapp.domain.usecase.WeatherUseCase
import com.tes.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error.asStateFlow()

    private var timer: Timer? = null


    init {
        getWeatherData()
        //startPolling(1000)
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            weatherUseCase.getWeatherResponseUseCase().collect { result ->
                when(result){
                    is Resource.Success ->{
                        result.data?.let {
                            _loading.value = false
                            _forcast.value = it.forecast
                            _current.value =it.current

                        }
                    }
                    is Resource.Loading -> {
                        _loading.value = true
                    }
                    is Resource.Error -> {
                        _loading.value = false
                        _error.value = "Error Occurred"

                    }
                }
            }
            //_condition.value = weatherUseCase.getWeatherResponseUseCase().current.condition
           // _forcast.value = weatherUseCase.getWeatherResponseUseCase().forecast
           // _current.value = weatherUseCase.getWeatherResponseUseCase().current
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