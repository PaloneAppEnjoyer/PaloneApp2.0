package com.palone.paloneapp.ui.screens

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.rounded.SwapHorizontalCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.color.KalendarThemeColor
import com.himanshoe.kalendar.model.KalendarType
import com.palone.paloneapp.data.models.SubstitutionData
import com.palone.paloneapp.data.models.SubstitutionDataEntry
import com.palone.paloneapp.ui.MainViewModel
import com.palone.paloneapp.ui.components.TopBar
import com.palone.paloneapp.ui.components.home_screen.FilterDialog
import com.palone.paloneapp.ui.components.home_screen.SubstitutionElement


@Composable
fun HomeScreen(viewModel: MainViewModel) {
    Scaffold(
        scaffoldState = viewModel.uiState.collectAsState().value.scaffoldState,
        modifier = Modifier.fillMaxSize(),
        drawerContent = {
            Text(
                text = "Hello, I'm the content of Drawer :)"
            )
        },
        drawerBackgroundColor = MaterialTheme.colors.background,
        drawerContentColor = MaterialTheme.colors.secondary,
        topBar = {
            TopBar(viewModel = viewModel, additionalButton = {
                IconButton(onClick = { viewModel.showTextFilterDialog() }) {
                    Icon(Icons.Default.FilterAlt, "Filter")
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO("switch tabs here")*/ },
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
            Kalendar(modifier = Modifier,
                kalendarType = KalendarType.Oceanic,
                kalendarThemeColor = KalendarThemeColor(
                    MaterialTheme.colors.primaryVariant,
                    MaterialTheme.colors.secondary,
                    MaterialTheme.colors.secondary
                ),
                onCurrentDayClick = { kalendarDay, _ ->
                    viewModel.updateSelectedLocalDate(kalendarDay.localDate)
                    viewModel.refreshSubstitutionsDataWithLocalDate(kalendarDay.localDate) { viewModel.refreshFilteredSubstitutionsWithQuery() }

                }
            )
            val willRefresh = remember { mutableStateOf(false) }
            val reloadBackgroundColor = animateColorAsState(
                targetValue = if (willRefresh.value) Color(0x77850000) else Color(0x2D000000),
                tween(durationMillis = 500, delayMillis = 10)
            )
            SwipeRefresh(
                modifier = Modifier
                    .padding(padding),
                swipeEnabled = true,
                state = rememberSwipeRefreshState(isRefreshing = viewModel.uiState.collectAsState().value.isLoading),
                onRefresh = {
                    viewModel.refreshSubstitutionsDataWithLocalDate(viewModel.uiState.value.selectedLocalDate)
                }, indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        modifier = Modifier.drawBehind {
                            willRefresh.value = state.indicatorOffset > 250f
                            Log.i("", "${state.indicatorOffset}")
                            drawCircle(
                                reloadBackgroundColor.value,
                                radius = if (state.indicatorOffset * 3.0f < 1000.0f) state.indicatorOffset * 3.0f else 1000.0f,
                                Offset(this.center.x, (state.indicatorOffset * 0.4f) - 200.0f)
                            )
                        },
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        contentColor = MaterialTheme.colors.onBackground,
                        fade = false,
                    )

                }) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    if (viewModel.uiState.collectAsState().value.filteredSubstitutionsList.isNullOrEmpty())
                        SubstitutionElement(
                            substitutionData = SubstitutionData(
                                entries = listOf(
                                    SubstitutionDataEntry(
                                        teacherReplacement = "Brak informacji. Jeśli masz pewność, że nowe dostępstwa już są dostępne - sprawdź ustawienia filtra"
                                    )
                                )
                            )
                        )
                    viewModel.uiState.collectAsState().value.filteredSubstitutionsList?.forEach {
                        SubstitutionElement(substitutionData = it)
                    }
                }
            }
        }
    }
    if (viewModel.uiState.collectAsState().value.shouldShowFilterDialog)
        FilterDialog(viewModel = viewModel)

}