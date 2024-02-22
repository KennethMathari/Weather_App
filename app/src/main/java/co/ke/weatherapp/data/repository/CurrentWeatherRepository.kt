package co.ke.weatherapp.data.repository

import co.ke.weatherapp.data.network.models.CurrentWeather
import co.ke.weatherapp.data.network.services.CurrentWeatherService
import co.ke.weatherapp.data.network.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CurrentWeatherRepository @Inject constructor(
    private val currentWeatherService: CurrentWeatherService
) {

    suspend fun getCurrentWeather(latitude: Double, longitude:Double, apiKey: String)
    : Flow<NetworkResult<CurrentWeather>> {
        return flow {
            try {
                val currentWeather = currentWeatherService.getCurrentWeather(
                    latitude,
                    longitude,
                    apiKey
                )

                emit(NetworkResult.Success(currentWeather))
            } catch (e: Throwable){
                emit(NetworkResult.Error(e))
            }

        }
            .flowOn(Dispatchers.IO)
            .onStart {
                emit(NetworkResult.Loading)
            }
    }
}