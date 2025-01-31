package dev.tau.speedconverter

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.tau.speedconverter.utils.speedTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeedConverterPage(modifier: Modifier = Modifier) {
    val backgroundImage: Painter = painterResource(id = R.drawable.running)  // Background image
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    var speedToBeConverted = remember { mutableStateOf("") }
    var convertedSpeed = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Speed Converter",
                        style = speedTypography.displaySmall
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = colorResource(id = R.color.light_blue)
                )
            )
        }
    ) { it ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            // Background image
            Image(
                painter = backgroundImage,
                contentDescription = "Running image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxHeight()
            )

            // Content placed on top of the background image
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()), // Added scrolling
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
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
                    Text(
                        text = formatSpeedToMinutesAndSeconds(convertedSpeed.value),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }

        // Request focus and show keyboard when the page is launched
        LaunchedEffect(Unit) {
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
    // Change comma to period if necessary
    val cleanedSpeed = speed.trim().replace(',', '.')

    // Try to convert the input to a decimal number
    val speedDouble = cleanedSpeed.toDoubleOrNull()

    if (speedDouble == null || speedDouble <= 0) {
        return "Invalid speed"
    }

    // Calculate minutes and seconds
    val minutes = speedDouble.toInt()
    val seconds = ((speedDouble - minutes) * 60).toInt()

    // If minutes are 0, show only seconds
    return if (minutes == 0) {
        "$seconds s / km"
    } else if (seconds == 0) {
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
            // Change any comma in the input to a period and convert to Double
            val cleanedSpeed = speedToBeConverted.value.trim().replace(',', '.')
            val speed = cleanedSpeed.toDoubleOrNull() ?: return@Button
            convertedSpeed.value = convertKmHToMinKm(speed)
        },
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.light_blue),
            contentColor = Color.White
        ),
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
