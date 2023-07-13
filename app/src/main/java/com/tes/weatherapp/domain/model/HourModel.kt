package com.tes.weatherapp.domain.model

import android.os.Parcelable
import com.tes.weatherapp.data.remote.londonweather.dto.AirQuality
import com.tes.weatherapp.data.remote.londonweather.dto.Condition
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HourModel (
    val air_quality: AirQuality,
    val chance_of_rain: Int,
    val cloud: Int,
    val condition: Condition,
    val feelslike_c: Double,
    val humidity: Int,
    val temp_c: Double,
    val time: String,
): Parcelable
