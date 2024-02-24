package co.ke.weatherapp.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.ke.weatherapp.ui.state.WeatherState

@Composable
fun WeatherScreen(
    weatherState: WeatherState,
    modifier: Modifier = Modifier
){
    weatherState.currentWeather?.let { currentWeather->
        Text(text = currentWeather.name)
    }

    
}