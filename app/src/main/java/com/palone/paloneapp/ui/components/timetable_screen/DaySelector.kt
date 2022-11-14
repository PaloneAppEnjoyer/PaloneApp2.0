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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DaySelector(
    modifier: Modifier = Modifier,
    selectedDay: String,
    onDaySelected: (String) -> Unit = {},
    getCoordinates: (Dp) -> Unit = {},
    isHorizontal: Boolean = false
) {
    if (isHorizontal)
        Row(
            modifier = modifier
                .height(50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DaySelectorDisplayData(
                isHorizontal = isHorizontal,
                selectedDay = selectedDay,
                onDaySelected = onDaySelected, getCoordinates = getCoordinates
            )
        }
    else
        Column(
            modifier = modifier
                .width(50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DaySelectorDisplayData(
                isHorizontal = isHorizontal,
                selectedDay = selectedDay,
                onDaySelected = onDaySelected, getCoordinates = getCoordinates
            )
        }
}

@Composable
fun DaySelectorDisplayData(
    isHorizontal: Boolean,
    days: List<String> = listOf("Pn", "Wt", "Śr", "Czw", "Pi"),
    selectedDay: String, getCoordinates: (Dp) -> Unit = {},
    onDaySelected: (String) -> Unit = {}
) {
    days.forEach {
        val cornerShape = if (isHorizontal) when (it) {
            days.first() -> RoundedCornerShape(bottomStart = 20.dp, topStart = 20.dp)
            days.last() -> RoundedCornerShape(bottomEnd = 20.dp, topEnd = 20.dp)
            else -> RoundedCornerShape(0.dp)
        }
        else
            when (it) {
                days.first() -> RoundedCornerShape(topStart = 20.dp)
                days.last() -> RoundedCornerShape(bottomStart = 20.dp)
                else -> RoundedCornerShape(0.dp)
            }


        val borderColor = BorderStroke(
            0.5.dp,
            animateColorAsState(
                targetValue = if (it == selectedDay) MaterialTheme.colors.secondary else MaterialTheme.colors.primaryVariant,
                animationSpec = tween(200)
            ).value
        )
        val backgroundColor =
            if (it == selectedDay) MaterialTheme.colors.background else MaterialTheme.colors.primaryVariant

        Card(
            shape = cornerShape,
            modifier = Modifier
                .clickable { onDaySelected(it) }
                .height(70.dp)
                .width(50.dp)
                .onGloballyPositioned { coords -> if (it == days.first()) getCoordinates(coords.positionInWindow().y.dp) },
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