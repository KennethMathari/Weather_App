package co.ke.weatherapp.data.repository

import co.ke.weatherapp.data.local.dao.FavouriteCityDao
import co.ke.weatherapp.data.local.entities.FavouriteCityEntity
import co.ke.weatherapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FavouriteCityRepositoryImpl @Inject constructor(
    private val favouriteCityDao: FavouriteCityDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FavouriteCityRepository {
    override suspend fun saveFavouriteCity(favouriteCityEntity: FavouriteCityEntity) {
        favouriteCityDao.saveFavouriteCity(favouriteCityEntity)
    }

    override suspend fun getFavouriteCities(): Flow<List<FavouriteCityEntity>> {
        return favouriteCityDao.getFavouriteCities().flowOn(ioDispatcher)
    }

    override suspend fun deleteCity(favouriteCityEntity: FavouriteCityEntity) {
        favouriteCityDao.deleteCity(favouriteCityEntity)
    }
}