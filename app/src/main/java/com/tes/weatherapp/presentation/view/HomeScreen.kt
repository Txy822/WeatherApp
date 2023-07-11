package com.tes.weatherapp.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.tes.weatherapp.R
import com.tes.weatherapp.presentation.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen( ) {
    val viewModel: WeatherViewModel = viewModel()
   // val condition = viewModel.condition.collectAsState()
    val current by viewModel.current.collectAsState()
    val forcast = viewModel.forcast.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.sky) ,
            contentDescription = "icon"
        )
        Column(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
            ) {
//        Text(text="Condition: "+condition.value.text )
//        Spacer(modifier = Modifier.height(8.dp))

                Box(modifier= Modifier){
                    Column() {
                        Text(text="Condition: "+ (current.condition?.text ?: ""))
                        Spacer(modifier = Modifier.width(16.dp))
                        Row() {
                            Text(text = "Last updated: "+ current.last_updated?.let { convertToWeeklyDate(it) } +"(" + current.last_updated+")")
                            Spacer(modifier = Modifier.width(16.dp))
                            AsyncImage(
                                model ="https:"+ (current.condition?.icon ?: R.drawable.ic_launcher_background),
                                contentDescription = "icon",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text="Cloud: "+ (current.cloud))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text="Temp in C: "+ (current.temp_c))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text="Feels like C: "+ (current.feelslike_c))
                        Divider(thickness = 2.dp, color = Color.Black)
                    }

                }
                if(!forcast.value.forecastday.isEmpty()) {

                    for (i in forcast.value.forecastday){
                        Row() {
                            Text(text = "date: " + i.date)
                            Spacer(modifier = Modifier.width(16.dp))
                            AsyncImage(
                                model ="https:"+i.day.condition.icon ,
                                contentDescription = "icon",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text="Condition: "+i.day.condition.text )
                        }

                    }
//
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = "day: " + forcast.value.forecastday[1].day)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = "hour condition: " + forcast.value.forecastday[0].hour[0].condition.text)
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(text = "hour date: " + forcast.value.forecastday[0].date)
//
//            Row(){
//                AsyncImage(
//                    model ="https:"+forcast.value.forecastday[0].hour[0].condition.icon ,
//                    contentDescription = "icon",
//                    modifier = Modifier
//                        .size(200.dp)
//                        .clip(RoundedCornerShape(8.dp))
//                )
//                Spacer(modifier = Modifier.width(16.dp))
//                AsyncImage(
//                    model ="https:"+forcast.value.forecastday[0].hour[3].condition.icon ,
//                    contentDescription = "icon",
//                    modifier = Modifier
//                        .size(200.dp)
//                        .clip(RoundedCornerShape(8.dp))
//                )
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))

//            Image(
//                painter = painterResource(id = R.drawable.ic_launcher_background),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(200.dp)
//                    .clip(RoundedCornerShape(8.dp))
//                    .background(color = colorResource(id = R.color.black))
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = "hour time: " + forcast.value.forecastday[0].hour[0].time)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = "hour tempreture: " + forcast.value.forecastday[0].hour[0].temp_c)

                }

            }
        }
    }

}

fun convertToWeeklyDate(dateString: String): String {
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