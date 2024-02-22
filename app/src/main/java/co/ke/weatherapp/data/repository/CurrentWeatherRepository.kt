package co.ke.weatherapp.data.repository

import co.ke.weatherapp.data.network.services.CurrentWeatherService
import javax.inject.Inject

class CurrentWeatherRepository @Inject constructor(
    private val currentWeatherService: CurrentWeatherService
) {
}