package co.ke.weatherapp.domain.utils

import co.ke.weatherapp.domain.model.WeatherForecastDomain


fun WeatherForecastDomain.filterFor1000h(): WeatherForecastDomain {
    val filteredList = this.list.filter {
        it.dtTxt.endsWith("00:00:00")
    }

    return this.copy(
        list = filteredList
    )
}
