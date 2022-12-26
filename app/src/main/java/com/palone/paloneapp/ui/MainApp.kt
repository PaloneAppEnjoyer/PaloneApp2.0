package com.palone.paloneapp.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.palone.paloneapp.data.ScreensProperties
import com.palone.paloneapp.substitutions.ui.SubstitutionsScreen
import com.palone.paloneapp.themeEditor.ThemeEditorScreen
import com.palone.paloneapp.timetable.ui.TimetableScreen
import com.palone.paloneapp.ui.screens.AppSettingsScreen

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PaloneApp(
    substitutionsViewModel: SubstitutionsViewModel,
    timetableViewModel: TimetableViewModel
) {
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
            AppSettingsScreen(navHostController = navHostController)
        }
        composable(ScreensProperties.ThemeEditorScreen.route) {
            ThemeEditorScreen(navHostController = navHostController)
        }
    }
}