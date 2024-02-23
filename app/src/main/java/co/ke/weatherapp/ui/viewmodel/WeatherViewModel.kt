package co.ke.weatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.ke.weatherapp.data.network.utils.NetworkResult
import co.ke.weatherapp.domain.location.LocationTracker
import co.ke.weatherapp.domain.repository.WeatherRepository
import co.ke.weatherapp.ui.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
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

    val apiKey = System.getProperty("api.key")


    private fun getCurrentWeather() {
        viewModelScope.launch(ioDispatcher) {

            _weatherState.update { currentWeatherState ->
                currentWeatherState.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            locationTracker.getCurrentLocation()?.let { location ->
                weatherRepository.getCurrentWeather(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    apiKey = apiKey
                ).buffer()
                    .collect { result ->
                        when (result) {
                            is NetworkResult.Success -> {
                                _weatherState.update { currentWeatherState ->
                                    currentWeatherState.copy(
                                        currentWeather = result.data,
                                        isLoading = false,
                                        errorMessage = null
                                    )
                                }
                            }

                            is NetworkResult.Error -> {
                                _weatherState.update { currentWeatherState ->
                                    currentWeatherState.copy(
                                        currentWeather = null,
                                        isLoading = false,
                                        errorMessage = result.errorDetails.message
                                    )
                                }
                            }

                            else -> {}
                        }

                    }

            } ?: kotlin.run {
                _weatherState.update { currentWeatherState ->
                    currentWeatherState.copy(
                        isLoading = false,
                        errorMessage = "Couldn't retrieve location. Make sure to grant " +
                                "permission and enable GPS"
                    )
                }
            }

        }
    }


}