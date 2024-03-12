package co.ke.weatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.ke.weatherapp.BuildConfig
import co.ke.weatherapp.data.local.entities.FavouriteCityEntity
import co.ke.weatherapp.data.location.LocationTracker
import co.ke.weatherapp.data.network.utils.NetworkResult
import co.ke.weatherapp.data.repository.FavouriteCityRepository
import co.ke.weatherapp.data.repository.WeatherRepository
import co.ke.weatherapp.di.IoDispatcher
import co.ke.weatherapp.domain.WeatherType.Companion.getWeatherType
import co.ke.weatherapp.domain.mappers.mapToCurrentWeatherDomain
import co.ke.weatherapp.domain.mappers.mapToWeatherForecastDomain
import co.ke.weatherapp.domain.model.CurrentWeatherDomain
import co.ke.weatherapp.domain.utils.filterFor1000h
import co.ke.weatherapp.ui.state.WeatherInfo
import co.ke.weatherapp.ui.state.WeatherState
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
    private val favouriteCityRepository: FavouriteCityRepository
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
                                        currentWeather = CurrentWeatherDomain(
                                            dt = currentWeather.data.dt,
                                            id = currentWeather.data.id,
                                            main = currentWeather.data.main,
                                            name = currentWeather.data.name,
                                            coord = currentWeather.data.coord,
                                            isFavourite = isFavouriteCity(currentWeather.data),
                                            //isFavourite = false,
                                            weather = currentWeather.data.weather
                                        ),
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

    fun getWeatherByCityName(cityName: String) {
        viewModelScope.launch(ioDispatcher) {
            weatherRepository.getWeatherByCityName(cityName, apiKey).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _weatherState.update { currentWeatherState ->
                            val weatherInfo = result.data.currentWeather?.let { currentWeather ->
                                val weatherType = getWeatherType(currentWeather.weather[0].id)
                                WeatherInfo(
                                    weatherForecast = result.data.weatherForecast,
                                    currentWeather = CurrentWeatherDomain(
                                        dt = currentWeather.dt,
                                        id = currentWeather.id,
                                        main = currentWeather.main,
                                        name = currentWeather.name,
                                        coord = currentWeather.coord,
                                        weather = currentWeather.weather,
                                        isFavourite = isFavouriteCity(currentWeather)
                                        //isFavourite = false
                                    ),
                                    weatherType = weatherType
                                )
                            }

                            currentWeatherState.copy(
                                weatherInfo = weatherInfo, isLoading = false, errorMessage = null
                            )

                        }
                    }

                    is NetworkResult.Error -> {
                        _weatherState.update { currentWeatherState ->
                            currentWeatherState.copy(
                                weatherInfo = null,
                                isLoading = false,
                                errorMessage = result.errorDetails.message
                            )
                        }
                    }

                    is NetworkResult.Loading -> {
                        _weatherState.update { currentWeatherState ->
                            currentWeatherState.copy(
                                weatherInfo = null, isLoading = true, errorMessage = null
                            )
                        }
                    }
                }

            }

        }
    }

    suspend fun saveFavouriteCity(favouriteCityEntity: FavouriteCityEntity) {
        viewModelScope.launch(ioDispatcher) {
            val currentState = _weatherState.value
            val updatedWeatherInfo = currentState.weatherInfo?.copy(
                currentWeather = currentState.weatherInfo.currentWeather?.copy(
                    isFavourite = true
                )
            )

            _weatherState.value = currentState.copy(
                weatherInfo = updatedWeatherInfo
            )
            favouriteCityRepository.saveFavouriteCity(favouriteCityEntity)
        }
    }

    suspend fun deleteFavouriteCity(favouriteCityEntity: FavouriteCityEntity) {
        viewModelScope.launch(ioDispatcher) {
            val currentState = _weatherState.value
            val updatedWeatherInfo = currentState.weatherInfo?.copy(
                currentWeather = currentState.weatherInfo.currentWeather?.copy(
                    isFavourite = false
                )
            )

            _weatherState.value = currentState.copy(
                weatherInfo = updatedWeatherInfo
            )
            favouriteCityRepository.deleteCity(favouriteCityEntity)
        }
    }

    private fun isFavouriteCity(currentWeatherDomain: CurrentWeatherDomain): Boolean {
        var isFavourite = true
        viewModelScope.launch(ioDispatcher) {
            favouriteCityRepository.getFavouriteCities().collect {
                isFavourite = it.contains(
                    FavouriteCityEntity(
                        id = currentWeatherDomain.id,
                        cityName = currentWeatherDomain.name,
                        latitude = currentWeatherDomain.coord.lat.toString(),
                        longitude = currentWeatherDomain.coord.lon.toString()
                    )
                )
            }
        }
        return isFavourite
    }

}
