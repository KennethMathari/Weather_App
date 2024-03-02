package co.ke.weatherapp.domain.mappers

import co.ke.weatherapp.data.network.dto.Coord
import co.ke.weatherapp.data.network.dto.CurrentWeather
import co.ke.weatherapp.data.network.dto.Main
import co.ke.weatherapp.data.network.dto.Weather
import co.ke.weatherapp.data.network.dto.WeatherForecast
import co.ke.weatherapp.data.network.dto.WeatherForecastInfo
import co.ke.weatherapp.data.network.utils.NetworkResult
import co.ke.weatherapp.domain.model.CoordDomain
import co.ke.weatherapp.domain.model.CurrentWeatherDomain
import co.ke.weatherapp.domain.model.MainDomain
import co.ke.weatherapp.domain.model.WeatherDomain
import co.ke.weatherapp.domain.model.WeatherForecastDomain
import co.ke.weatherapp.domain.model.WeatherForecastInfoDomain

fun mapToWeatherForecastDomain(networkResult: NetworkResult<WeatherForecast>): NetworkResult<WeatherForecastDomain> {
    return when (networkResult) {
        is NetworkResult.Success -> {
            val weatherForecastDomain = WeatherForecastDomain(
                list = networkResult.data.list.map {
                    mapToWeatherForecastInfoDomain(it)
                },
            )
            NetworkResult.Success(weatherForecastDomain)
        }

        is NetworkResult.Error -> NetworkResult.Error(networkResult.errorDetails)
        is NetworkResult.Loading -> {
            NetworkResult.Loading
        }
    }
}

fun mapToWeatherForecastInfoDomain(weatherForecastInfo: WeatherForecastInfo): WeatherForecastInfoDomain {
    return WeatherForecastInfoDomain(dt = weatherForecastInfo.dt,
        dtTxt = weatherForecastInfo.dtTxt,
        main = mapToMainDomain(weatherForecastInfo.main),
        weather = weatherForecastInfo.weather.map {
            mapToWeatherDomain(it)
        }

    )
}


fun mapToMainDomain(main: Main): MainDomain {
    return MainDomain(
        temp = main.temp ?: 0.0, tempMax = main.tempMax ?: 0.0, tempMin = main.tempMin ?: 0.0
    )
}

fun mapToWeatherDomain(weather: Weather): WeatherDomain {
    return WeatherDomain(
        description = weather.description ?: "", id = weather.id ?: 0, main = weather.main ?: ""
    )
}

fun mapToCurrentWeatherDomain(networkResult: NetworkResult<CurrentWeather>): NetworkResult<CurrentWeatherDomain> {
    return when (networkResult) {
        is NetworkResult.Success -> {
            val currentWeatherDomain = CurrentWeatherDomain(dt = networkResult.data.dt,
                id = networkResult.data.id,
                main = mapToMainDomain(networkResult.data.main),
                name = networkResult.data.name,
                weather = networkResult.data.weather.map {
                    mapToWeatherDomain(it)
                },
                coord = mapToCoordDomain(networkResult.data.coord)
            )
            NetworkResult.Success(currentWeatherDomain)
        }

        is NetworkResult.Error -> NetworkResult.Error(networkResult.errorDetails)
        is NetworkResult.Loading -> {
            NetworkResult.Loading
        }
    }
}

fun mapToCurrentWeatherDomain(currentWeather: CurrentWeather): CurrentWeatherDomain{
    return CurrentWeatherDomain(
        dt = currentWeather.dt,
        id = currentWeather.id,
        main = mapToMainDomain(currentWeather.main),
        coord = mapToCoordDomain(currentWeather.coord),
        name = currentWeather.name,
        weather = currentWeather.weather.map {
            mapToWeatherDomain(it)
        }
    )
}

fun mapToWeatherForecastDomain(weatherForecast: WeatherForecast): WeatherForecastDomain{
    return WeatherForecastDomain(
        list = weatherForecast.list.map {
            mapToWeatherForecastInfoDomain(it)
        }
    )
}

fun mapToCoordDomain(coord: Coord): CoordDomain{
    return CoordDomain(
        lat = coord.lat,
        lon = coord.lon
    )
}