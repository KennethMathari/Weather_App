package co.ke.weatherapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.ke.weatherapp.data.local.entities.FavouriteCityEntity
import co.ke.weatherapp.data.repository.FavouriteCityRepository
import co.ke.weatherapp.di.IoDispatcher
import co.ke.weatherapp.domain.mappers.toDomainList
import co.ke.weatherapp.ui.state.FavouriteCityState
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteCityViewModel @Inject constructor(
    private val favouriteCityRepository: FavouriteCityRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val placesClient: PlacesClient,
    private val autocompleteSessionToken: AutocompleteSessionToken
):ViewModel() {

    private val _favouriteCityState = MutableStateFlow(FavouriteCityState())
    val favouriteCityState: StateFlow<FavouriteCityState> get() = _favouriteCityState.asStateFlow()


    init {
        getAllFavouriteCities()
    }

    private fun getAllFavouriteCities(){
        viewModelScope.launch(ioDispatcher) {
            favouriteCityRepository.getFavouriteCities()
                .map {
                    it.toDomainList()
                }
                .collect{result->
                _favouriteCityState.update {favouriteCityState->
                    favouriteCityState.copy(
                        favouriteCities = result
                    )

                }
            }
        }
    }

    fun deleteFavouriteCity(favouriteCityEntity: FavouriteCityEntity){
        viewModelScope.launch(ioDispatcher) {
            favouriteCityRepository.deleteCity(favouriteCityEntity)
        }
    }

    private fun getCityDetails(cityId : String){
        val placeFields = listOf(Place.Field.ID, Place.Field.DINE_IN, Place.Field.RATING)
        val request = FetchPlaceRequest.builder(
            cityId,
            placeFields
        ).build()

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response->
                val place = response.place
                Log.e("PlaceDetails:", "$place")
            }
            .addOnFailureListener { exception->
                Log.e("PlaceDetailsError:","$exception")
            }

    }

    fun getCityId(cityName: String){
        val request = FindAutocompletePredictionsRequest.builder()
            .setTypesFilter(listOf(PlaceTypes.CITIES))
            .setSessionToken(autocompleteSessionToken)
            .setQuery(cityName)
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response->
                if (response != null && response.autocompletePredictions.isNotEmpty()) {
                    val placeId = response.autocompletePredictions[0].placeId
                    getCityDetails(placeId)
                    Log.e("Place:", placeId)
                }
            }
            .addOnFailureListener { exception->
                Log.e("PlaceError:","$exception")
            }
    }
}