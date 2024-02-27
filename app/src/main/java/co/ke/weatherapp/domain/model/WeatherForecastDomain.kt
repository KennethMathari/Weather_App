package co.ke.weatherapp.domain.model

import co.ke.weatherapp.data.network.dto.City
import co.ke.weatherapp.data.network.dto.WeatherForecastInfo

data class WeatherForecastDomain(
    val list: List<WeatherForecastInfoDomain>,
)
