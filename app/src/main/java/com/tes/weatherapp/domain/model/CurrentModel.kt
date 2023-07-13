package com.tes.weatherapp.domain.model

import android.os.Parcelable
import com.tes.weatherapp.data.remote.londonweather.dto.Condition
import kotlinx.android.parcel.Parcelize

@Parcelize
class CurrentModel (
    val cloud: Int?,
    val condition: Condition?,
    val feelslike_c: Double?,
    val humidity: Int?,
    val last_updated: String?,
    val temp_c: Double?
): Parcelable