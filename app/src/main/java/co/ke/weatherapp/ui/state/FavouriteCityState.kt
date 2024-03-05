package co.ke.weatherapp.ui.state

import co.ke.weatherapp.domain.model.FavouriteCityDomain

data class FavouriteCityState(
    val favouriteCities : List<FavouriteCityDomain> = emptyList()
)
