package com.tes.weatherapp.presentation.view

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.tes.weatherapp.R
import com.tes.weatherapp.core.Screen
import com.tes.weatherapp.domain.model.ForecastdayModel
import com.tes.weatherapp.presentation.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    viewModel: WeatherViewModel = viewModel()
) {
    val current by viewModel.current.collectAsState()
    val forecast = viewModel.forecast.collectAsState()
    val loading = viewModel.loading.collectAsState()
    val error = viewModel.error.collectAsState()

    val context = LocalContext.current
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    val isConnected = networkInfo?.isConnectedOrConnecting == true

    if (!isConnected) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(id = R.string.no_internet_connection))
        }
    }
    if (forecast.value.forecastday.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.sky),
                contentDescription = "icon"
            )
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                        Column() {
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.london_weather),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.SansSerif,
                                    color = Color.Black

                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start =8.dp,top = 4.dp, bottom = 4.dp, end= 8.dp),
                                border = BorderStroke(1.dp, Color.Black),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.Transparent,
                                ),
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(start =8.dp,end= 8.dp ),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.current_condition),
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Italic,
                                        color = Color.Yellow,
                                        fontFamily = FontFamily.Monospace
                                    )
                                    Text(
                                        text = current.condition?.text ?: "",
                                        color = Color.Yellow,
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Italic,
                                        fontFamily = FontFamily.SansSerif
                                    )
                                    AsyncImage(
                                        model = "https:" + (current.condition?.icon
                                            ?: R.drawable.ic_launcher_background),
                                        contentDescription = "icon",
                                        alignment = Alignment.Center,
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                    Text(
                                        text = "" + current.temp_c + "°C",
                                        color = Color.Yellow,
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Italic,
                                        fontFamily = FontFamily.SansSerif
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    modifier = Modifier.padding(start =8.dp,end= 8.dp ),
                                    text = "Last update: " + current.last_updated?.let {
                                        convertToWeeklyDate_yyyy_mm_dd_hh_mm(
                                            it
                                        )
                                    } + "(" + current.last_updated + ")",
                                    fontSize = 16.sp,
                                    color = Color.Yellow,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    fontFamily = FontFamily.SansSerif,
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                                //Divider(thickness = 3.dp, color = Color.Yellow)
                                Divider(thickness = 2.dp, color = Color.Yellow)
                                Spacer(modifier = Modifier.height(16.dp))

                                LazyColumn(
                                    modifier = Modifier
                                ) {

                                    items(forecast.value.forecastday.size) { i ->
                                        val dayforcast = forecast.value.forecastday[i]

                                        WeatherCard(dayforecast = dayforcast) {
                                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                                key = "dayforcast",
                                                value = dayforcast
                                            )
                                            navController.navigate(Screen.Detail.route)
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }

    } else if (loading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CustomCircularProgressBar()
        }
    } else if (error.value.isNotBlank() && isConnected) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No Data")
        }
    }

}

@Composable
fun WeatherCard(
    dayforecast: ForecastdayModel,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
            .clickable { onItemClick() },
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Spacer(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) // height and background only for demonstration
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = dayforecast.date.let {
                        convertToWeeklyDate_yyyy_mm_dd(it)
                    } + " (" + dayforecast.date + ")",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = dayforecast.day.condition.text,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Black
                )
            }
            Spacer(
                Modifier
                    .weight(4f)
                    .fillMaxHeight()
            ) // height and background only for demonstration
            AsyncImage(
                modifier = Modifier
                    .padding(16.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = "icon",
                model = "https:${dayforecast.day.condition.icon}"
            )
        }

    }
}

fun convertToWeeklyDate_yyyy_mm_dd_hh_mm(dateString: String): String {
    // Define input and output date formats
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val outputDateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    // Parse the input date string
    val date: Date? = inputDateFormat.parse(dateString)
    // Convert the parsed date to the output format
    return if (date != null) {
        outputDateFormat.format(date)
    } else {
        "Invalid date"
    }
}

fun convertToWeeklyDate_yyyy_mm_dd(dateString: String): String {
    // Define input and output date formats
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputDateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    // Parse the input date string
    val date: Date? = inputDateFormat.parse(dateString)
    // Convert the parsed date to the output format
    return if (date != null) {
        outputDateFormat.format(date)
    } else {
        "Invalid date"
    }
}

@Composable
private fun CustomCircularProgressBar() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp, bottom = 50.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(100.dp),
            color = Color.Green,
            strokeWidth = 10.dp
        )
    }
}