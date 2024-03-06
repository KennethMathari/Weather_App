package co.ke.weatherapp.domain.model

data class CurrentWeatherDomain(
    val dt: Int,
    val id: Int,
    val main: MainDomain,
    var name: String,
    val coord: CoordDomain,
    val weather: List<WeatherDomain>,
    var isFavourite: Boolean
)
