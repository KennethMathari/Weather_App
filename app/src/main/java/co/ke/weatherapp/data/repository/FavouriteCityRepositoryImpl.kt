package co.ke.weatherapp.data.repository

import co.ke.weatherapp.data.local.dao.FavouriteCityDao
import co.ke.weatherapp.data.local.entities.FavouriteCityEntity
import javax.inject.Inject

class FavouriteCityRepositoryImpl @Inject constructor(
    private val favouriteCityDao: FavouriteCityDao
): FavouriteCityRepository {
    override suspend fun saveFavouriteCity(favouriteCityEntity: FavouriteCityEntity) {
        favouriteCityDao.saveFavouriteCity(favouriteCityEntity)
    }

    override suspend fun getFavouriteCities(): List<FavouriteCityEntity> {
        return favouriteCityDao.getFavouriteCities()
    }

    override suspend fun deleteCity(favouriteCityEntity: FavouriteCityEntity) {
        favouriteCityDao.deleteCity(favouriteCityEntity)
    }
}