package co.ke.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.ke.weatherapp.ui.state.WeatherState
import co.ke.weatherapp.R
import co.ke.weatherapp.ui.utils.convertKelvinToCelsius

@Composable
fun WeatherScreen(
    weatherState: WeatherState,
    modifier: Modifier = Modifier
){
    weatherState.weatherInfo?.let { weatherInfo ->



        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .weight(45f)
                    .fillMaxSize()
            ) {
                // First Row
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
               ) {
                    //Image part
                        val image: Painter = painterResource(id = R.drawable.forest_sunny)
                        Image(
                            painter = image,
                            contentDescription = null,
                            modifier = modifier
                                .fillMaxSize(),
                            alignment = Alignment.BottomCenter
                        )

                    Box (
                            modifier = modifier
                                .padding(16.dp)
                                .align(Alignment.TopStart)
                        ){
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_menu_24),
                                contentDescription = null,
                                modifier = modifier
                                    .size(35.dp)
                                    .clickable(
                                        enabled = true
                                    ) {},
                                tint = Color.White
                            )

                        }

                    Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = modifier.align(Alignment.Center)
                        ) {
                            Text(
                                text = weatherInfo.currentWeather!!.main.temp!!.convertKelvinToCelsius(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                style = TextStyle(
                                    fontSize = 34.sp
                                )
                            )
                            Text(
                                text = weatherInfo.currentWeather.weather[0].main ,
                                color = Color.White,
                            )
                        }
                }
            }

            Row(
                modifier = Modifier
                    .weight(10f)
                    .fillMaxWidth()
            ) {
                // Second Row
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Column {
                            Text(text = weatherInfo.currentWeather!!.main.tempMin!!.convertKelvinToCelsius())
                            Text(text = "min")
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = weatherInfo.currentWeather!!.main.temp!!.convertKelvinToCelsius())
                            Text(text = "Current")
                        }

                        Column(
                            horizontalAlignment = Alignment.End
                        ){
                            Text(text = weatherInfo.currentWeather!!.main.tempMax!!.convertKelvinToCelsius())
                            Text(text = "max")
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .weight(45f)
                    .fillMaxWidth()
            ) {
                // Third Row
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        //.background(Color.Blue)
                ) {
//                    Text(
//                        text = "Third Row",
//                        color = Color.White,
//                        modifier = Modifier.align(Alignment.Center)
//                    )
                    LazyColumn(
                        modifier = modifier.padding(8.dp)
                    ){
                        weatherInfo.weatherForecast?.let {
                            items(it.list){it->
                                Text(text = it.dt.toString())
                            }
                        }
                    }
                }
            }
        }


    }
}


