package com.palone.paloneapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorPalette = darkColors(
    primary = Color(0xFFD3D3D3),
    primaryVariant = Color(0xFF40434E),//subs elements
    secondary = Color(0xFFfff1e6),
    background = Color(0xFF25262C),//Light Pink
    surface = Color(0xFF702632), //TEXT COLOR
    onBackground = Color(0xFFF9F1EF),
    onPrimary = Color(0xFFDFDFDF)

)

private val LightColorPalette = lightColors(
    primary = Color(0xFF6F6F6F),
    primaryVariant = Color(0xFFFCFCFC),//subs elements
    secondary = Color(0xFF9B2720),
    background = Color(0xFFF9F1EF),//Light Pink
    surface = Color(0xFF9B2720), //TEXT COLOR
    onBackground = Color(0xFFF9F1EF),
    onPrimary = Color(0xFF000000)

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun PaloneAppTheme(
    isSystemDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (isSystemDarkTheme) DarkColorPalette else LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}