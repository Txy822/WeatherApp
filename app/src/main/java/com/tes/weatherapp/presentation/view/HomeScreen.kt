package com.tes.weatherapp.presentation.view

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.tes.weatherapp.R
import com.tes.weatherapp.core.Screen
import com.tes.weatherapp.presentation.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    viewModel: WeatherViewModel = viewModel()
) {
   // val viewModel: WeatherViewModel = viewModel()
    // val condition = viewModel.condition.collectAsState()
    val current by viewModel.current.collectAsState()
    val forcast = viewModel.forcast.collectAsState()
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
            Text(text = "No Internet connection")
        }
    }
    if (!forcast.value.forecastday.isEmpty()) {


        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.sky),
                contentDescription = "icon"
            )
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
//                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {

                    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                        Column() {
                            Spacer(modifier = Modifier.height(16.dp))
                            Box(contentAlignment = Alignment.Center){
                                Text(text = "London Weather")
                            }
                            Spacer(modifier = Modifier.height(32.dp))
                            Text(text = "Condition: " + (current.condition?.text ?: ""))
                            Spacer(modifier = Modifier.height(16.dp))
                            Row() {
                                Text(text = "Last updated: " + current.last_updated?.let {
                                    convertToWeeklyDate_yyyy_mm_dd_hh_mm(
                                        it
                                    )
                                } + "(" + current.last_updated + ")")
                                Spacer(modifier = Modifier.width(16.dp))
                                AsyncImage(
                                    model = "https:" + (current.condition?.icon
                                        ?: R.drawable.ic_launcher_background),
                                    contentDescription = "icon",
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = "Cloud: " + (current.cloud))
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = "Temp in C: " + (current.temp_c))
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = "Feels like C: " + (current.feelslike_c))
                            Divider(thickness = 2.dp, color = Color.Black)
                        }

                    }

                    LazyColumn(
                        modifier = Modifier
                    ) {

                        items(forcast.value.forecastday.size) { i ->
                            val dayforcast = forcast.value.forecastday[i]
                            Row(modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(key="dayforcast", value = dayforcast)
                                    navController.navigate(
                                        route= Screen.Detail.route
                                    )
                                }
                            ) {

                                Text(text = dayforcast.date.let {
                                    convertToWeeklyDate_yyyy_mm_dd(
                                        it
                                    )
                                } + "(" + dayforcast.date + ")")

                                Spacer(modifier = Modifier.width(16.dp))
                                AsyncImage(
                                    model = "https:" + dayforcast.day.condition.icon,
                                    contentDescription = "icon",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(text = dayforcast.day.condition.text)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Divider(thickness = 1.dp, color = Color.Black)
                            Spacer(modifier = Modifier.height(4.dp))

                        }
                    }

                    /*
                    if (!forcast.value.forecastday.isEmpty()) {

                        for (i in forcast.value.forecastday) {
                            Row() {
                                Text(text = "date: " + i.date)
                                Spacer(modifier = Modifier.width(16.dp))
                                AsyncImage(
                                    model = "https:" + i.day.condition.icon,
                                    contentDescription = "icon",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(text = "Condition: " + i.day.condition.text)
                            }

                        }
                    }
*/
                }
            }
        }
        /*
              Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.sky),
                contentDescription = "icon"
            )
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {

                    Box(modifier = Modifier) {
                        Column() {
                            Spacer(modifier = Modifier.heightIn(16.dp))
                            Text(text = "London Weather")
                            Spacer(modifier = Modifier.height(32.dp))
                            Text(text = "Condition: " + (current.condition?.text ?: ""))
                            Spacer(modifier = Modifier.height(16.dp))
                            Row() {
                                Text(text = "Last updated: " + current.last_updated?.let {
                                    convertToWeeklyDate(
                                        it
                                    )
                                } + "(" + current.last_updated + ")")
                                Spacer(modifier = Modifier.width(16.dp))
                                AsyncImage(
                                    model = "https:" + (current.condition?.icon
                                        ?: R.drawable.ic_launcher_background),
                                    contentDescription = "icon",
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = "Cloud: " + (current.cloud))
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = "Temp in C: " + (current.temp_c))
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = "Feels like C: " + (current.feelslike_c))
                            Divider(thickness = 2.dp, color = Color.Black)
                        }

                    }
                    if (!forcast.value.forecastday.isEmpty()) {

                        for (i in forcast.value.forecastday) {
                            Row() {
                                Text(text = "date: " + i.date)
                                Spacer(modifier = Modifier.width(16.dp))
                                AsyncImage(
                                    model = "https:" + i.day.condition.icon,
                                    contentDescription = "icon",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(text = "Condition: " + i.day.condition.text)
                            }

                        }
                    }

                }
            }
        }
         */
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