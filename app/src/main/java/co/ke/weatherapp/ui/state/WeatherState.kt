package co.ke.weatherapp.ui.state

import co.ke.weatherapp.domain.WeatherType
import co.ke.weatherapp.domain.model.CurrentWeatherDomain
import co.ke.weatherapp.domain.model.WeatherForecastDomain

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class WeatherInfo(
    val currentWeather: CurrentWeatherDomain,
    val weatherForecast: WeatherForecastDomain,
    val weatherType: WeatherType
)
