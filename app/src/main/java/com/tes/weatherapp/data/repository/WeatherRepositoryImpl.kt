package com.tes.weatherapp.data.repository

import com.tes.weatherapp.data.remote.londonweather.apiservice.WeatherApi
import com.tes.weatherapp.data.remote.londonweather.mapper.toWeatherResponseModel
import com.tes.weatherapp.domain.model.WeatherResponseModel
import com.tes.weatherapp.domain.repository.WeatherRepository
import com.tes.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {

    override suspend fun getWeatherData(): Flow<Resource<WeatherResponseModel>> {

        return flow {
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
            finally {
                emit(Resource.Loading(isLoading = false))
            }
            remoteData?.let { listings ->
                emit(Resource.Success(
                    data = listings.toWeatherResponseModel()
                ))
                emit(Resource.Loading(false))
            }
        }
    }

}