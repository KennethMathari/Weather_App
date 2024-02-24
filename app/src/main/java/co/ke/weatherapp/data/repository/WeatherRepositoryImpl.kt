package co.ke.weatherapp.data.repository

import co.ke.weatherapp.data.network.dto.CurrentWeather
import co.ke.weatherapp.data.network.dto.WeatherForecast
import co.ke.weatherapp.data.network.services.WeatherApi
import co.ke.weatherapp.data.network.utils.NetworkResult
import co.ke.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        latitude: String, longitude: String, apiKey: String
    ): Flow<NetworkResult<CurrentWeather>> {
        return flow {
            try {
                val currentWeather = weatherApi.getCurrentWeather(
                    latitude, longitude, apiKey
                )

                emit(NetworkResult.Success(currentWeather))
            } catch (e: Throwable) {
                emit(NetworkResult.Error(e))
            }

        }.flowOn(ioDispatcher).onStart {
                emit(NetworkResult.Loading)
            }
    }

    override suspend fun getWeatherForecast(
        latitude: String, longitude: String, apiKey: String
    ): Flow<NetworkResult<WeatherForecast>> {
        return flow {
            try {
                val weatherForecast = weatherApi.getWeatherForecast(
                    latitude, longitude, apiKey
                )

                emit(NetworkResult.Success(weatherForecast))
            } catch (e: Exception) {
                emit(NetworkResult.Error(e))
            }
        }.flowOn(ioDispatcher).onStart {
                emit(NetworkResult.Loading)
            }
    }
}