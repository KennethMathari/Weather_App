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
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    modifier: Modifier = Modifier,
    weatherInfo: WeatherInfo,
    onLocationSearchClicked: (String) -> Unit
) {
    var locationInput by remember { mutableStateOf("") }

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
                    .clickable { },
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
                        fontSize = 70.sp
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
                        color = Color.White, style = TextStyle(
                            fontSize = 15.sp
                        )
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
                    Text(text = "Search Location...")
                },
                shape = RoundedCornerShape(percent = 50),
                maxLines = 1,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                trailingIcon = {
                    Icon(imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable(onClick = {
                            onLocationSearchClicked(locationInput)
                        }))
                })

        }
    }
}