package com.tes.weatherapp.data.remote.londonweather.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecastday(
    val astro: Astro,
    val date: String,
    val date_epoch: Int,
    val day: Day,
    val hour: List<Hour>
): Parcelable
