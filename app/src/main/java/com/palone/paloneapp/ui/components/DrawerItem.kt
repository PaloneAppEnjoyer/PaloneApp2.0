package com.palone.paloneapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerItem(title: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                onClick()
            },
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.secondary
    ) {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = title)
        }

    }
}