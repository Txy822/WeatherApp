package com.tes.weatherapp.domain.repository

import com.tes.weatherapp.domain.model.WeatherResponseModel
import com.tes.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

   suspend fun getWeatherData(): Flow<Resource<WeatherResponseModel>>
}