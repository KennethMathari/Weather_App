package co.ke.weatherapp.ui.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.ke.weatherapp.BuildConfig
import co.ke.weatherapp.data.location.LocationTracker
import co.ke.weatherapp.data.network.utils.NetworkResult
import co.ke.weatherapp.data.repository.WeatherRepository
import co.ke.weatherapp.di.IoDispatcher
import co.ke.weatherapp.domain.WeatherType.Companion.getWeatherType
import co.ke.weatherapp.domain.mappers.mapToCurrentWeatherDomain
import co.ke.weatherapp.domain.mappers.mapToWeatherForecastDomain
import co.ke.weatherapp.ui.state.WeatherInfo
import co.ke.weatherapp.ui.state.WeatherState
import co.ke.weatherapp.ui.utils.filterFor1000h
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> get() = _weatherState.asStateFlow()

    private val apiKey = BuildConfig.API_KEY

    fun getWeatherInfo() {
        viewModelScope.launch(ioDispatcher) {

            locationTracker.getCurrentLocation()?.let { location ->
                val latitude = location.latitude.toString()
                val longitude = location.longitude.toString()

                val currentWeatherFlow = weatherRepository.getCurrentWeather(
                    latitude = latitude, longitude = longitude, apiKey = apiKey
                ).map {
                    mapToCurrentWeatherDomain(it)
                }.catch { e ->
                    println(e)
                }

                val weatherForecastFlow = weatherRepository.getWeatherForecast(
                    latitude = latitude, longitude = longitude, apiKey = apiKey
                ).map {
                    mapToWeatherForecastDomain(it)
                }.catch { e ->
                    println(e)
                }

                combine(
                    currentWeatherFlow, weatherForecastFlow
                ) { currentWeatherResult, weatherForecastResult ->
                    Pair(currentWeatherResult, weatherForecastResult)
                }.buffer().collect { combinedWeatherResult ->
                    val currentWeather = combinedWeatherResult.first
                    val weatherForecast = combinedWeatherResult.second

                    when {
                        currentWeather is NetworkResult.Success && weatherForecast is NetworkResult.Success -> {
                            _weatherState.update { currentWeatherState ->
                                currentWeatherState.copy(
                                    weatherInfo = WeatherInfo(
                                        currentWeather = currentWeather.data,
                                        weatherForecast = weatherForecast.data.filterFor1000h(),
                                        weatherType = getWeatherType(currentWeather.data.weather[0].id)
                                    ), isLoading = false, errorMessage = null
                                )
                            }
                        }

                        currentWeather is NetworkResult.Error && weatherForecast is NetworkResult.Error -> {
                            _weatherState.update { currentWeatherState ->
                                currentWeatherState.copy(
                                    weatherInfo = null,
                                    isLoading = false,
                                    errorMessage = currentWeather.errorDetails.message
                                        ?: weatherForecast.errorDetails.message
                                )
                            }
                        }

                        currentWeather is NetworkResult.Loading && weatherForecast is NetworkResult.Loading -> {
                            _weatherState.update { currentWeatherState ->
                                currentWeatherState.copy(
                                    weatherInfo = null, isLoading = true, errorMessage = null
                                )
                            }
                        }
                    }
                }

            } ?: kotlin.run {
                _weatherState.update { currentWeatherState ->
                    currentWeatherState.copy(
                        isLoading = false,
                        errorMessage = "Couldn't retrieve location. Make sure to grant " + "permission and enable GPS"
                    )
                }
            }

        }
    }

    fun getWeatherByCityName(cityName: String){
        viewModelScope.launch(ioDispatcher) {

            weatherRepository.getWeatherByCityName(cityName, apiKey)
                .map {
                    mapToCurrentWeatherDomain(it)
                }.collect{ result->
                    when(result){
                        is NetworkResult.Success -> {
                            _weatherState.update { currentWeatherState ->
                                currentWeatherState.copy(
                                    weatherInfo = WeatherInfo(
                                        currentWeather = result.data,
                                        weatherType = getWeatherType(result.data.weather[0].id)
                                    ), isLoading = false, errorMessage = null
                                )
                            }
                        }
                        is NetworkResult.Error -> {
                            _weatherState.update { currentWeatherState ->
                                currentWeatherState.copy(
                                    isLoading = false,
                                    errorMessage = result.errorDetails.message
                                )
                            }
                        }
                        is NetworkResult.Loading->{
                            _weatherState.update { currentWeatherState ->
                                currentWeatherState.copy(
                                    isLoading = true,
                                    errorMessage = null
                                )
                            }
                        }

                    }
                }

        }
    }


}