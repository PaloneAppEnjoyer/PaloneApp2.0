package com.palone.paloneapp.timetable.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Groups
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterActiveButtonFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(Icons.Filled.Groups, contentDescription = null, tint = MaterialTheme.colors.secondary)
        Icon(
            Icons.Filled.FilterAlt,
            contentDescription = null,
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier
                .offset(x = (-13).dp, y = (-3).dp)
                .height(13.dp)
        )

    }
}