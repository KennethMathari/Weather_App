package co.ke.weatherapp.data.repository

import android.os.Build
import androidx.annotation.RequiresExtension
import co.ke.weatherapp.data.network.services.WeatherApi
import co.ke.weatherapp.di.IoDispatcher
import co.ke.weatherapp.domain.CityNameError
import co.ke.weatherapp.domain.NetworkResult
import co.ke.weatherapp.domain.WeatherInfoError
import co.ke.weatherapp.domain.WeatherType
import co.ke.weatherapp.domain.mappers.mapToWeatherForecastDomain
import co.ke.weatherapp.domain.mappers.toDomain
import co.ke.weatherapp.domain.model.CurrentWeatherDomain
import co.ke.weatherapp.domain.model.WeatherForecastDomain
import co.ke.weatherapp.ui.state.WeatherInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
    ): Flow<NetworkResult<CurrentWeatherDomain, WeatherInfoError>> {
        return flow<NetworkResult<CurrentWeatherDomain, WeatherInfoError>> {
            val result = weatherApi.getCurrentWeather(
                latitude, longitude, apiKey
            ).toDomain()
            emit(NetworkResult.Success(result))
        }.flowOn(ioDispatcher).onStart {
            emit(NetworkResult.Loading)
        }.catch {
            emit(NetworkResult.Error(WeatherInfoError.NO_WEATHER_INFO))
        }
    }

    override suspend fun getWeatherForecast(
        latitude: String, longitude: String, apiKey: String
    ): Flow<NetworkResult<WeatherForecastDomain, WeatherInfoError>> {
        return flow<NetworkResult<WeatherForecastDomain, WeatherInfoError>> {
            val result = weatherApi.getWeatherForecast(
                latitude, longitude, apiKey
            )
            emit(NetworkResult.Success(mapToWeatherForecastDomain(result)))
        }.flowOn(ioDispatcher).onStart {
            emit(NetworkResult.Loading)
        }.catch {
            emit(NetworkResult.Error(WeatherInfoError.NO_WEATHER_INFO))
        }
    }


    override suspend fun getWeatherByCityName(
        cityName: String, apiKey: String
    ): Flow<NetworkResult<WeatherInfo, CityNameError>> {
        return flow<NetworkResult<WeatherInfo, CityNameError>> {

            val currentWeatherDomain = weatherApi.getWeatherByCityName(cityName, apiKey).toDomain()
            val weatherForecastResult = weatherApi.getWeatherForecastByCityName(cityName, apiKey)

            val weatherForecastDomain = mapToWeatherForecastDomain(weatherForecastResult)

            val weatherInfo = WeatherInfo(
                currentWeather = currentWeatherDomain,
                weatherForecast = weatherForecastDomain,
                weatherType = WeatherType.getWeatherType(currentWeatherDomain.weather[0].id)
            )

            emit(NetworkResult.Success(weatherInfo))
        }.flowOn(ioDispatcher).onStart {
            emit(NetworkResult.Loading)
        }.catch {
            emit(NetworkResult.Error(CityNameError.NO_CITY_NAME))
        }
    }

}