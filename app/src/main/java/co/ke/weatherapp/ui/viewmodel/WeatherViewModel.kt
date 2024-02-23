package co.ke.weatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.ke.weatherapp.data.network.utils.NetworkResult
import co.ke.weatherapp.data.repository.CurrentWeatherRepository
import co.ke.weatherapp.ui.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val currentWeatherRepository: CurrentWeatherRepository
) : ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> get() = _weatherState.asStateFlow()


    //    init {
//        getCurrentWeather()
//    }
    private fun getCurrentWeather(latitude: Double, longitude: Double, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {

            currentWeatherRepository.getCurrentWeather(
                latitude,
                longitude,
                apiKey
            ).buffer()
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            _weatherState.update { currentWeatherState ->
                                currentWeatherState.copy(
                                    currentWeather = result.data
                                )
                            }
                        }

                        is NetworkResult.Loading -> {
                            _weatherState.update { currentWeatherState ->
                                currentWeatherState.copy(
                                    isLoading = true
                                )
                            }
                        }

                        is NetworkResult.Error -> {
                            _weatherState.update { currentWeatherState ->
                                currentWeatherState.copy(
                                    errorMessage = result.errorDetails.message
                                )
                            }
                        }
                    }

                }

        }
    }


}