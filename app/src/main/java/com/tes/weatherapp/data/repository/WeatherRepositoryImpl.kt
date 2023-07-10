package com.tes.weatherapp.data.repository

import android.graphics.ColorSpace
import androidx.lifecycle.LifecycleCoroutineScope
import com.tes.weatherapp.data.remote.londonweather.apiservice.WeatherApi
import com.tes.weatherapp.data.remote.londonweather.dto.LondonWeatherResponse
import com.tes.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {

    override suspend fun getWeatherData(): LondonWeatherResponse {

        return api.getWeatherResponse()

    }


}