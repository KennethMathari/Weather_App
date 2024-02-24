package co.ke.weatherapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.ke.weatherapp.BuildConfig
import co.ke.weatherapp.data.network.utils.NetworkResult
import co.ke.weatherapp.domain.location.LocationTracker
import co.ke.weatherapp.domain.repository.WeatherRepository
import co.ke.weatherapp.ui.state.WeatherInfo
import co.ke.weatherapp.ui.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> get() = _weatherState.asStateFlow()

    private val apiKey = BuildConfig.API_KEY


    fun getWeatherInfo() {
        viewModelScope.launch(ioDispatcher) {

            _weatherState.update { currentWeatherState ->
                currentWeatherState.copy(
                    isLoading = true, errorMessage = null
                )
            }

            locationTracker.getCurrentLocation()?.let { location ->
                Log.e("Latitude:", "${location.latitude}")
                Log.e("Longitude:", "${location.longitude}")
                val latitude = location.latitude.toString()
                val longitude = location.longitude.toString()

                val currentWeatherFlow = weatherRepository.getCurrentWeather(
                    latitude = latitude, longitude = longitude, apiKey = apiKey
                )

                val weatherForecastFlow = weatherRepository.getWeatherForecast(
                    latitude = latitude, longitude = longitude, apiKey = apiKey
                )

                combine(
                    currentWeatherFlow,
                    weatherForecastFlow
                ) { currentWeatherResult, weatherForecastResult ->
                    Pair(currentWeatherResult, weatherForecastResult)
                }.buffer().collect { combinedWeatherResult ->
                        val currentWeather = combinedWeatherResult.first
                        val weatherForecast = combinedWeatherResult.second

                        if (currentWeather is NetworkResult.Success && weatherForecast is NetworkResult.Success) {
                            _weatherState.update { currentWeatherState ->
                                currentWeatherState.copy(
                                    weatherInfo = WeatherInfo(
                                        currentWeather = currentWeather.data,
                                        weatherForecast = weatherForecast.data
                                    ), isLoading = false, errorMessage = null
                                )
                            }
                        } else if (currentWeather is NetworkResult.Error && weatherForecast is NetworkResult.Error) {
                            _weatherState.update { currentWeatherState ->
                                currentWeatherState.copy(
                                    weatherInfo = null,
                                    isLoading = false,
                                    errorMessage = currentWeather.errorDetails.message
                                        ?: weatherForecast.errorDetails.message
                                )
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


}