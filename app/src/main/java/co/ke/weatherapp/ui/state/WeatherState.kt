package co.ke.weatherapp.ui.state

import co.ke.weatherapp.data.network.models.CurrentWeather

data class WeatherState(
    val currentWeather: CurrentWeather? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
