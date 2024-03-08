package co.ke.weatherapp.ui.screens

import android.util.Log
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
import co.ke.weatherapp.ui.viewmodel.FavouriteCityViewModel

@Composable
fun FavouriteCitiesScreen(
    favouriteCityViewModel: FavouriteCityViewModel,
    onFavouriteCityClicked: (String) -> Unit
) {
    val favouriteCityState by favouriteCityViewModel.favouriteCityState.collectAsStateWithLifecycle()

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
                        modifier = Modifier.weight(1f)
                            .padding(10.dp))

                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete Favourite City",
                        modifier = Modifier.weight(1f)
                            .padding(10.dp)
                    )
                }


            }

        }
    }
}