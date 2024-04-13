package co.ke.weatherapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.ke.weatherapp.ui.components.CurrentWeatherMinMaxTempSection
import co.ke.weatherapp.ui.components.CurrentWeatherSection
import co.ke.weatherapp.ui.components.WeatherForecastSection
import co.ke.weatherapp.ui.utils.items
import co.ke.weatherapp.ui.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch


@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel,
    onDrawerItemClicked: (String) -> Unit,
) {
    val weatherState by weatherViewModel.weatherState.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    when {
        weatherState.isLoading -> LoadingScreen(modifier = modifier)
        weatherState.errorMessage != null -> ErrorScreen(
            modifier = modifier, errorMessage = weatherState.errorMessage ?: ""
        )

        else -> {

            ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
                ModalDrawerSheet {
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    items.forEachIndexed { index, navigationItem ->
                        NavigationDrawerItem(label = {
                            Text(text = navigationItem.title)
                        }, selected = index == selectedItemIndex, onClick = {
                            selectedItemIndex = index
                            if (navigationItem.title == "Home") {
                                weatherViewModel.getWeatherInfo()
                            } else {
                                onDrawerItemClicked(navigationItem.route)
                            }

                            scope.launch {
                                drawerState.close()
                            }
                        }, icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    navigationItem.selectedIcon
                                } else navigationItem.unselectedIcon,
                                contentDescription = navigationItem.title
                            )
                        }, badge = {
                            navigationItem.badgeCount?.let {
                                Text(text = navigationItem.badgeCount.toString())
                            }
                        }, modifier = modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            }) {


                weatherState.weatherInfo?.let { weatherInfo ->

                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .background(weatherInfo.weatherType.color)
                            .statusBarsPadding()
                            .navigationBarsPadding()
                    ) {
                        Row(
                            modifier = modifier
                                .weight(45f)
                                .fillMaxSize()
                        ) {
                            // First Row
                            CurrentWeatherSection(modifier = modifier,
                                weatherInfo = weatherInfo,
                                onLocationSearchClicked = {
                                    if (it.isNotBlank()) {
                                        scope.launch {
                                            weatherViewModel.getWeatherByCityName(it)
                                        }
                                    }
                                },
                                drawerState = drawerState,
                                scope = scope,
                                onSaveFavouriteCity = { favouriteCityEntity ->
                                    scope.launch {
                                        if (favouriteCityEntity != null) {
                                            weatherViewModel.saveFavouriteCity(favouriteCityEntity)
                                        }
                                    }

                                },
                                onDeleteFavouriteCity = { favouriteCityEntity ->
                                    scope.launch {
                                        if (favouriteCityEntity != null) {
                                            weatherViewModel.deleteFavouriteCity(favouriteCityEntity)
                                        }
                                    }
                                })
                        }

                        Row(
                            modifier = modifier
                                .weight(10f)
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .background(weatherInfo.weatherType.color)
                        ) {
                            // Second Row
                            CurrentWeatherMinMaxTempSection(
                                modifier = modifier, weatherInfo = weatherInfo
                            )

                        }

                        Row(
                            modifier = modifier
                                .weight(45f)
                                .fillMaxWidth()
                                .background(weatherInfo.weatherType.color)
                        ) {
                            // Third Row
                            Box(
                                modifier = modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .fillMaxWidth()
                            ) {
                                WeatherForecastSection(
                                    modifier = modifier, weatherInfo = weatherInfo
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


