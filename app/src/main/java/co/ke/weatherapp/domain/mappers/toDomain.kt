package co.ke.weatherapp.domain.mappers

import co.ke.weatherapp.data.local.entities.FavouriteCityEntity
import co.ke.weatherapp.data.network.dto.Coord
import co.ke.weatherapp.data.network.dto.CurrentWeather
import co.ke.weatherapp.data.network.dto.Main
import co.ke.weatherapp.data.network.dto.Weather
import co.ke.weatherapp.data.network.dto.WeatherForecast
import co.ke.weatherapp.data.network.dto.WeatherForecastInfo
import co.ke.weatherapp.domain.model.CoordDomain
import co.ke.weatherapp.domain.model.CurrentWeatherDomain
import co.ke.weatherapp.domain.model.FavouriteCityDomain
import co.ke.weatherapp.domain.model.MainDomain
import co.ke.weatherapp.domain.model.WeatherDomain
import co.ke.weatherapp.domain.model.WeatherForecastDomain
import co.ke.weatherapp.domain.model.WeatherForecastInfoDomain



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


fun mapToCurrentWeatherDomain(currentWeather: CurrentWeather): CurrentWeatherDomain{
    return CurrentWeatherDomain(
        dt = currentWeather.dt,
        id = currentWeather.id,
        main = mapToMainDomain(currentWeather.main),
        coord = mapToCoordDomain(currentWeather.coord),
        name = currentWeather.name,
        weather = currentWeather.weather.map {
            mapToWeatherDomain(it)
        },
        isFavourite = false
    )
}

fun CurrentWeather.toDomain(): CurrentWeatherDomain{
    return CurrentWeatherDomain(
        dt = this.dt,
        id = this.id,
        main = mapToMainDomain(this.main),
        coord = mapToCoordDomain(this.coord),
        name = this.name,
        weather = this.weather.map {
            mapToWeatherDomain(it)
        },
        isFavourite = false
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

fun List<FavouriteCityEntity>.toDomainList(): List<FavouriteCityDomain>{
    return map {favouriteCityEntity ->
        FavouriteCityDomain(
            id = favouriteCityEntity.id,
            cityName = favouriteCityEntity.cityName,
            latitude = favouriteCityEntity.latitude,
            longitude = favouriteCityEntity.longitude
        )

    }
}

fun mapFavouriteCityEntityToDomain(entity: FavouriteCityEntity): FavouriteCityDomain {
    return FavouriteCityDomain(
        id = entity.id,
        cityName = entity.cityName,
        latitude = entity.latitude,
        longitude = entity.longitude
    )
}