package com.tes.weatherapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastModel (
    val forecastday: List<ForecastdayModel>
): Parcelable
