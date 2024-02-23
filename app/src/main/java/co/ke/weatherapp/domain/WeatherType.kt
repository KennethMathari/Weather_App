package co.ke.weatherapp.domain

import androidx.annotation.DrawableRes
import co.ke.weatherapp.R

sealed class WeatherType(
    val weatherDesc: String,
    @DrawableRes val iconRes: Int
) {
    object Sunny: WeatherType(
        weatherDesc = "Sunny",
        iconRes = R.drawable.forest_sunny
    )
}