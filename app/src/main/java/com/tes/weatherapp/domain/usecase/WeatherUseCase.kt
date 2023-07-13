package com.tes.weatherapp.domain.usecase

import com.tes.weatherapp.data.remote.londonweather.dto.WeatherResponse
import com.tes.weatherapp.domain.model.WeatherResponseModel
import com.tes.weatherapp.domain.repository.WeatherRepository
import com.tes.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
   private val  repository: WeatherRepository
){
    suspend fun  getWeatherResponseUseCase(): Flow<Resource<WeatherResponseModel>> {
        return  repository.getWeatherData()
    }
}