package co.ke.weatherapp.data.network.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherForecastInfo (
    @Json(name = "dt")
    val dt: Int,
    @Json(name = "main")
    val main: Main
)