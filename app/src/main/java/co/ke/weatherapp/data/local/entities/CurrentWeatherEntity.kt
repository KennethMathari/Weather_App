package co.ke.weatherapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import co.ke.weatherapp.domain.model.MainDomain
import co.ke.weatherapp.domain.model.WeatherDomain

@Entity
data class CurrentWeatherEntity(
    @PrimaryKey
    val id: Int,
    val dt: Int,
    val main: MainDomain,
    val name: String,
    val weather: List<WeatherDomain>
)
