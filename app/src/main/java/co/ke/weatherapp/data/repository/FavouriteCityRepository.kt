package co.ke.weatherapp.data.repository

import co.ke.weatherapp.data.local.entities.FavouriteCityEntity
import kotlinx.coroutines.flow.Flow

interface FavouriteCityRepository {

    suspend fun saveFavouriteCity(favouriteCityEntity: FavouriteCityEntity)

    fun getFavouriteCities(): Flow<List<FavouriteCityEntity>>

    suspend fun deleteCity(favouriteCityEntity: FavouriteCityEntity)
}