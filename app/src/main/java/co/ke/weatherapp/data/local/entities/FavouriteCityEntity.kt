package co.ke.weatherapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_city")
data class FavouriteCityEntity(
    @PrimaryKey
    val id: Int,
    val cityName : String,
    val latitude : String,
    val longitude : String
)
