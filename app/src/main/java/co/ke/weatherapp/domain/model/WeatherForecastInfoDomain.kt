package co.ke.weatherapp.domain.model

import co.ke.weatherapp.data.network.dto.Clouds
import co.ke.weatherapp.data.network.dto.Main
import co.ke.weatherapp.data.network.dto.Rain
import co.ke.weatherapp.data.network.dto.Sys
import co.ke.weatherapp.data.network.dto.Weather
import co.ke.weatherapp.data.network.dto.Wind
import com.squareup.moshi.Json

data class WeatherForecastInfoDomain(
    val dt: Int,
    val dtTxt: String,
    val main: MainDomain,
    val weather: List<WeatherDomain>
)
