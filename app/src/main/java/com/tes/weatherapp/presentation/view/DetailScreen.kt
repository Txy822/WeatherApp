package com.tes.weatherapp.presentation.view


import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.tes.weatherapp.R
import com.tes.weatherapp.data.remote.londonweather.dto.Hour
import com.tes.weatherapp.domain.model.ForecastModel
import com.tes.weatherapp.domain.model.ForecastdayModel
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun DetailScreen(
    forecastDay: ForecastdayModel? = ForecastModel(emptyList()).forecastday[0],
    navController: NavController = rememberNavController()
) {
    val configuration = LocalConfiguration.current
    val screenOrientation = configuration.orientation

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.sky),
            contentDescription = "icon"
        )
        Column(modifier = Modifier.fillMaxSize()) {
            val day = forecastDay?.let { convertToWeeklyDate_yyyy_mm_dd(it.date) }
            TopAppBarContent(navController, day)
            Column() {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start =8.dp,top = 4.dp, bottom = 4.dp, end= 8.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent,
                    ),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start=8.dp, end=8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(text = forecastDay?.date?.let {
                            convertToWeeklyDate_yyyy_mm_dd(
                                it
                            )
                        } + "(" + forecastDay?.date + ")",
                            color = Color.Black
                        )

                        AsyncImage(
                            model = "https:" + (forecastDay?.day?.condition?.icon
                                ?: R.drawable.ic_launcher_background),
                            contentDescription = "icon",
                            modifier = if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                                Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(8.dp))

                            } else {
                                Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            }
                        )
                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start=8.dp, end=8.dp),
                        text = "Condition: " + (forecastDay?.day?.condition?.text ?: ""),
                        color = Color.Black
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start=8.dp, end=8.dp),
                        text = "Daily chance of rain: " + (forecastDay?.day?.daily_chance_of_rain),
                        color = Color.Black
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start=8.dp, end=8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Temp in C: " + (forecastDay?.day?.avgtemp_c),
                            color = Color.Black
                        )
                        Text(
                            text = ", Humidity: " + (forecastDay?.day?.avghumidity),
                            color = Color.Black
                        )
                    }
                    val hours = forecastDay?.hour
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        hours?.let {
                            items(it.size) { i ->
                                val hour = hours[i]

                                CardItem(hour, screenOrientation)
                                if (i < hours.size) {
                                    Divider(
                                        modifier = Modifier.padding(
                                            horizontal = 8.dp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CardItem(
    hour: Hour,
    screenOrientation: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.6f)
        ),

        ) {
        Column(modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())) {
            val time = extractTimeFromDateTime(hour.time)
            Text(
                text = time,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            AsyncImage(
                model = "https:" + (hour?.condition?.icon
                    ?: R.drawable.ic_launcher_background),
                contentDescription = "",

                modifier = if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(50.dp)
                        .clip(RoundedCornerShape(8.dp))

                } else {
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                }
            )
            Text(
                text = "Temp: " + hour.temp_c.toString(),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Text(
                text = hour.condition.text,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Text(
                text = "Feels like: " + hour.feelslike_c.toString(),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Text(
                text = "Chance of rain: " + hour.chance_of_rain.toString(),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarContent(navController: NavController, day: String?) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = " London Weather on $day",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                color = Color.Black
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
                    tint = Color.Black
                )
            }
        }
    )
}


fun extractTimeFromDateTime(dateTime: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    val date = inputFormat.parse(dateTime)
    return outputFormat.format(date)
}