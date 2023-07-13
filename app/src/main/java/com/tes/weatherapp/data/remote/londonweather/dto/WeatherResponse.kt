package com.tes.weatherapp.data.remote.londonweather.dto

import com.tes.weatherapp.domain.model.WeatherResponseModel

data class WeatherResponse(
    val alerts: Alerts,
    val current: Current,
    val forecast: Forecast,
    val location: Location
)
