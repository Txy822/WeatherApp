package com.tes.weatherapp.domain.repository

import com.tes.weatherapp.data.remote.londonweather.dto.LondonWeatherResponse

interface WeatherRepository {

   suspend fun getWeatherData(): LondonWeatherResponse
}