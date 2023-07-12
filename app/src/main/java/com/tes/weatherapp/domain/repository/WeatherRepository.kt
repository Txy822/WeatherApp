package com.tes.weatherapp.domain.repository

import com.tes.weatherapp.data.remote.londonweather.dto.LondonWeatherResponse
import com.tes.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

   suspend fun getWeatherData(): Flow<Resource<LondonWeatherResponse>>
}