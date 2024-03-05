package co.ke.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import co.ke.weatherapp.data.local.entities.FavouriteCityEntity

@Dao
interface FavouriteCityDao {

    @Query("SELECT * FROM favourite_city")
    suspend fun getFavouriteCities(): List<FavouriteCityEntity>

    @Upsert
    suspend fun saveFavouriteCity(vararg favouriteCityEntity: FavouriteCityEntity)

    @Delete
    suspend fun deleteCity(favouriteCityEntity: FavouriteCityEntity)

}
