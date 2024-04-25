package co.ke.weatherapp.data.repository

import co.ke.weatherapp.data.local.dao.FavouriteCityDao
import co.ke.weatherapp.data.local.entities.FavouriteCityEntity
import co.ke.weatherapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavouriteCityRepositoryImpl @Inject constructor(
    private val favouriteCityDao: FavouriteCityDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FavouriteCityRepository {
    override suspend fun saveFavouriteCity(favouriteCityEntity: FavouriteCityEntity) {
        withContext(ioDispatcher) {
            favouriteCityDao.saveFavouriteCity(favouriteCityEntity)
        }
    }

    override fun getFavouriteCities(): Flow<List<FavouriteCityEntity>> {
        return favouriteCityDao.getFavouriteCities().flowOn(ioDispatcher)
    }

    override suspend fun deleteCity(favouriteCityEntity: FavouriteCityEntity) {
        withContext(ioDispatcher) {
            favouriteCityDao.deleteCity(favouriteCityEntity)
        }
    }
}