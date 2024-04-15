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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.ke.weatherapp.R
import co.ke.weatherapp.data.local.entities.FavouriteCityEntity
import co.ke.weatherapp.ui.utils.WeatherRoutes
import co.ke.weatherapp.ui.viewmodel.FavouriteCityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteCitiesScreen(
    onFavouriteCityClicked: (String) -> Unit,
    onNavBackClicked: () -> Unit,
    favouriteCityViewModel: FavouriteCityViewModel = hiltViewModel()
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(topBar = {
        TopAppBar(colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = {
            Text(stringResource(id = WeatherRoutes.FavouriteCities.title))
        }, navigationIcon = {
            IconButton(
                onClick = onNavBackClicked
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.navigate_back)
                )
            }
        }, scrollBehavior = scrollBehavior
        )
    }) {

        val favouriteCityState by favouriteCityViewModel.favouriteCityState.collectAsStateWithLifecycle()

        if (favouriteCityState.favouriteCities.isEmpty()) {
            EmptyFavouriteScreen(modifier = Modifier)
        }

        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            items(items = favouriteCityState.favouriteCities) { favouriteCityDomain ->
                Card(
                    elevation = CardDefaults.cardElevation(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable(onClick = {
                            onFavouriteCityClicked(favouriteCityDomain.cityName)
                        })
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
                            contentDescription = stringResource(R.string.delete_favourite_city),
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable(onClick = {
                                    val favouriteCityEntity = FavouriteCityEntity(
                                        id = favouriteCityDomain.id,
                                        cityName = favouriteCityDomain.cityName,
                                        latitude = favouriteCityDomain.latitude,
                                        longitude = favouriteCityDomain.longitude
                                    )
                                    favouriteCityViewModel.deleteFavouriteCity(favouriteCityEntity)
                                })
                        )
                        Icon(imageVector = Icons.Filled.Info,
                            contentDescription = stringResource(R.string.more_info),
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable(onClick = {
                                    //favouriteCityViewModel.getCityDetails(favouriteCityDomain.cityName)
                                    favouriteCityViewModel.getCityId(favouriteCityDomain.cityName)
                                }))
                    }


                }

            }
        }
    }
}