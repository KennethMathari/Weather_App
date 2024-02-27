package co.ke.weatherapp.domain.model

data class CurrentWeatherDomain(
    val dt: Int,
    val id: Int,
    val main: MainDomain,
    val name: String,
    val weather: List<WeatherDomain>,
)
