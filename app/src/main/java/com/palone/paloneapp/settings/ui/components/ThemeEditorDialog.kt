package com.palone.paloneapp.settings.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ThemeEditorDialog(onDismissRequest: () -> Unit = {}) {
    val colorNames = listOf(
        listOf(MaterialTheme.colors.primary, "primary"),
        listOf(MaterialTheme.colors.primaryVariant, "primaryVariant"),
        listOf(MaterialTheme.colors.secondary, "secondary"),
        listOf(MaterialTheme.colors.background, "background"),
        listOf(MaterialTheme.colors.surface, "surface"),
        listOf(MaterialTheme.colors.onBackground, "onBackground")
    )
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Kiedyś będzie działać")
            colorNames.forEach {
                Row {
                    Card(backgroundColor = it[0] as Color, modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clickable { }) {
                    }

                }
            }
        }
    }
}
//val colors = darkColors(
//    primary = Color(0xFF6F6F6F),
//    primaryVariant = Color(0xFFFCFCFC),//subs elements
//    secondary = Color(0xFF9B2720),
//    background = Color(0xFFF9F1EF),//Light Pink
//    surface = Color(0xFF9B2720), //TEXT COLOR
//    onBackground = Color(0xFFF9F1EF)
//)