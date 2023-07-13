package com.tes.weatherapp.domain.model

import com.tes.weatherapp.data.remote.londonweather.dto.Current
import com.tes.weatherapp.data.remote.londonweather.dto.Forecast
import com.tes.weatherapp.data.remote.londonweather.dto.Location

data class WeatherResponseModel(
    val current: CurrentModel,
    val forecast: ForecastModel,
)