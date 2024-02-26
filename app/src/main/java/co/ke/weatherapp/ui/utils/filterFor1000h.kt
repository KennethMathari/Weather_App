package co.ke.weatherapp.ui.utils

import co.ke.weatherapp.data.network.dto.WeatherForecast


fun WeatherForecast.filterFor1000h(): WeatherForecast{
    val filteredList = this.list.filter {
        it.dtTxt.endsWith("00:00:00")
    }

    return this.copy(
        list = filteredList
    )
}
