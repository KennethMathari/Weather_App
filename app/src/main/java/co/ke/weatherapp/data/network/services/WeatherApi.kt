package co.ke.weatherapp.data.network.services

import co.ke.weatherapp.data.network.dto.CurrentWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude : String,
        @Query("appid") apiKey: String
    ): CurrentWeather
}