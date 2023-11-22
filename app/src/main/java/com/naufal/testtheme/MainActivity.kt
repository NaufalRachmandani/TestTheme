package com.naufal.testtheme

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.naufal.testtheme.ui.theme.CustomTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val themeState by themeViewModel.themeState.collectAsState()

            HomeScreen(
                themeState = themeState,
                onToggleTheme = {
                    themeViewModel.toggleTheme()
                }
            )
        }
    }
}

@Composable
fun HomeScreen(
    themeState: ThemeViewModel.ThemeState = ThemeViewModel.ThemeState(),
    onToggleTheme: () -> Unit = {}
) {
    Log.e("HomeScreen1234", "HomeScreen: ${themeState.isDarkMode}")

    CustomTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CustomTheme.colors.bg
        ) {
            val colorPrimary = CustomTheme.colors.primary
            val colorSecondary = CustomTheme.colors.secondary

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .drawBehind {
                            drawCircle(color = colorPrimary)
                        }
                )

                Spacer(modifier = Modifier.height(30.dp))

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .drawBehind {
                            drawCircle(color = colorSecondary)
                        }
                )

                Spacer(modifier = Modifier.height(100.dp))

                Button(onClick = { onToggleTheme() }) {
                    Text(
                        text = if (themeState.isDarkMode == true) {
                            "Matikan dark mode"
                        } else {
                            "Hidupkan dark mode"
                        },
                        color = CustomTheme.colors.primary
                    )
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
