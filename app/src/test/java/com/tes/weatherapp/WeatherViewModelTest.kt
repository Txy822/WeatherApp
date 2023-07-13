package com.tes.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tes.weatherapp.data.remote.londonweather.dto.WeatherResponse
import com.tes.weatherapp.domain.model.CurrentModel
import com.tes.weatherapp.domain.model.ForecastModel
import com.tes.weatherapp.domain.model.WeatherResponseModel
import com.tes.weatherapp.domain.repository.WeatherRepository
import com.tes.weatherapp.domain.usecase.WeatherUseCase
import com.tes.weatherapp.presentation.viewmodel.WeatherViewModel
import com.tes.weatherapp.core.util.Resource
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Mock dependencies
    @Mock
    private lateinit var repository: WeatherRepository

    private lateinit var viewModel: WeatherViewModel
    private lateinit var useCase:WeatherUseCase


    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        // Initialize the mocks
        MockitoAnnotations.initMocks(this)

        // Create the ViewModel instance
        useCase = WeatherUseCase(repository)
        viewModel = WeatherViewModel(useCase)
        Dispatchers.setMain(testDispatcher)
    }


    @Test
     fun `test getWeatherData success`() = runBlocking  {

        val forecast = ForecastModel(emptyList())
        val current = CurrentModel( null, null, null, null, null, null)
        val weatherResponse = Resource.Success(WeatherResponseModel(current, forecast))
        `when`(repository.getWeatherData()).thenReturn(flowOf(weatherResponse))

        viewModel.getWeatherData()

        assertEquals(forecast, viewModel.forecast.value)
        assertEquals(current, viewModel.current.value)
        assertEquals(true, viewModel.loading.value)
        assertEquals("", viewModel.error.value)
    }

    @Test
    fun `test getWeatherData loading`() = runBlocking {
        val weatherResponse = Resource.Loading<WeatherResponse>(isLoading = true)
        `when`(repository.getWeatherData()).thenReturn(
           flow{ emit(Resource.Loading(true))}
        )
        viewModel.getWeatherData()

        assertEquals(false, viewModel.loading.value)
        assertEquals("", viewModel.error.value)

    }

    @Test
    fun `test getWeatherData error`() = runBlocking {
        val weatherResponse = Resource.Error<WeatherResponseModel>("Error Occurred",null )
        `when`(repository.getWeatherData()).thenReturn(flowOf(weatherResponse))

        viewModel.getWeatherData()
        assertEquals(false, viewModel.loading.value)
        assertEquals("Error Occurred", viewModel.error.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}


