package co.ke.weatherapp.domain.model

import com.squareup.moshi.Json

data class MainDomain(
    val temp: Double,
    val tempMax: Double,
    val tempMin: Double
)
