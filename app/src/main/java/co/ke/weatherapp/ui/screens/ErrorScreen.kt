package co.ke.weatherapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.ke.weatherapp.R

@Composable
fun ErrorScreen(
    modifier : Modifier,
    onReturnToHomePageClicked:()-> Unit){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.an_error_occurred))
        Button(
            onClick = { onReturnToHomePageClicked() },
            modifier = modifier.padding(8.dp)) {
            Text(text = "Return to Home Page")
        }
    }
}