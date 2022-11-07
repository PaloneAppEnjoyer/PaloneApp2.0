package com.palone.paloneapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.palone.paloneapp.data.ScreensProperties
import com.palone.paloneapp.ui.screens.SubstitutionsScreen
import com.palone.paloneapp.ui.screens.TimetableScreen

@Composable
fun PaloneApp(
    substitutionsViewModel: SubstitutionsViewModel,
    timetableViewModel: TimetableViewModel
) {
    // TODO add navigation
    // TODO switch between screens
//    SubstitutionsScreen(viewModel = substitutionsViewModel)
    val navHostController = rememberNavController()
    NavHost(
        navController = navHostController,
        startDestination = ScreensProperties.SubstitutionsScreen.route
    ) {
        composable(ScreensProperties.SubstitutionsScreen.route) {
            SubstitutionsScreen(
                viewModel = substitutionsViewModel,
                navHostController = navHostController
            )
        }
        composable(ScreensProperties.TimetableScreen.route) {
            TimetableScreen(viewModel = timetableViewModel, navHostController = navHostController)
        }
    }
}