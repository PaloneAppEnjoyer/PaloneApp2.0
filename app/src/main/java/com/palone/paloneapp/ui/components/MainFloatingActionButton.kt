package com.palone.paloneapp.ui.components

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SwapHorizontalCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.palone.paloneapp.ui.MainViewModel

@Composable
fun MainFloatingActionButton(
    viewModel: MainViewModel,
    navHostController: NavHostController,
    onLongPress: () -> Unit = { Log.i("long press", "looooooong") }
) {
    val shouldChangeAlpha = remember { mutableStateOf(false) }
    Card(
        contentColor = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .size(70.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        shouldChangeAlpha.value = !shouldChangeAlpha.value
                    },
                    onTap = {
                        viewModel.onFabClick(navHostController = navHostController)
                    })
            }
            .alpha(if (shouldChangeAlpha.value) 0.5f else 1f),
        shape = RoundedCornerShape(100.dp),
        backgroundColor = MaterialTheme.colors.surface,
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
}