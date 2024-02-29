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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.ke.weatherapp.R
import co.ke.weatherapp.ui.components.WeatherForecastItem
import co.ke.weatherapp.ui.utils.convertKelvinToCelsius
import co.ke.weatherapp.ui.viewmodel.WeatherViewModel
import java.util.Locale


@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier, weatherViewModel: WeatherViewModel
) {
    val weatherState by weatherViewModel.weatherState.collectAsStateWithLifecycle()

    when {
        weatherState.isLoading -> LoadingScreen(modifier = modifier)
        weatherState.errorMessage != null -> ErrorScreen(modifier = modifier)
        else -> {

            weatherState.weatherInfo?.let { weatherInfo ->


                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(weatherInfo.weatherType.color)
                        .statusBarsPadding()
                        .navigationBarsPadding()
                ) {
                    Row(
                        modifier = modifier
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
                            val image: Painter =
                                painterResource(id = weatherInfo.weatherType.imageRes)
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
                                        .clickable {},
                                    tint = Color.White
                                )

                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = modifier
                                    .align(Alignment.Center)
                                    .padding(bottom = 80.dp, end = 15.dp)
                            ) {
                                Text(
                                    text = weatherInfo.currentWeather.main.temp.convertKelvinToCelsius(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    style = TextStyle(
                                        fontSize = 70.sp
                                    )
                                )

                                Text(
                                    text = weatherInfo.weatherType.weatherDesc.uppercase(Locale.ROOT),
                                    color = Color.White,
                                    style = TextStyle(
                                        fontSize = 30.sp
                                    )
                                )
                            }

                            Row(
                                modifier = modifier
                                    .align(Alignment.TopEnd)
                                    .padding(16.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.pin),
                                    contentDescription = null,
                                    modifier = modifier
                                        .size(25.dp)
                                        .padding(end = 5.dp),
                                    tint = Color.White
                                )
                                Text(
                                    text = weatherInfo.currentWeather.name, color = Color.White
                                )
                            }


                        }
                    }

                    Row(
                        modifier = Modifier
                            .weight(10f)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(weatherInfo.weatherType.color)
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
                                            text = weatherInfo.currentWeather.main.tempMin.convertKelvinToCelsius(),
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
                                            text = weatherInfo.currentWeather.main.temp.convertKelvinToCelsius(),
                                            color = Color.White,
                                            style = TextStyle(
                                                fontSize = 20.sp
                                            )
                                        )
                                        Text(
                                            text = "Current",
                                            color = Color.White,
                                            style = TextStyle(
                                                fontSize = 18.sp
                                            )
                                        )
                                    }

                                    Column(
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        Text(
                                            text = weatherInfo.currentWeather.main.tempMax.convertKelvinToCelsius(),
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
                            .background(weatherInfo.weatherType.color)
                    ) {
                        // Third Row
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .fillMaxWidth()
                        ) {
                            WeatherForecastItem(
                                modifier = modifier,
                                weatherInfo = weatherInfo
                            )

                        }
                    }
                }
            }


        }

    }
}


