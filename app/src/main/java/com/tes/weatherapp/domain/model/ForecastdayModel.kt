package com.tes.weatherapp.domain.model

import android.os.Parcelable
import com.tes.weatherapp.data.remote.londonweather.dto.Day
import com.tes.weatherapp.data.remote.londonweather.dto.Hour
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastdayModel(
    val date: String,
    val day: Day,
    val hour: List<Hour>
): Parcelable