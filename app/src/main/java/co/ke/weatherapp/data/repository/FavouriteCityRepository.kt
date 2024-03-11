package co.ke.weatherapp.data.repository

import co.ke.weatherapp.data.local.entities.FavouriteCityEntity

interface FavouriteCityRepository {

    suspend fun saveFavouriteCity(favouriteCityEntity: FavouriteCityEntity)

    suspend fun getFavouriteCities(): List<FavouriteCityEntity>

    suspend fun deleteCity(favouriteCityEntity: FavouriteCityEntity)
}