package co.ke.weatherapp.data.repository

import co.ke.weatherapp.domain.CityNameError
import co.ke.weatherapp.domain.NetworkResult
import co.ke.weatherapp.domain.WeatherInfoError
import co.ke.weatherapp.domain.model.CurrentWeatherDomain
import co.ke.weatherapp.domain.model.WeatherForecastDomain
import co.ke.weatherapp.ui.state.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getCurrentWeather(
        latitude: String, longitude: String, apiKey: String
    ): Flow<NetworkResult<CurrentWeatherDomain, WeatherInfoError>>

    suspend fun getWeatherForecast(
        latitude: String, longitude: String, apiKey: String
    ): Flow<NetworkResult<WeatherForecastDomain, WeatherInfoError>>

    suspend fun getWeatherByCityName(
        cityName: String, apiKey: String
    ): Flow<NetworkResult<WeatherInfo, CityNameError>>
}