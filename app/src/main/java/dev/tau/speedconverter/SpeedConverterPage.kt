package dev.tau.speedconverter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
        val speedToBeConverted = remember { mutableStateOf("") }

        Column(
            modifier = modifier.padding(it)
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
        }

        // Request focus and show keyboard when the page is launched
        androidx.compose.runtime.LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }
}


@Preview
@Composable
private fun SpeedConverterPagePreview() {
    SpeedConverterPage()
}