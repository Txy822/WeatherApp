package com.tes.weatherapp.domain.usecase

import com.tes.weatherapp.data.remote.londonweather.dto.LondonWeatherResponse
import com.tes.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
   private val  repository: WeatherRepository
){

    suspend fun  getWeatherResponseUseCase(): LondonWeatherResponse {
        return  repository.getWeatherData()
    }
}