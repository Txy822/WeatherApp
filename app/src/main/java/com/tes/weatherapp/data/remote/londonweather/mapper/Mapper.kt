package com.tes.weatherapp.data.remote.londonweather.mapper

import com.tes.weatherapp.data.remote.londonweather.dto.Current
import com.tes.weatherapp.data.remote.londonweather.dto.Forecast
import com.tes.weatherapp.data.remote.londonweather.dto.Forecastday
import com.tes.weatherapp.data.remote.londonweather.dto.WeatherResponse
import com.tes.weatherapp.domain.model.CurrentModel
import com.tes.weatherapp.domain.model.ForecastModel
import com.tes.weatherapp.domain.model.ForecastdayModel
import com.tes.weatherapp.domain.model.WeatherResponseModel

    fun Current.toCurrentModel(): CurrentModel = CurrentModel(
        cloud = cloud,
        condition = condition,
        feelslike_c = feelslike_c,
        humidity = humidity,
        last_updated = last_updated,
        temp_c = temp_c
    )

    fun Forecast.toForecastModel(): ForecastModel = ForecastModel(
        forecastday = forecastday.map { it.toForecastdayModel()}
    )

    fun Forecastday.toForecastdayModel(): ForecastdayModel = ForecastdayModel(
        date = date,
        day =day,
        hour = hour
    )

    fun WeatherResponse.toWeatherResponseModel(): WeatherResponseModel = WeatherResponseModel(
        current = current.toCurrentModel(),
        forecast = forecast.toForecastModel(),
    )

