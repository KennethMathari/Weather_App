package co.ke.weatherapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.ke.weatherapp.data.local.entities.FavouriteCityEntity
import co.ke.weatherapp.ui.viewmodel.FavouriteCityViewModel

@Composable
fun FavouriteCitiesScreen(
    favouriteCityViewModel: FavouriteCityViewModel,
    onFavouriteCityClicked: (String) -> Unit,
    onFavouriteCityDelete:(FavouriteCityEntity)-> Unit
) {
    val favouriteCityState by favouriteCityViewModel.favouriteCityState.collectAsStateWithLifecycle()

    if (favouriteCityState.favouriteCities.isEmpty()) {
        EmptyFavouriteScreen(modifier = Modifier)
    }

    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        items(items = favouriteCityState.favouriteCities) { favouriteCityDomain ->
            Card(
                elevation = CardDefaults.cardElevation(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clickable(
                        onClick = {
                            onFavouriteCityClicked(favouriteCityDomain.cityName)
                        }
                    )
            ) {
                Row {
                    Text(
                        text = favouriteCityDomain.cityName,
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(1f)

                    )

                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete Favourite City",
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable(
                                onClick = {
                                    val favouriteCityEntity = FavouriteCityEntity(
                                        id = favouriteCityDomain.id,
                                        cityName = favouriteCityDomain.cityName,
                                        latitude = favouriteCityDomain.latitude,
                                        longitude = favouriteCityDomain.longitude
                                    )
                                    onFavouriteCityDelete(favouriteCityEntity)
                                }
                            )
                    )
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "More info",
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }


            }

        }
    }
}