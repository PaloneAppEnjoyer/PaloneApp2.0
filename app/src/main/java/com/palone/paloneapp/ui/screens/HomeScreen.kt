package com.palone.paloneapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.palone.paloneapp.ui.MainViewModel

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    Scaffold { padding ->
        SwipeRefresh(
            modifier = Modifier
                .padding(padding),
            swipeEnabled = true,
            state = rememberSwipeRefreshState(isRefreshing = viewModel.uiState.collectAsState().value.isLoading),
            onRefresh = {
                viewModel.refreshSubstitutionsWithDayOffset(0)
            }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {

                viewModel.uiState.collectAsState().value.substitutionsList?.forEach {
                    Row {
                        Text(
                            text = it.className ?: "aa", modifier = Modifier.padding(10.dp)
                        )
                        it.lesson?.forEach {
                            Text(
                                text = it, modifier = Modifier.padding(10.dp)
                            )
                        }
                        it.description?.forEach {
                            Text(
                                text = it, modifier = Modifier.padding(10.dp)
                            )
                        }
                    }

                }


            }


        }
    }

}