package dev.tau.speedconverter

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeedConverterPage(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Speed Converter") }
            )
        }
    ) { it ->
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = remember { FocusRequester() }
        var speedToBeConverted = remember { mutableStateOf("") }
        var convertedSpeed = remember { mutableStateOf("") }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TextField(
                value = speedToBeConverted.value,
                onValueChange = { speedToBeConverted.value = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                label = { Text("Speed in km/h") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
                    .focusRequester(focusRequester)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        val speed = speedToBeConverted.value.toDoubleOrNull() ?: return@Button
                        convertedSpeed.value = convertKmHToMinKm(speed)
                        Log.d("SpeedConverter", "Speed: $speed km/h, Min/km: ${convertedSpeed.value} min/km")
                    }
                ) {
                    Text("Convert")
                }

                Spacer(modifier = Modifier.width(16.dp))
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "${convertedSpeed.value} min/km",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }

        // Request focus and show keyboard when the page is launched
        androidx.compose.runtime.LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }
}

@SuppressLint("DefaultLocale")
fun convertKmHToMinKm(kmH: Double): String {
    val minKm = 60 / kmH
    Log.d("SpeedConverter", "convertKmHToMinKm: $minKm")
    return String.format("%.2f", minKm)
}



@Preview
@Composable
private fun SpeedConverterPagePreview() {
    SpeedConverterPage()
}