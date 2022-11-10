package com.palone.paloneapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xFF6F6F6F),
    primaryVariant = Color(0xFFFCFCFC),//subs elements
    secondary = Color(0xFF9B2720),
    background = Color(0xFFF9F1EF),//Light Pink
    surface = Color(0xFF9B2720), //TEXT COLOR
    onBackground = Color(0xFFF9F1EF)
)

private val LightColorPalette = lightColors(
    primary = Color(0xFFF9F9F9),
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Color.LightGray,


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
    forceDarkTheme: Boolean = false,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme || forceDarkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}