package co.ke.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import co.ke.weatherapp.data.local.dao.FavouriteCityDao
import co.ke.weatherapp.data.local.entities.FavouriteCityEntity

@Database(
    entities = [
        FavouriteCityEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase: RoomDatabase() {

    companion object
    {
        const val DATABASE_NAME: String = "weather_db"
    }

    abstract fun favouriteCityDao(): FavouriteCityDao
}