package com.palone.paloneapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
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
import com.palone.paloneapp.ui.components.DrawerItem
import com.palone.paloneapp.ui.components.MainFloatingActionButton
import com.palone.paloneapp.ui.components.TopBar
import com.palone.paloneapp.ui.components.timetable_screen.*

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TimetableScreen(viewModel: TimetableViewModel, navHostController: NavHostController) {
    val shouldShowSchoolClassFilterDialog = remember { mutableStateOf(false) }
    if (shouldShowSchoolClassFilterDialog.value) ClassFilterDialog(viewModel = viewModel) {
        shouldShowSchoolClassFilterDialog.value = false
    }
    val shouldShowHideGroupDialog = remember { mutableStateOf(false) }
    if (shouldShowHideGroupDialog.value) HideGroupDialog(viewModel = viewModel) {
        shouldShowHideGroupDialog.value = false
    }
    val shouldShowDisplayTeacherTimetableDialog = remember {
        mutableStateOf(false)
    }
    if (shouldShowDisplayTeacherTimetableDialog.value) DisplayTeacherTimetableDialog(viewModel.getRawTimetableList()) {
        shouldShowDisplayTeacherTimetableDialog.value = false
    }
    val shouldShowClassRoomNameTimetableDialog = remember {
        mutableStateOf(false)
    }
    if (shouldShowClassRoomNameTimetableDialog.value) DisplayClassRoomNameTimetableDialog(viewModel.getRawTimetableList()) {
        shouldShowClassRoomNameTimetableDialog.value = false
    }
    Scaffold(
        drawerBackgroundColor = MaterialTheme.colors.background,
        drawerContentColor = MaterialTheme.colors.secondary,
        scaffoldState = viewModel.uiState.collectAsState().value.scaffoldState,
        drawerContent = {
            DrawerItem(
                "Ukryj aktualnie wyświetlane grupy",
                onClick = { shouldShowHideGroupDialog.value = true })
            DrawerItem(title = "Wyszukaj nauczyciela") {
                shouldShowDisplayTeacherTimetableDialog.value = true
            }
            DrawerItem(title = "Wyszukaj po sali lekcyjnej") {
                shouldShowClassRoomNameTimetableDialog.value = true
            }
        },
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
                                data = scope.entries.filter {
                                    !viewModel.uiState.value.hiddenGroups.contains(
                                        it.groupName
                                    )
                                },
                                modifier = Modifier
                                    .padding(top = 5.dp), lessonNumber = scope.lessonNumber
                            )
                        }
                    }
                }
            }
            DaySelector(
                selectedDay = viewModel.uiState.collectAsState().value.selectedDay,
                onDaySelected = { viewModel.selectDay(it) },
                modifier = Modifier.offset(
                    x = LocalConfiguration.current.screenWidthDp.dp - 50.dp
                )
            )
            if (viewModel.uiState.collectAsState().value.hiddenGroups.isNotEmpty())
                FilterActiveButtonFloatingActionButton(
                    modifier = Modifier.offset(
                        x = LocalConfiguration.current.screenWidthDp.dp - 45.dp,
                        y = (LocalConfiguration.current.screenWidthDp.dp / 2) + 10.dp
                    )
                ) { shouldShowHideGroupDialog.value = true }
        }
    }
}

