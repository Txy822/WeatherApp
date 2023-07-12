package com.tes.weatherapp.presentation.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.tes.weatherapp.R
import com.tes.weatherapp.data.remote.londonweather.dto.Day
import com.tes.weatherapp.data.remote.londonweather.dto.Forecastday


@Composable
fun DetailScreen(
    forecastDay: Forecastday?,
    navController: NavController) {

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

            ) {
                val day = forecastDay?.let { convertToWeeklyDate_yyyy_mm_dd(it.date) }
                TopAppBarContent(navController, day)
                Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                    Column(modifier =Modifier.padding(16.dp)) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "London Weather")
                        }
                        Spacer(modifier = Modifier.height(32.dp))

                        Text(text = "Date: " + forecastDay?.date?.let {
                            convertToWeeklyDate_yyyy_mm_dd(
                                it
                            )
                        } + "(" + forecastDay?.date + ")")
                        Spacer(modifier = Modifier.height(32.dp))

                        Text(text = "Condition: " + (forecastDay?.day?.condition?.text ?: ""))
                        Spacer(modifier = Modifier.height(16.dp))
                        Row() {

                            Spacer(modifier = Modifier.width(16.dp))
                            AsyncImage(
                                model = "https:" + (forecastDay?.day?.condition?.icon
                                    ?: R.drawable.ic_launcher_background),
                                contentDescription = "icon",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "Daily chance of rain: " + (forecastDay?.day?.daily_chance_of_rain))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "Temp in C: " + (forecastDay?.day?.avgtemp_c))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "Humidity: " + (forecastDay?.day?.avghumidity))
                      //  Divider(thickness = 2.dp, color = Color.Black)
                    }

                }
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarContent(navController: NavController, day: String?) {
    TopAppBar(
        title = {
            Text(
                text = " London Weather on $day",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Go back",
                )
            }
        }
    )
}