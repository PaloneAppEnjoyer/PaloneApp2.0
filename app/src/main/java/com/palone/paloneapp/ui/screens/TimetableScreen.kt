package com.palone.paloneapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.palone.paloneapp.ui.TimetableViewModel
import com.palone.paloneapp.ui.components.MainFloatingActionButton
import com.palone.paloneapp.ui.components.TopBar
import com.palone.paloneapp.ui.components.timetable_screen.ClassFilterDialog
import com.palone.paloneapp.ui.components.timetable_screen.DaySelector
import com.palone.paloneapp.ui.components.timetable_screen.TimetableElement

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TimetableScreen(viewModel: TimetableViewModel, navHostController: NavHostController) {
    val shouldShowSchoolClassFilterDialog = remember { mutableStateOf(false) }
    if (shouldShowSchoolClassFilterDialog.value) ClassFilterDialog(viewModel = viewModel) {
        shouldShowSchoolClassFilterDialog.value = false
    }
    Scaffold(
        topBar = {
            TopBar(viewModel = viewModel) {
                IconButton(onClick = { shouldShowSchoolClassFilterDialog.value = true }) {
                    Icon(Icons.Default.FilterAlt, "Filter")
                }
            }
        },
        floatingActionButton = {
            MainFloatingActionButton(viewModel = viewModel, navHostController = navHostController)
        }) { padding ->
        SwipeRefresh(
            modifier = Modifier
                .padding(padding),
            state = rememberSwipeRefreshState(isRefreshing = viewModel.uiState.collectAsState().value.isLoading),
            onRefresh = { /*TODO*/ },
            swipeEnabled = false
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                viewModel.uiState.collectAsState().value.lessonsList.forEach {
                    AnimatedContent(
                        targetState = it,
                        transitionSpec = { scaleIn() with fadeOut() }) { scope ->
                        Row {
                            TimetableElement(
                                data = scope.entries,
                                modifier = Modifier
                                    .padding(top = 5.dp), scope.lessonNumber
                            )
                        }
                    }
                }
            }
            DaySelector(
                viewModel = viewModel,
                modifier = Modifier.offset(
                    x = LocalConfiguration.current.screenWidthDp.dp - 50.dp
                )
            )
        }
    }
}

