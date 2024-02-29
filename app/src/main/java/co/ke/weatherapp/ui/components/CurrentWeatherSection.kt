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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.ke.weatherapp.R
import co.ke.weatherapp.ui.state.WeatherInfo
import co.ke.weatherapp.ui.utils.convertKelvinToCelsius
import java.util.Locale

@Composable
fun CurrentWeatherSection(
    modifier: Modifier,
    weatherInfo: WeatherInfo
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            //Image part
            val image: Painter =
                painterResource(id = weatherInfo.weatherType.imageRes)
            Image(
                painter = image,
                contentDescription = null,
                modifier = modifier.fillMaxSize(),
                alignment = Alignment.BottomCenter
            )

            Box(
                modifier = modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_menu_24),
                    contentDescription = null,
                    modifier = modifier
                        .size(35.dp)
                        .clickable {},
                    tint = Color.White
                )

            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .align(Alignment.Center)
                    .padding(bottom = 80.dp, end = 15.dp)
            ) {
                Text(
                    text = weatherInfo.currentWeather.main.temp.convertKelvinToCelsius(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        fontSize = 70.sp
                    )
                )

                Text(
                    text = weatherInfo.weatherType.weatherDesc.uppercase(Locale.ROOT),
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 30.sp
                    )
                )
            }

            Row(
                modifier = modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.pin),
                    contentDescription = null,
                    modifier = modifier
                        .size(25.dp)
                        .padding(end = 5.dp),
                    tint = Color.White
                )
                Text(
                    text = weatherInfo.currentWeather.name, color = Color.White
                )
            }


        }
    }
}