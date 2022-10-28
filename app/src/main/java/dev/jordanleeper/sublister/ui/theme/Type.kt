package dev.jordanleeper.sublister.ui.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import dev.jordanleeper.sublister.R

val lato = FontFamily(
    Font(R.font.lato_regular),
    Font(R.font.lato_bold, FontWeight.Bold),
    Font(R.font.lato_light, FontWeight.Light),
    Font(R.font.lato_italic, style = FontStyle.Italic)
)

val default = Typography()
val Typography = Typography(
    headlineLarge = default.headlineLarge.copy(fontFamily = lato),
    headlineMedium = default.headlineMedium.copy(fontFamily = lato),
    headlineSmall = default.headlineSmall.copy(fontFamily = lato),
    bodyLarge = default.bodyLarge.copy(fontFamily = lato),
    bodyMedium = default.bodyMedium.copy(fontFamily = lato),
    bodySmall = default.bodySmall.copy(fontFamily = lato),
    titleLarge = default.titleLarge.copy(fontFamily = lato),
    titleMedium = default.titleMedium.copy(fontFamily = lato),
    titleSmall = default.titleSmall.copy(fontFamily = lato),
    labelLarge = default.labelLarge.copy(fontFamily = lato),
    labelMedium = default.labelMedium.copy(fontFamily = lato),
    labelSmall = default.labelSmall.copy(fontFamily = lato),
    displayLarge = default.displayLarge.copy(fontFamily = lato),
    displayMedium = default.displayMedium.copy(fontFamily = lato),
    displaySmall = default.displaySmall.copy(fontFamily = lato),
)
