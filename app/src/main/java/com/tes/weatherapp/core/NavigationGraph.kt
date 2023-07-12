package com.tes.weatherapp.core

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tes.weatherapp.data.remote.londonweather.dto.Forecastday
import com.tes.weatherapp.presentation.view.DetailScreen
import com.tes.weatherapp.presentation.view.HomeScreen
import com.tes.weatherapp.presentation.viewmodel.WeatherViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: WeatherViewModel,
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(route= Screen.Detail.route){
            val result = navController.previousBackStackEntry?.savedStateHandle?.get<Forecastday>("dayforcast")
            DetailScreen(forecastDay = result, navController = navController)
        }
    }

}