package com.palone.paloneapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.palone.paloneapp.ui.TimetableViewModel
import com.palone.paloneapp.ui.components.MainFloatingActionButton
import com.palone.paloneapp.ui.components.TopBar
import com.palone.paloneapp.ui.components.timetable_screen.TimetableElement

@Composable
fun TimetableScreen(viewModel: TimetableViewModel, navHostController: NavHostController) {
    Scaffold(
        topBar = { TopBar(viewModel = viewModel) },
        floatingActionButton = {
            MainFloatingActionButton(viewModel = viewModel, navHostController = navHostController)
        }) { padding ->
        SwipeRefresh(
            modifier = Modifier
                .padding(padding),
            state = rememberSwipeRefreshState(isRefreshing = viewModel.uiState.collectAsState().value.isLoading),
            onRefresh = { /*TODO*/ },
            swipeEnabled = true
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                viewModel.uiState.collectAsState().value.timetableList?.sortedBy { it.className }
                    ?.filter { it.className == "4ftg" }?.forEach {
                        it.day.forEach { it2 ->
                            Text(
                                text = it2.dayNameShorted,
                                color = MaterialTheme.colors.secondary,
                                fontSize = 15.sp
                            )
                            it2.lessons.forEach { it3 ->
                                Row {

                                    TimetableElement(
                                        data = it3.entries,
                                        modifier = Modifier
                                            .padding(top = 5.dp), it3.lessonNumber
                                    )
                                }
                            }
                        }
                    }
            }
        }
    }
}

