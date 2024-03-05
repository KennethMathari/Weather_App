package co.ke.weatherapp.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
    val badgeCount : Int? = null,
    val onItemClicked:()-> Unit,
    val route: String
)


val items = listOf(
    NavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Filled.Home,
        onItemClicked = {},
        route = WeatherRoutes.Weather.name
    ),
    NavigationItem(
        title = "Favourite Location",
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Filled.Favorite,
        onItemClicked = {},
        route = WeatherRoutes.FavouriteCities.name
    )
)