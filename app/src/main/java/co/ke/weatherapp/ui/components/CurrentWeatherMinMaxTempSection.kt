package co.ke.weatherapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.ke.weatherapp.ui.state.WeatherInfo
import co.ke.weatherapp.ui.utils.convertKelvinToCelsius

@Composable
fun CurrentWeatherMinMaxTempSection(
    modifier: Modifier,
    weatherInfo: WeatherInfo
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TemperatureColumn(
                temperature = weatherInfo.currentWeather?.main?.tempMin ?: 0.0,
                label = "min"
            )

            TemperatureColumn(
                temperature = weatherInfo.currentWeather?.main?.temp ?: 0.0,
                label = "Current"
            )

            TemperatureColumn(
                temperature = weatherInfo.currentWeather?.main?.tempMax ?: 0.0,
                label = "max"
            )
        }

        Divider(
            thickness = 1.dp,
            color = Color.White
        )
    }
}

@Composable
private fun TemperatureColumn(
    temperature: Double,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = temperature.convertKelvinToCelsius(),
            color = Color.White,
            style = TextStyle(
                fontSize = 20.sp
            )
        )
        Text(
            text = label,
            color = Color.White,
            style = TextStyle(
                fontSize = 18.sp
            )
        )
    }
}
