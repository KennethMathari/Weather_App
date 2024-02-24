package co.ke.weatherapp.ui.state

import co.ke.weatherapp.data.network.dto.CurrentWeather
import co.ke.weatherapp.data.network.dto.WeatherForecast

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class WeatherInfo(
    val currentWeather: CurrentWeather? = null,
    val weatherForecast: WeatherForecast? = null
)
