package dev.jordanleeper.mylists.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/


@Composable
fun MyListsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val dynamic = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val context = LocalContext.current

    val colorScheme = when {
        dynamic && darkTheme -> dynamicDarkColorScheme(context)
        dynamic && !darkTheme -> dynamicLightColorScheme(context)
        darkTheme -> darkColorScheme(primary = Color.Black)
        else -> lightColorScheme(primary = Color.LightGray)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}