package com.palone.paloneapp.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.palone.paloneapp.feature_screen_substitutions.data.ScreensProperties
import com.palone.paloneapp.feature_screen_timetable.ui.TimetableScreen
import com.palone.paloneapp.ui.screens.AppSettingsScreen
import com.palone.paloneapp.ui.screens.SubstitutionsScreen

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PaloneApp(
    substitutionsViewModel: SubstitutionsViewModel,
    timetableViewModel: TimetableViewModel
) {
    // TODO add navigation
    // TODO switch between screens
//    SubstitutionsScreen(viewModel = substitutionsViewModel)
    val navHostController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navHostController,
        startDestination = ScreensProperties.SubstitutionsScreen.route
    ) {
        composable(
            ScreensProperties.SubstitutionsScreen.route,
            enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.End) },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Start
                )
            }) {
            SubstitutionsScreen(
                viewModel = substitutionsViewModel,
                navHostController = navHostController
            )
        }
        composable(
            ScreensProperties.TimetableScreen.route,
            enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Start) },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.End
                )
            }) {
            TimetableScreen(viewModel = timetableViewModel, navHostController = navHostController)
        }
        composable(ScreensProperties.SettingsScreen.route) {
            AppSettingsScreen()
        }
    }
}