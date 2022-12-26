package com.palone.paloneapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SwapHorizontalCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.palone.paloneapp.ui.MainViewModel

@Composable
fun MainFloatingActionButton(viewModel: MainViewModel, navHostController: NavHostController) {
    FloatingActionButton(
        onClick = { viewModel.onFabClick(navHostController = navHostController) },
        contentColor = MaterialTheme.colors.onBackground,
        modifier = Modifier.size(70.dp),
        shape = RoundedCornerShape(100.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Rounded.SwapHorizontalCircle,
                contentDescription = "Switch",
                modifier = Modifier.size(40.dp)
            )
        }
    }
}