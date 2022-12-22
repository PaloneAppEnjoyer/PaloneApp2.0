package com.palone.paloneapp.timetable.ui

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
import com.palone.paloneapp.substitutions.data.ScreensProperties
import com.palone.paloneapp.timetable.ui.components.*
import com.palone.paloneapp.ui.TimetableViewModel
import com.palone.paloneapp.ui.components.DrawerItem
import com.palone.paloneapp.ui.components.MainFloatingActionButton
import com.palone.paloneapp.ui.components.TopBar
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TimetableScreen(viewModel: TimetableViewModel, navHostController: NavHostController) {
    val shouldShowSchoolClassFilterDialog =
        remember { mutableStateOf(viewModel.uiState.value.selectedSchoolClass == "") }
    val calendarTodayDayOfWeek = remember {
        mutableStateOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))
    }
    if (shouldShowSchoolClassFilterDialog.value && viewModel.getAllSchoolClassesNames()
            .isNotEmpty()
    ) ClassFilterDialog(viewModel = viewModel) {
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
            DrawerItem(
                title = "Opcje",
                onClick = { navHostController.navigate(ScreensProperties.SettingsScreen.route) })
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

                viewModel.uiState.collectAsState().value.lessonsList.forEach { it2 ->
                    AnimatedContent(
                        targetState = it2,
                        transitionSpec = { scaleIn() with fadeOut() }) { scope ->
                        Row {
                            val data = scope.entries.filter {
                                !viewModel.uiState.value.hiddenGroups.contains(
                                    it.groupName
                                )
                            }
                            val substitutions =
                                viewModel.getSubstitutionsForSelectedLesson(scope.lessonNumber)
                                    .filter {
                                        data.any { it2 ->
                                            it.subject.contains(
                                                it2.groupName.replace(".", "")
                                            ) || it2.groupName == "Cała klasa"
                                        }
                                    }//TODO ("Ability to disable this filter because it may break some substitutions in the future")
                            TimetableElement(
                                data = data,
                                modifier = Modifier
                                    .padding(top = 5.dp),
                                lessonNumber = scope.lessonNumber,
                                currentLesson = viewModel.uiState.collectAsState().value.currentLesson,
                                todayDayInWeek = calendarTodayDayOfWeek.value,
                                substitutions = substitutions,
                                areSubstitutionsForTomorrow = viewModel.uiState.collectAsState().value.selectedDay != viewModel.uiState.collectAsState().value.todayDate.day_of_week_name
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .offset(
                        x = LocalConfiguration.current.screenWidthDp.dp - 50.dp
                    )
                    .fillMaxHeight(), verticalArrangement = Arrangement.Center
            ) {
                if (viewModel.uiState.collectAsState().value.hiddenGroups.isNotEmpty())
                    FilterActiveButtonFloatingActionButton(
                        modifier = Modifier
                            .size(20.dp)
                            .offset(x = 18.dp)
                    ) { shouldShowHideGroupDialog.value = true }
                else
                    Spacer(modifier = Modifier.size(20.dp))
                DaySelector(
                    selectedDay = viewModel.uiState.collectAsState().value.selectedDay,
                    onDaySelected = { viewModel.selectDay(it) },
                )
            }


        }
    }
}

