package co.ke.weatherapp.data.repository

import co.ke.weatherapp.data.network.dto.CurrentWeather
import co.ke.weatherapp.data.network.dto.WeatherForecast
import co.ke.weatherapp.data.network.utils.NetworkResult
import co.ke.weatherapp.domain.model.CurrentWeatherDomain
import co.ke.weatherapp.domain.model.WeatherForecastDomain
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getCurrentWeather(
        latitude: String, longitude: String, apiKey: String
    ): Flow<NetworkResult<CurrentWeatherDomain>>

    suspend fun getWeatherForecast(
        latitude: String, longitude: String, apiKey: String
    ): Flow<NetworkResult<WeatherForecastDomain>>
}