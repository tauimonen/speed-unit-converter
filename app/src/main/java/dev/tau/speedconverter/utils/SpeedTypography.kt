package dev.tau.speedconverter.utils

import androidx.compose.material3.Typography
import androidx.compose.ui.unit.sp
import dev.tau.speedconverter.ui.theme.Typography

val speedTypography = Typography(
    displayLarge = Typography.displayLarge.copy(
        fontSize = 48.sp,
        fontFamily = googleFontFamily
    ),
    displayMedium = Typography.displayMedium.copy(
        fontSize = 34.sp,
        fontFamily = googleFontFamily
    ),
    displaySmall = Typography.displaySmall.copy(
        fontSize = 24.sp,
        fontFamily = googleFontFamily
    ),
)