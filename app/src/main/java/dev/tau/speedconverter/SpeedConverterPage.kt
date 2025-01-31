package dev.tau.speedconverter

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = speedToBeConverted.value,
                onValueChange = { speedToBeConverted.value = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                label = { Text("Speed in km/h") },
                modifier = Modifier
                    .size(200.dp, 100.dp)
                    .padding(16.dp)
                    .focusRequester(focusRequester)
            )

            MyFilledButton(
                speedToBeConverted,
                convertedSpeed,
                modifier = Modifier
                    .size(220.dp, 80.dp)
                    .padding(8.dp),
                contentPadding = PaddingValues(16.dp)
            )

            if (convertedSpeed.value.isNotEmpty()) {
                Log.d("SpeedConverter", "SpeedConverterPage: ${convertedSpeed.value}")
                Text(
                    text = formatSpeedToMinutesAndSeconds(convertedSpeed.value),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
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

fun formatSpeedToMinutesAndSeconds(speed: String): String {
    // Vaihdetaan mahdollinen pilkku pisteeksi
    val cleanedSpeed = speed.trim().replace(',', '.')

    // Yritetään muuntaa syöte desimaaliluvuksi
    val speedDouble = cleanedSpeed.toDoubleOrNull()

    if (speedDouble == null || speedDouble <= 0) {
        return "Virheellinen nopeus"
    }

    // Lasketaan minuutit ja sekunnit
    val minutes = speedDouble.toInt()
    val seconds = ((speedDouble - minutes) * 60).toInt()

    // Jos sekunteja ei ole, tulostetaan vain minuutit
    return if (seconds == 0) {
        "$minutes min / km"
    } else {
        "$minutes min $seconds s / km"
    }
}


@Composable
fun MyFilledButton(
    speedToBeConverted: MutableState<String>,
    convertedSpeed: MutableState<String>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource? = null
) {
    Button(
        onClick = {
            // Vaihdetaan mahdollinen syöteen pilkku pisteeksi ja muunnetaa Doubleksi
            val cleanedSpeed = speedToBeConverted.value.trim().replace(',', '.')
            val speed = cleanedSpeed.toDoubleOrNull() ?: return@Button
            convertedSpeed.value = convertKmHToMinKm(speed)
        },
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    ) {
        Text("CONVERT",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold )
    }
}

@Preview
@Composable
private fun SpeedConverterPagePreview() {
    SpeedConverterPage()
}