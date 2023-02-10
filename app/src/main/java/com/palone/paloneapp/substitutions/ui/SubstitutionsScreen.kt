package com.palone.paloneapp.substitutions.ui

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.color.KalendarThemeColor
import com.himanshoe.kalendar.model.KalendarType
import com.palone.paloneapp.data.ScreensProperties
import com.palone.paloneapp.substitutions.data.models.SubstitutionData
import com.palone.paloneapp.substitutions.data.models.SubstitutionDataEntry
import com.palone.paloneapp.substitutions.ui.substitutions_screen.QueryFilterDialog
import com.palone.paloneapp.substitutions.ui.substitutions_screen.SubstitutionElement
import com.palone.paloneapp.ui.SubstitutionsViewModel
import com.palone.paloneapp.ui.components.DrawerItem
import com.palone.paloneapp.ui.components.MainFloatingActionButton
import com.palone.paloneapp.ui.components.TopBar
import com.palone.paloneapp.utils.composableToBitmap
import com.palone.paloneapp.utils.shareAImage


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SubstitutionsScreen(viewModel: SubstitutionsViewModel, navHostController: NavHostController) {
    val localContext = LocalContext.current
    val substitutionDataFilament = remember {
        mutableStateOf(SubstitutionData("", emptyList()))
    }
    val bitmapFromComposable =
        composableToBitmap {
            SubstitutionElement(
                substitutionData = substitutionDataFilament.value,
                currentDay = viewModel.uiState.collectAsState().value.selectedLocalDate,
                shouldShowPaloneWatermark = true
            )
        }

    Scaffold(
        scaffoldState = viewModel.uiState.collectAsState().value.scaffoldState,
        modifier = Modifier.fillMaxSize(),
        drawerContent = {
            DrawerItem(
                title = "Opcje",
                onClick = { navHostController.navigate(ScreensProperties.SettingsScreen.route) })
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
            MainFloatingActionButton(viewModel = viewModel, navHostController = navHostController)
        }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                backgroundColor = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(0.dp)
            ) {
                Kalendar(
                    modifier = Modifier
                        .requiredHeight(130.dp)
                        .requiredWidth(410.dp)
                        .scale(
                            scale = if (LocalConfiguration.current.screenWidthDp < 410) {
                                (LocalConfiguration.current.screenWidthDp.toFloat()) / 410f
                            } else 1f
                        )
                        .aspectRatio(3.01f),
                    kalendarType = KalendarType.Oceanic,
                    kalendarThemeColor = KalendarThemeColor(
                        MaterialTheme.colors.primaryVariant,
                        MaterialTheme.colors.surface,
                        MaterialTheme.colors.secondary
                    ),
                    onCurrentDayClick = { kalendarDay, _ ->
                        viewModel.updateSelectedLocalDate(kalendarDay.localDate)
                        viewModel.refreshSubstitutionsDataWithLocalDate(kalendarDay.localDate) { viewModel.refreshFilteredSubstitutionsWithQuery() }
                    },
                    takeMeToDate = viewModel.uiState.collectAsState().value.selectedLocalDate,
                )
            }

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
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (viewModel.uiState.collectAsState().value.filteredSubstitutionsList.isNullOrEmpty())
                        SubstitutionElement(
                            substitutionData = SubstitutionData(
                                entries = listOf(
                                    SubstitutionDataEntry(
                                        teacherReplacement = "Brak informacji. Jeśli masz pewność, że nowe dostępstwa już są dostępne - sprawdź ustawienia filtra"
                                    )
                                )
                            ),
                            currentDay = viewModel.uiState.collectAsState().value.selectedLocalDate,
                            shouldShowPaloneWatermark = false
                        )
                    viewModel.uiState.collectAsState().value.filteredSubstitutionsList?.forEach {
                        AnimatedContent(
                            targetState = it,
                            transitionSpec = { scaleIn() with fadeOut() }) { scope ->
                            SubstitutionElement(
                                substitutionData = scope,
                                currentDay = viewModel.uiState.collectAsState().value.selectedLocalDate,
                                shouldShowPaloneWatermark = false
                            ) {
                                substitutionDataFilament.value = scope
                                viewModel.onLongPressShare(
                                    bitmapFromComposable,
                                    localContext,
                                    shareImage = { shareAImage(localContext) })
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(0.dp))
                }
            }
        }
    }
    if (viewModel.uiState.collectAsState().value.shouldShowFilterDialog)
        QueryFilterDialog(viewModel = viewModel)

}


