package co.ke.weatherapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.ke.weatherapp.ui.components.CurrentWeatherMinMaxTempSection
import co.ke.weatherapp.ui.components.CurrentWeatherSection
import co.ke.weatherapp.ui.components.WeatherForecastSection
import co.ke.weatherapp.ui.viewmodel.WeatherViewModel


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
                        CurrentWeatherSection(modifier = modifier,
                            weatherInfo = weatherInfo,
                            onLocationSearchClicked = {
                                if (it.isNotBlank()) {
                                    weatherViewModel.getWeatherByCityName(it)
                                }
                            })
                    }

                    Row(
                        modifier = modifier
                            .weight(10f)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(weatherInfo.weatherType.color)
                    ) {
                        // Second Row
                        CurrentWeatherMinMaxTempSection(
                            modifier = modifier, weatherInfo = weatherInfo
                        )

                    }

                    Row(
                        modifier = modifier
                            .weight(45f)
                            .fillMaxWidth()
                            .background(weatherInfo.weatherType.color)
                    ) {
                        // Third Row
                        Box(
                            modifier = modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .fillMaxWidth()
                        ) {
                            WeatherForecastSection(
                                modifier = modifier, weatherInfo = weatherInfo
                            )
                        }
                    }
                }
            }
        }
    }
}


