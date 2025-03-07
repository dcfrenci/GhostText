package com.dcfrenci.ghosttext.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = NightWhite,
    secondary = NightGreenSecond,
    tertiary = NightGreenThird,

    onPrimary = NightBlack,
    onSecondary = NightSilver,
    onTertiary = NightSilver,

    surface = NightDarkGreen,
    background = NightBlack,

    onSurface = NightSilver,
    onBackground = NightSilver,
)

private val LightColorScheme = lightColorScheme(
    primary = Color.Green,
    secondary = Color.DarkGray,
    tertiary = Color.Magenta,
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40

//     Other default colors to override
    background = Color.Magenta,
    surface = Color.Blue,
    onPrimary = Color.Cyan,
    onSecondary = Color.Magenta,
    onTertiary = Color.Red,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)

@Composable
fun GhostTextTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}