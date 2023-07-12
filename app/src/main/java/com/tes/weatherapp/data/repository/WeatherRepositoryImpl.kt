package com.tes.weatherapp.data.repository

import android.graphics.ColorSpace
import androidx.lifecycle.LifecycleCoroutineScope
import com.tes.weatherapp.data.remote.londonweather.apiservice.WeatherApi
import com.tes.weatherapp.data.remote.londonweather.dto.LondonWeatherResponse
import com.tes.weatherapp.domain.repository.WeatherRepository
import com.tes.weatherapp.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {

    override suspend fun getWeatherData(): Flow<Resource<LondonWeatherResponse>> {

        return flow {
            //api.getWeatherResponse()
            emit(Resource.Loading(true))
            val remoteData = try {
                api.getWeatherResponse()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null // flow{null}
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null // flow{null}
            }
            remoteData?.let { listings ->
                emit(Resource.Success(
                    data = listings
                ))
                emit(Resource.Loading(false))
            }
        }
    }

}