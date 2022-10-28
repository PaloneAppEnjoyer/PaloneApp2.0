package com.palone.paloneapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SwapHorizontalCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.color.KalendarThemeColor
import com.himanshoe.kalendar.model.KalendarType
import com.palone.paloneapp.ui.MainViewModel
import com.palone.paloneapp.ui.components.home_screen.SubstitutionElement


@Composable
fun HomeScreen(viewModel: MainViewModel) {
    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(
            onClick = { /*TODO*/ },
            contentColor = MaterialTheme.colors.onBackground,
            modifier = Modifier.size(70.dp),
            shape = RoundedCornerShape(100.dp),
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Rounded.SwapHorizontalCircle,
                    contentDescription = "Switch",
                    modifier = Modifier.size(40.dp)
                )
            }
        }

    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Card(
                backgroundColor = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .padding(10.dp)
                    .padding(bottom = 0.dp)
                    .height(50.dp)
                    .fillMaxWidth(),
                contentColor = MaterialTheme.colors.onBackground,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Paloneeee")
            }
            Kalendar(modifier = Modifier.padding(10.dp),
                kalendarType = KalendarType.Oceanic,
                kalendarThemeColor = KalendarThemeColor(
                    MaterialTheme.colors.primaryVariant,
                    MaterialTheme.colors.secondary,
                    MaterialTheme.colors.secondary
                ),
                onCurrentDayClick = { kalendarDay, _ ->
                    viewModel.updateSelectedLocalDate(kalendarDay.localDate)
                    viewModel.refreshSubstitutionsDataWithLocalDate(kalendarDay.localDate)
                }
            )
            SwipeRefresh(
                modifier = Modifier
                    .padding(padding),
                swipeEnabled = true,
                state = rememberSwipeRefreshState(isRefreshing = viewModel.uiState.collectAsState().value.isLoading),
                onRefresh = {
                    viewModel.refreshSubstitutionsDataWithLocalDate(viewModel.selectedLocalDate.value)
                }, indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        contentColor = MaterialTheme.colors.onBackground,
                        fade = false,

                        )
                }) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    viewModel.uiState.collectAsState().value.substitutionsList?.forEach {
                        SubstitutionElement(substitutionData = it)
                    }
                }
            }
        }
    }

}