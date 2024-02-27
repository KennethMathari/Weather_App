package co.ke.weatherapp.ui.state

import co.ke.weatherapp.data.network.dto.CurrentWeather
import co.ke.weatherapp.data.network.dto.WeatherForecast
import co.ke.weatherapp.domain.WeatherType

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class WeatherInfo(
    val currentWeather: CurrentWeather? = null,
    val weatherForecast: WeatherForecast? = null,
    val weatherType: WeatherType? = null
)
