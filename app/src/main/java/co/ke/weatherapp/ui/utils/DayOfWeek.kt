package co.ke.weatherapp.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Int.toDayOfWeek(): String {
    // Create a Date object using the timestamp
    val date = Date(this.toLong() * 1000)

    // Define the date format
    val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())

    // Format the date to get the day of the week
    return dateFormat.format(date)
}