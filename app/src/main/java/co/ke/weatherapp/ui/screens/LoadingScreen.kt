package co.ke.weatherapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.ke.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun LoadingScreen(
    modifier: Modifier
){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = modifier.width(70.dp).height(70.dp).padding(10.dp),
            color = Color.Blue
        )
        Text(text = "Fetching Weather Information...")
    }
}

@Preview
@Composable
fun PreviewLoadingScreen(){
    WeatherAppTheme {
        LoadingScreen(modifier = Modifier)
    }

}