package com.naufal.testtheme.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.naufal.testtheme.ThemeViewModel

@Immutable
data class ExtendedColors(
    val primary: Color,
    val surface: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        primary = Color.Unspecified,
        surface = Color.Unspecified
    )
}

@Composable
fun ExtendedTheme(
    themeViewModel: ThemeViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val themeState by themeViewModel.themeState.collectAsState()

    var extendedColors = ExtendedColors(
        primary = Color.Blue,
        surface = Color.White
    )
    if (themeState.isDarkMode == true) {
        extendedColors = ExtendedColors(
            primary = Color.Red,
            surface = Color.Black
        )
    }

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            content = content
        )
    }
}

// Use with eg. ExtendedTheme.colors.blue
object ExtendedTheme {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}
