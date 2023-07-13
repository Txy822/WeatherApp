package com.tes.weatherapp.data.remote.londonweather.dto

import android.os.Parcelable
import com.tes.weatherapp.domain.model.ForecastdayModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecastday(
    val astro: Astro,
    val date: String,
    val date_epoch: Int,
    val day: Day,
    val hour: List<Hour>
): Parcelable


