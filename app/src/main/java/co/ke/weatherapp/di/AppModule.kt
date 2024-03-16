package co.ke.weatherapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import co.ke.weatherapp.BuildConfig
import co.ke.weatherapp.data.local.WeatherDatabase
import co.ke.weatherapp.data.local.dao.FavouriteCityDao
import co.ke.weatherapp.data.network.services.WeatherApi
import co.ke.weatherapp.utils.Constants.BASE_URL
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.Places.initializeWithNewPlacesApiEnabled
import com.google.android.libraries.places.api.net.PlacesClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS).retryOnConnectionFailure(true).build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
    }

    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    fun provideFusedLocationClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideWeatherDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context, WeatherDatabase::class.java, WeatherDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideFavouriteCityDao(weatherDatabase: WeatherDatabase): FavouriteCityDao {
        return weatherDatabase.favouriteCityDao()
    }

    @Provides
    @Named("OpenWeatherApiKey")
    fun provideOpenWeatherApiKey(): String {
        return BuildConfig.OPEN_WEATHER_API_KEY
    }

    @Provides
    @Named("GoogleMapsApiKey")
    fun provideGoogleMapsApiKey(): String {
        return BuildConfig.GOOGLE_MAPS_API_KEY
    }

    @Provides
    fun providePlacesClient(
        @ApplicationContext context: Context, @Named("GoogleMapsApiKey") googleMapsApiKey: String
    ): PlacesClient {
        initializeWithNewPlacesApiEnabled(context, googleMapsApiKey)
        return Places.createClient(context)
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher
