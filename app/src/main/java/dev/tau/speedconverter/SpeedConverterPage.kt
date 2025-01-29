package dev.tau.speedconverter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeedConverterPage(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Speed Converter") }
            )
        }
    ) {
        Column(
            modifier = modifier.padding(it)
        ) {
            TextField(value = "", onValueChange = {}, label = { Text("Speed in km/h") })
        },
        modifier
    }
}

@Preview
@Composable
private fun SpeedConverterPagePreview() {
    SpeedConverterPage()
}