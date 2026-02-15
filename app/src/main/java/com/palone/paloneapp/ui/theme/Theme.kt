package com.palone.paloneapp.ui.theme

import android.app.Activity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowInsetsControllerCompat
import com.palone.paloneapp.settings.data.ThemeSettingsRepository

private val DarkBase = darkColors(
    primary = Color(0xFFD3D3D3),
    primaryVariant = Color(0xFF3C3644),
    secondary = Color(0xFFFFFFFF),
    background = Color(0xFF151E31),
    surface = Color(0xFF03A9F4),
    onBackground = Color(0xFFF9F1EF),
    onPrimary = Color(0xFFDFDFDF)
)

private val LightBase = lightColors(
    primary = Color(0xFF6F6F6F),
    primaryVariant = Color(0xFFFCFCFC),
    secondary = Color(0xFF1F1F1F),
    background = Color(0xFFF9F1EF),
    surface = Color(0xFF03A9F4),
    onBackground = Color(0xFFF9F1EF),
    onPrimary = Color(0xFF000000)
)

@Composable
fun PaloneAppTheme(
    isSystemDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val repo = remember { ThemeSettingsRepository(context) }

    val savedSurface = repo.surface.collectAsState(initial = DarkBase.surface.value.toInt()).value

    val base = if (isSystemDarkTheme) DarkBase else LightBase

    val dynamic = base.copy(
        surface = Color(savedSurface)
    )

    SideEffect {
        val window = (context as Activity).window
        window.navigationBarColor = dynamic.background.toArgb()
        WindowInsetsControllerCompat(window, window.decorView)
            .isAppearanceLightNavigationBars = dynamic.isLight
    }

    MaterialTheme(
        colors = dynamic,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
