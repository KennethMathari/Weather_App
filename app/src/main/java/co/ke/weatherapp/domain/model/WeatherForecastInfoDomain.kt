package co.ke.weatherapp.domain.model

import co.ke.weatherapp.data.network.dto.Clouds
import co.ke.weatherapp.data.network.dto.Main
import co.ke.weatherapp.data.network.dto.Rain
import co.ke.weatherapp.data.network.dto.Sys
import co.ke.weatherapp.data.network.dto.Weather
import co.ke.weatherapp.data.network.dto.Wind
import com.squareup.moshi.Json

data class WeatherForecastInfoDomain(
    val clouds: Clouds,
    val dt: Int,
    val dtTxt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain?,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)
