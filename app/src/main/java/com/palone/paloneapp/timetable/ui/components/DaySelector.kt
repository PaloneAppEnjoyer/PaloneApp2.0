package com.palone.paloneapp.timetable.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
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
    val shouldChangeAlpha = remember { mutableStateOf(false) }

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
                onDaySelected = onDaySelected, getCoordinates = getCoordinates, onLongPress = {}
            )
        }
    else
        Column(
            modifier = modifier
                .width(50.dp)
                .alpha(if (shouldChangeAlpha.value) 0.5f else 1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DaySelectorDisplayData(
                isHorizontal = isHorizontal,
                selectedDay = selectedDay,
                onDaySelected = onDaySelected, getCoordinates = getCoordinates, onLongPress = {
                    shouldChangeAlpha.value = !shouldChangeAlpha.value
                }, elevation = if (shouldChangeAlpha.value) 0.dp else 10.dp
            )
        }
}

@Composable
fun DaySelectorDisplayData(
    isHorizontal: Boolean,
    days: List<String> = listOf("Pn", "Wt", "Śr", "Czw", "Pi"),
    selectedDay: String, getCoordinates: (Dp) -> Unit = {},
    onDaySelected: (String) -> Unit = {}, onLongPress: () -> Unit, elevation: Dp = 10.dp
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
                targetValue = if (it == selectedDay) MaterialTheme.colors.surface else MaterialTheme.colors.primaryVariant,
                animationSpec = tween(200)
            ).value
        )
        val backgroundColor =
            if (it == selectedDay) MaterialTheme.colors.background else MaterialTheme.colors.primaryVariant

        Card(
            shape = cornerShape,
            modifier = Modifier
                .height(70.dp)
                .width(50.dp)
                .onGloballyPositioned { coords -> if (it == days.first()) getCoordinates(coords.positionInWindow().y.dp) },
            backgroundColor = backgroundColor,
            contentColor = MaterialTheme.colors.secondary,
            elevation = elevation,
            border = borderColor
        ) {
            Column(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(onLongPress = {
                            onLongPress()
                        }, onTap = { _ -> onDaySelected(it) })
                    }

                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = it)
            }
        }
    }

}