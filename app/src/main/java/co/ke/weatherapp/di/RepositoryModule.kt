package co.ke.weatherapp.di

import co.ke.weatherapp.data.repository.FavouriteCityRepository
import co.ke.weatherapp.data.repository.FavouriteCityRepositoryImpl
import co.ke.weatherapp.data.repository.WeatherRepository
import co.ke.weatherapp.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindFavouriteCityRepository(favouriteCityRepositoryImpl: FavouriteCityRepositoryImpl): FavouriteCityRepository
}