package com.naufal.testtheme.ui.theme

import android.app.Activity
import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.naufal.testtheme.ThemeViewModel

@Stable
class CustomColors(
    primary: Color,
    secondary: Color,
    bg: Color
) {
    var primary by mutableStateOf(primary)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var bg by mutableStateOf(bg)
        private set

    fun update(other: CustomColors) {
        primary = other.primary
        secondary = other.secondary
        bg = other.bg
    }
}

internal val LocalCustomColors =
    staticCompositionLocalOf<CustomColors> { error("No CustomColors provided") }

// Composable for custom provider
@Composable
fun ProvideCustomColors(
    colors: CustomColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    colorPalette.update(colors)
    Log.e("HomeScreen1234", "theme: update colors")
    CompositionLocalProvider(LocalCustomColors provides colorPalette, content = content)
}

val LightThemeColors by lazy {
    CustomColors(
        primary = Purple80,
        secondary = Pink80,
        bg = Color.White
    )
}

val DarkThemeColors by lazy {
    CustomColors(
        primary = Color.Green,
        secondary = Color.Blue,
        bg = Color.Black
    )
}

object CustomTheme {
    val colors: CustomColors
        @Composable
        get() = LocalCustomColors.current
}

@Composable
fun CustomTheme(themeViewModel: ThemeViewModel = hiltViewModel(), content: @Composable () -> Unit) {
    val themeState by themeViewModel.themeState.collectAsState()

    val colors = when {
        themeState.isDarkMode == true -> DarkThemeColors
        else -> LightThemeColors
    }

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()

            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    Log.e("HomeScreen1234", "theme: ${themeState.isDarkMode}")

    ProvideCustomColors(colors = colors) {
        MaterialTheme(
            content = content
        )
    }
}
