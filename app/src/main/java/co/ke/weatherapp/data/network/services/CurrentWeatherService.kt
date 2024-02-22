package co.ke.weatherapp.data.network.services

import co.ke.weatherapp.data.network.models.CurrentWeather
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrentWeatherService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude : Double,
        @Query("appid") apiKey: String
    ): CurrentWeather
}