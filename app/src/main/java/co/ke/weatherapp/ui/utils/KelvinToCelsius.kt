package co.ke.weatherapp.ui.utils

import kotlin.math.roundToInt

fun Double.convertKelvinToCelsius(): String {
    val celsius = (this - 273.15).roundToInt()
    return "$celsius°C"
}
