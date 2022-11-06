package com.palone.paloneapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.palone.paloneapp.data.ScreensProperties
import com.palone.paloneapp.ui.screens.SubstitutionsScreen

@Composable
fun PaloneApp(viewModel: SubstitutionsViewModel) {
    // TODO add navigation
    // TODO switch between screens
    SubstitutionsScreen(viewModel = viewModel)
    NavHost(
        navController = rememberNavController(),
        startDestination = ScreensProperties.SubstitutionsScreen.route
    ) {
        composable(ScreensProperties.SubstitutionsScreen.route) {
            SubstitutionsScreen(viewModel = viewModel)
        }
        composable(ScreensProperties.TimetableScreen.route) {

        }
    }
}