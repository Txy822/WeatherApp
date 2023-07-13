package com.tes.weatherapp.data.remote.londonweather.dto

import com.tes.weatherapp.domain.model.ForecastModel

data class Forecast(
    val forecastday: List<Forecastday>
)

