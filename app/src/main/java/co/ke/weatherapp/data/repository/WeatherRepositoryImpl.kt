package co.ke.weatherapp.data.repository

import android.os.Build
import androidx.annotation.RequiresExtension
import co.ke.weatherapp.data.network.dto.CurrentWeather
import co.ke.weatherapp.data.network.dto.WeatherForecast
import co.ke.weatherapp.data.network.services.WeatherApi
import co.ke.weatherapp.data.network.utils.NetworkResult
import co.ke.weatherapp.data.network.utils.safeApiCall
import co.ke.weatherapp.di.IoDispatcher
import co.ke.weatherapp.domain.WeatherType
import co.ke.weatherapp.domain.mappers.mapToCurrentWeatherDomain
import co.ke.weatherapp.domain.mappers.mapToWeatherForecastDomain
import co.ke.weatherapp.ui.state.WeatherInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi, @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        latitude: String, longitude: String, apiKey: String
    ): Flow<NetworkResult<CurrentWeather>> {
        return flow {
            val result = safeApiCall {
                weatherApi.getCurrentWeather(
                    latitude, longitude, apiKey
                )
            }
            emit(result)
        }.flowOn(ioDispatcher).onStart {
            emit(NetworkResult.Loading)
        }

    }

    override suspend fun getWeatherForecast(
        latitude: String, longitude: String, apiKey: String
    ): Flow<NetworkResult<WeatherForecast>> {
        return flow {
            val result = safeApiCall {
                weatherApi.getWeatherForecast(
                    latitude, longitude, apiKey
                )

            }
            emit(result)
        }.flowOn(ioDispatcher).onStart {
            emit(NetworkResult.Loading)
        }
    }


    override suspend fun getWeatherByCityName(
        cityName: String, apiKey: String
    ): Flow<NetworkResult<WeatherInfo>> {
        return flow {

            val currentWeatherResult = safeApiCall {
                weatherApi.getWeatherByCityName(cityName, apiKey)
            }

            if (currentWeatherResult is NetworkResult.Success) {
                val currentWeather = currentWeatherResult.data
                val latitude = currentWeather.coord.lat.toString()
                val longitude = currentWeather.coord.lon.toString()

                val weatherForecastResult = safeApiCall {
                    weatherApi.getWeatherForecast(latitude, longitude, apiKey)
                }

                if (weatherForecastResult is NetworkResult.Success) {
                    val weatherForecast = weatherForecastResult.data

                    emit(
                        NetworkResult.Success(
                            WeatherInfo(
                                currentWeather = mapToCurrentWeatherDomain(currentWeather),
                                weatherForecast = mapToWeatherForecastDomain(weatherForecast),
                                weatherType = WeatherType.getWeatherType(
                                    currentWeather.weather[0].id ?: 0
                                )
                            )
                        )
                    )
                } else {
                    emit(NetworkResult.Error(Exception("Failed to fetch weather forecast")))
                }
            } else {
                emit(NetworkResult.Error(Exception("Failed to fetch current weather")))
            }
        }.flowOn(ioDispatcher).onStart {
                NetworkResult.Loading
            }
    }

}