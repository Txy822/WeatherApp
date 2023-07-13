package com.tes.weatherapp.core.di

import com.tes.weatherapp.data.remote.londonweather.apiservice.WeatherApi
import com.tes.weatherapp.data.repository.WeatherRepositoryImpl
import com.tes.weatherapp.domain.repository.WeatherRepository
import com.tes.weatherapp.domain.usecase.WeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)

class AppModule {

    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()

    @Provides
    fun providesRetrofit(): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(WeatherApi.BASE_URL)
        .build()

    @Provides
    fun providesApi(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)

    @Provides
    fun providesRepository(
        api: WeatherApi,
//        db:CatFactDatabase
    ): WeatherRepository {
        return WeatherRepositoryImpl(
            api,
            //db
        )
    }

    @Provides
    fun provideCatFactUseCase(repository: WeatherRepository): WeatherUseCase {
        return WeatherUseCase(repository)
    }
}