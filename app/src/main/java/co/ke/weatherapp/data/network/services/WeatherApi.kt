package co.ke.weatherapp.data.network.services

import co.ke.weatherapp.data.network.dto.CurrentWeather
import co.ke.weatherapp.data.network.dto.WeatherForecast
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") apiKey: String
    ): CurrentWeather

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") apiKey: String
    ): WeatherForecast

    @GET("weather")
    suspend fun getWeatherByCityName(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): CurrentWeather
}