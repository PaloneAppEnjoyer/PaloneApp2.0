package com.palone.paloneapp.ui.components.timetable_screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.palone.paloneapp.ui.TimetableViewModel

@Composable
fun DaySelector(viewModel: TimetableViewModel, modifier: Modifier = Modifier) {
    val days = listOf("Pn", "Wt", "Śr", "Czw", "Pi")
    Column(
        modifier = modifier
            .width(50.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        days.forEach {
            val cornerShape =
                when (it) {
                    days.first() -> RoundedCornerShape(topStart = 20.dp)
                    days.last() -> RoundedCornerShape(bottomStart = 20.dp)
                    else -> RoundedCornerShape(0.dp)
                }
            val borderColor = BorderStroke(
                0.5.dp,
                animateColorAsState(
                    targetValue = if (it == viewModel.uiState.collectAsState().value.selectedDay) MaterialTheme.colors.secondary else MaterialTheme.colors.primaryVariant,
                    animationSpec = tween(200)
                ).value
            )
            val backgroundColor =
                if (it == viewModel.uiState.collectAsState().value.selectedDay) MaterialTheme.colors.background else MaterialTheme.colors.primaryVariant

            Card(
                shape = cornerShape,
                modifier = Modifier
                    .clickable { viewModel.selectDay(day = it) }
                    .height(70.dp)
                    .width(50.dp),
                backgroundColor = backgroundColor,
                contentColor = MaterialTheme.colors.secondary,
                elevation = 10.dp,
                border = borderColor
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = it)
                }
            }
        }
    }
}