package co.ke.weatherapp.data.network.services

import co.ke.weatherapp.data.network.dto.CurrentWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude : Double,
        @Query("appid") apiKey: String
    ): CurrentWeather
}