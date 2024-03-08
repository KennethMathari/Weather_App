package co.ke.weatherapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.ke.weatherapp.R
import co.ke.weatherapp.data.local.entities.FavouriteCityEntity
import co.ke.weatherapp.ui.state.WeatherInfo
import co.ke.weatherapp.ui.utils.convertKelvinToCelsius
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun CurrentWeatherSection(
    modifier: Modifier = Modifier,
    weatherInfo: WeatherInfo,
    onLocationSearchClicked: (String) -> Unit,
    drawerState: DrawerState,
    scope: CoroutineScope,
    onSaveFavouriteCity: (FavouriteCityEntity?) -> Unit,
    onDeleteFavouriteCity: (FavouriteCityEntity?) -> Unit
) {
    var locationInput by rememberSaveable { mutableStateOf("") }
    var isFavouriteCity by rememberSaveable {
        mutableStateOf(weatherInfo.currentWeather?.isFavourite)
    }


    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            // Image
            val image: Painter = painterResource(id = weatherInfo.weatherType.imageRes)
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.BottomCenter
            )

            // Menu Icon
            Icon(
                painter = painterResource(id = R.drawable.baseline_menu_24),
                contentDescription = null,
                modifier = modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .size(45.dp)
                    .clickable(onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }),
                tint = Color.White
            )

            // Weather Info
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 70.dp, end = 15.dp)
            ) {
                Text(
                    text = weatherInfo.currentWeather?.main?.temp?.convertKelvinToCelsius() ?: "",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        fontSize = 60.sp
                    )
                )

                Text(
                    text = weatherInfo.weatherType.weatherDesc.uppercase(Locale.ROOT),
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 30.sp, fontWeight = FontWeight.Bold
                    )
                )

                Row {

                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = Color.White
                    )

                    Text(
                        text = weatherInfo.currentWeather?.name ?: "",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 15.sp
                        )
                    )
                    val favouriteIcon = if (isFavouriteCity == true) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    }

                    Icon(
                        imageVector = favouriteIcon,
                        contentDescription = stringResource(R.string.add_to_favourites),
                        tint = Color.White,
                        modifier = modifier
                            .padding(start = 5.dp)
                            .clickable(onClick = {
                                isFavouriteCity = !isFavouriteCity!!

                                if (isFavouriteCity == true) {
                                    //save favourite city
                                    onSaveFavouriteCity(weatherInfo.currentWeather?.let {
                                        FavouriteCityEntity(
                                            id = it.id,
                                            cityName = it.name,
                                            latitude = it.coord.lat.toString(),
                                            longitude = it.coord.lon.toString()
                                        )
                                    })
                                } else {
                                    //delete favourite city
                                    onDeleteFavouriteCity(weatherInfo.currentWeather?.let {
                                        FavouriteCityEntity(
                                            id = it.id,
                                            cityName = it.name,
                                            latitude = it.coord.lat.toString(),
                                            longitude = it.coord.lon.toString()
                                        )
                                    })

                                }

                            })
                    )
                }

            }

            // Search Location
            OutlinedTextField(value = locationInput,
                onValueChange = {
                    locationInput = it
                },
                textStyle = TextStyle(
                    color = Color.White
                ),
                placeholder = {
                    Text(
                        text = "Search Location...", style = TextStyle(
                            color = Color.White
                        )
                    )
                },
                shape = RoundedCornerShape(percent = 50),
                maxLines = 1,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable(onClick = {
                            onLocationSearchClicked(locationInput)
                        })
                    )
                })

        }
    }
}