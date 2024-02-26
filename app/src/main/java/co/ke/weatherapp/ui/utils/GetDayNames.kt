package co.ke.weatherapp.ui.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

//@RequiresApi(Build.VERSION_CODES.O)
//fun List<Int>.getNextFiveDays(): List<String> {
//    val zoneId = ZoneId.systemDefault()
//    val formatter = DateTimeFormatter.ofPattern("EEEE", java.util.Locale.ENGLISH)
//
//    val currentDate = LocalDateTime.now(zoneId).toLocalDate()
//
//    return this.asSequence().map { timestamp ->
//        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp.toLong()), zoneId)
//        dateTime.toLocalDate() to formatter.format(dateTime)
//    }.filter { (date, _) -> date != currentDate }
//        .distinctBy { it.first }
//        .map { it.second }
//        .take(5).toList()
//}