package co.ke.weatherapp.data.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}