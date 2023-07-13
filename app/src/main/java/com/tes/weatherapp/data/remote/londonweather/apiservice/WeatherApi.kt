package com.tes.weatherapp.data.remote.londonweather.apiservice

import com.tes.weatherapp.BuildConfig
import com.tes.weatherapp.data.remote.londonweather.dto.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(END)
    suspend fun getWeatherResponse(
        @Query("key") api_key: String = BuildConfig.api_key,
        @Query("q") q: String = "London",
        @Query("days") days: String = "7",
        @Query("aqi") aqi: String = "yes",
        @Query("alerts") alerts: String = "yes"
    ): WeatherResponse

    companion object {
        const val BASE_URL = "https://api.weatherapi.com/"
        const val END = "v1/forecast.json"
    }
}