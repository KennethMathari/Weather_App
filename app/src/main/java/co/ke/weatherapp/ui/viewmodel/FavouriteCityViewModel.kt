package co.ke.weatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.ke.weatherapp.data.local.entities.FavouriteCityEntity
import co.ke.weatherapp.data.repository.FavouriteCityRepository
import co.ke.weatherapp.di.IoDispatcher
import co.ke.weatherapp.domain.mappers.mapFavouriteCityEntityToDomain
import co.ke.weatherapp.domain.mappers.toDomainList
import co.ke.weatherapp.ui.state.FavouriteCityState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteCityViewModel @Inject constructor(
    private val favouriteCityRepository: FavouriteCityRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
):ViewModel() {

    private val _favouriteCityState = MutableStateFlow(FavouriteCityState())
    val favouriteCityState: StateFlow<FavouriteCityState> get() = _favouriteCityState.asStateFlow()

    init {
        getAllFavouriteCities()
    }

    private fun getAllFavouriteCities(){
        viewModelScope.launch(ioDispatcher) {
            val favouriteCities = favouriteCityRepository.getFavouriteCities()

            favouriteCities.map {
                _favouriteCityState.update {favouriteCityState->
                    favouriteCityState.copy(
                        favouriteCities =favouriteCities.toDomainList()
                    )

                }
            }
        }
    }

    fun deleteFavouriteCity(favouriteCityEntity: FavouriteCityEntity){
        viewModelScope.launch(ioDispatcher) {
            _favouriteCityState.value = _favouriteCityState.value.copy(
                favouriteCities = _favouriteCityState.value.favouriteCities.filterNot {
                    it == mapFavouriteCityEntityToDomain(favouriteCityEntity)
                }
            )
            favouriteCityRepository.deleteCity(favouriteCityEntity)
        }
    }
}