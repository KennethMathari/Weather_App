package co.ke.weatherapp.domain

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import co.ke.weatherapp.R
import co.ke.weatherapp.ui.theme.Cloudy
import co.ke.weatherapp.ui.theme.Rainy
import co.ke.weatherapp.ui.theme.Sunny

sealed class WeatherType(
    val weatherDesc: String,
    @DrawableRes val iconRes: Int,
    @DrawableRes val imageRes: Int,
    val color: Color
) {
    object Clear : WeatherType(
        weatherDesc = "Sunny",
        iconRes = R.drawable.clear,
        imageRes = R.drawable.forest_sunny,
        color = Sunny
    )

    object Clouds : WeatherType(
        weatherDesc = "Cloudy",
        iconRes = R.drawable.partlysunny,
        imageRes = R.drawable.forest_cloudy,
        color = Cloudy
    )

    object Rain : WeatherType(
        weatherDesc = "Rainy",
        iconRes = R.drawable.rain,
        imageRes = R.drawable.forest_rainy,
        color = Rainy
    )

    object Thunderstorm : WeatherType(
        weatherDesc = "Thunderstorm",
        iconRes = R.drawable.rain,
        imageRes = R.drawable.forest_rainy,
        color = Rainy
    )

    object Drizzle : WeatherType(
        weatherDesc = "Drizzle",
        iconRes = R.drawable.rain,
        imageRes = R.drawable.forest_rainy,
        color = Rainy
    )

    companion object {
        fun getWeatherType(code: Int): WeatherType {
            return when (code) {
                800 -> Clear
                801 -> Clouds
                802 -> Clouds
                803 -> Clouds
                804 -> Clouds
                500 -> Rain
                501 -> Rain
                502 -> Rain
                503 -> Rain
                504 -> Rain
                511 -> Rain
                520 -> Rain
                521 -> Rain
                522 -> Rain
                531 -> Rain
                200 -> Thunderstorm
                201 -> Thunderstorm
                202 -> Thunderstorm
                210 -> Thunderstorm
                211 -> Thunderstorm
                212 -> Thunderstorm
                221 -> Thunderstorm
                230 -> Thunderstorm
                231 -> Thunderstorm
                232 -> Thunderstorm
                300 -> Drizzle
                301 -> Drizzle
                302 -> Drizzle
                310 -> Drizzle
                311 -> Drizzle
                312 -> Drizzle
                313 -> Drizzle
                314 -> Drizzle
                321 -> Drizzle
                else -> {
                    Clouds
                }
            }
        }
    }
}