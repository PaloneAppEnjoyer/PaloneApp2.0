package com.palone.paloneapp.ui.components.timetable_screen

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FilterActiveButtonFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(Icons.Filled.Groups, contentDescription = null, tint = MaterialTheme.colors.secondary)
    }
}