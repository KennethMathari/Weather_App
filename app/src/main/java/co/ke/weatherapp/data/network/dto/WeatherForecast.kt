package co.ke.weatherapp.data.network.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherForecast(
    @Json(name = "list")
    val list: List<WeatherForecastInfo>
)