package co.ke.weatherapp.di

import co.ke.weatherapp.data.location.DefaultLocationTracker
import co.ke.weatherapp.data.repository.WeatherRepositoryImpl
import co.ke.weatherapp.domain.location.LocationTracker
import co.ke.weatherapp.domain.repository.WeatherRepository
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
}