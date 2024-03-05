package co.ke.weatherapp.ui.utils

import androidx.annotation.StringRes
import co.ke.weatherapp.R

enum class WeatherRoutes(@StringRes val title: Int) {
    Weather(title = R.string.app_name),
    FavouriteCities(title = R.string.favourite_cities)
}