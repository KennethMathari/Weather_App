package co.ke.weatherapp.domain.model

import com.squareup.moshi.Json

data class WeatherDomain(
    val description: String,
    val id: Int,
    val main: String
)
