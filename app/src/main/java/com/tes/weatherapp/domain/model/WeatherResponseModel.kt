package com.tes.weatherapp.domain.model

data class WeatherResponseModel(
    val current: CurrentModel,
    val forecast: ForecastModel,
)