package com.tes.weatherapp.data.remote.londonweather.dto

data class LondonWeatherResponse(
    val alerts: Alerts,
    val current: Current,
    val forecast: Forecast,
    val location: Location
)