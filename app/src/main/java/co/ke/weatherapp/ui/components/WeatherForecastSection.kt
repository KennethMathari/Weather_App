package co.ke.weatherapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.ke.weatherapp.ui.state.WeatherInfo
import co.ke.weatherapp.ui.utils.convertKelvinToCelsius
import co.ke.weatherapp.ui.utils.toDayOfWeek

@Composable
fun WeatherForecastSection(
    modifier: Modifier, weatherInfo: WeatherInfo
) {
    LazyColumn(
        modifier = modifier.padding(8.dp)
    ) {
        items(weatherInfo.weatherForecast.list) { weatherForecastInfoDomain ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = weatherForecastInfoDomain.dt.toDayOfWeek(),
                    modifier = Modifier.weight(1f),
                    style = TextStyle(
                        color = Color.White, fontSize = 20.sp
                    )
                )

                Image(
                    painter = painterResource(id = weatherInfo.weatherType.iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    alignment = Alignment.Center
                )

                Text(
                    text = weatherForecastInfoDomain.main.temp.convertKelvinToCelsius(),
                    modifier = Modifier.weight(1f),
                    style = TextStyle(
                        color = Color.White, fontSize = 20.sp
                    ),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}
