package com.tes.weatherapp.data.remote.londonweather.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Condition(
    val code: Int,
    val icon: String,
    val text: String
): Parcelable