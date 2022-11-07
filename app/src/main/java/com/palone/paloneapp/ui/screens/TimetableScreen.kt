package com.palone.paloneapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.palone.paloneapp.ui.TimetableViewModel
import com.palone.paloneapp.ui.components.MainFloatingActionButton
import com.palone.paloneapp.ui.components.TopBar

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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(viewModel.uiState.value.timetableList?.size ?: 0) {
                    viewModel.uiState.collectAsState().value.timetableList?.sortedBy { it.className }
                        ?.forEach {
                            Card {
                                Column {
                                    Text(text = it.className)
                                    it.entries.sortedBy { it.dayInt.toString() + it.lessonFrom }
                                        .forEach { Text(text = "${it.dayName} ${it.lessonFrom} ${it.classroomsName} ${it.subjectShortName} ${it.teacherShortName} ${it.groupName}") }
                                }
                            }
                        }
                }


            }
        }


    }


}