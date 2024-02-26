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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.ke.weatherapp.R
import co.ke.weatherapp.ui.state.WeatherState
import co.ke.weatherapp.ui.theme.Sunny
import co.ke.weatherapp.ui.utils.convertKelvinToCelsius
import co.ke.weatherapp.ui.utils.toDayOfWeek
import java.util.Locale

@Composable
fun WeatherScreen(
    weatherState: WeatherState, modifier: Modifier = Modifier
) {
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
                        modifier = modifier.fillMaxSize(),
                        alignment = Alignment.BottomCenter
                    )

                    Box(
                        modifier = modifier
                            .padding(16.dp)
                            .align(Alignment.TopStart)
                    ) {
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
                            .padding(bottom = 80.dp, end = 15.dp)
                    ) {
                        Text(
                            text = weatherInfo.currentWeather!!.main.temp!!.convertKelvinToCelsius(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                fontSize = 70.sp
                            )
                        )
                        Text(
                            text = weatherInfo.currentWeather.weather[0].main!!.uppercase(Locale.ROOT),
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 30.sp
                            )
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .weight(10f)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Sunny)
            ) {
                // Second Row
                Column {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(horizontal = 16.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = weatherInfo.currentWeather!!.main.tempMin!!.convertKelvinToCelsius(),
                                    color = Color.White,
                                    style = TextStyle(
                                        fontSize = 20.sp
                                    )
                                )
                                Text(
                                    text = "min", color = Color.White, style = TextStyle(
                                        fontSize = 18.sp
                                    )
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = weatherInfo.currentWeather!!.main.temp!!.convertKelvinToCelsius(),
                                    color = Color.White,
                                    style = TextStyle(
                                        fontSize = 20.sp
                                    )
                                )
                                Text(
                                    text = "Current", color = Color.White, style = TextStyle(
                                        fontSize = 18.sp
                                    )
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = weatherInfo.currentWeather!!.main.tempMax!!.convertKelvinToCelsius(),
                                    color = Color.White,
                                    style = TextStyle(
                                        fontSize = 20.sp
                                    )
                                )
                                Text(
                                    text = "max", color = Color.White, style = TextStyle(
                                        fontSize = 18.sp
                                    )
                                )
                            }
                        }
                    }

                    Divider(
                        thickness = 1.dp, color = Color.White
                    )
                }

            }

            Row(
                modifier = Modifier
                    .weight(45f)
                    .fillMaxWidth()
                    .background(Sunny)
            ) {
                // Third Row
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        weatherInfo.weatherForecast?.let {
                            items(it.list) {

                                Row(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(
                                            horizontal = 16.dp, vertical = 20.dp
                                        )
                                ) {
                                    Text(
                                        text = it.dt.toDayOfWeek(),
                                        modifier = modifier.weight(1f),
                                        style = TextStyle(
                                            color = Color.White, fontSize = 20.sp
                                        )
                                    )

                                    Image(
                                        painter = painterResource(id = R.drawable.clear),
                                        contentDescription = null,
                                        modifier = modifier.weight(1f).size(30.dp),
                                        alignment = Alignment.Center
                                    )

                                    Text(
                                        text = it.main.temp!!.convertKelvinToCelsius(),
                                        modifier = modifier.weight(1f),
                                        style = TextStyle(
                                            color = Color.White, fontSize = 20.sp
                                        ),
                                        textAlign = TextAlign.End
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


