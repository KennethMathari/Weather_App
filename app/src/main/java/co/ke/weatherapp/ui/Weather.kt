package co.ke.weatherapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.ke.weatherapp.ui.screens.WeatherScreen
import co.ke.weatherapp.ui.state.WeatherState
import co.ke.weatherapp.ui.utils.WeatherRoutes
import co.ke.weatherapp.ui.viewmodel.WeatherViewModel

@Composable
fun Weather(
    weatherViewModel: WeatherViewModel,
    navController: NavHostController = rememberNavController(),
) {
    val weatherState: WeatherState by weatherViewModel.weatherState.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = WeatherRoutes.Weather.name) {

        composable(route = WeatherRoutes.Weather.name) {
            WeatherScreen(weatherState)
        }
    }

}