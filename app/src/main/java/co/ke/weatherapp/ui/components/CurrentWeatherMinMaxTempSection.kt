package co.ke.weatherapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
    modifier: Modifier, weatherInfo: WeatherInfo
) {

    Column {
        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxHeight()
                .fillMaxWidth()
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = weatherInfo.currentWeather.main.tempMin.convertKelvinToCelsius(),
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                    Text(
                        text = "min", color = Color.White, style = TextStyle(
                            fontSize = 18.sp
                        )
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = weatherInfo.currentWeather.main.temp.convertKelvinToCelsius(),
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                    Text(
                        text = "Current", color = Color.White, style = TextStyle(
                            fontSize = 18.sp
                        )
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = weatherInfo.currentWeather.main.tempMax.convertKelvinToCelsius(),
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                    Text(
                        text = "max", color = Color.White, style = TextStyle(
                            fontSize = 18.sp
                        )
                    )
                }
            }
        }

        Divider(
            thickness = 1.dp, color = Color.White
        )
    }
}