package com.palone.paloneapp.ui

import androidx.compose.runtime.Composable
import com.palone.paloneapp.ui.screens.HomeScreen

@Composable
fun PaloneApp(viewModel: MainViewModel) {
    // TODO add navigation
    // TODO switch between screens
    HomeScreen(viewModel = viewModel)
}