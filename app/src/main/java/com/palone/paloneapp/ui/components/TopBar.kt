package com.palone.paloneapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.palone.paloneapp.ui.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun TopBar(
    viewModel: MainViewModel,
    additionalButton: @Composable () -> Unit = { Spacer(modifier = Modifier.width(0.dp)) }
) {
    val scope = rememberCoroutineScope()
    Card(
        backgroundColor = MaterialTheme.colors.secondary,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        contentColor = MaterialTheme.colors.onBackground,
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { scope.launch { viewModel.openDrawer() } }) {
                Icon(Icons.Default.Menu, "Drawer")

            }
            Text(text = "Palone 2.0")
            additionalButton()
        }
    }
}