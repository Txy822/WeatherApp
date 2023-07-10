package com.tes.weatherapp.data.remote.londonweather.dto

data class AirQuality(
    val co: Double,
    val gb-defra-index: Int,
    val no2: Double,
    val o3: Double,
    val pm10: Double,
    val pm2_5: Double,
    val so2: Double,
    val us-epa-index: Int
)