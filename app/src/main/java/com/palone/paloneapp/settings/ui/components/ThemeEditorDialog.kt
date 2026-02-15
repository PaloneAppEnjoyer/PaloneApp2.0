package com.palone.paloneapp.settings.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun ThemeEditorDialog(
    onDismissRequest: () -> Unit,
    onColorChanged: (Int) -> Unit,
    onReset: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {

        val controller = rememberColorPickerController()

        LaunchedEffect(controller.selectedColor.value) {
            onColorChanged(controller.selectedColor.value.toArgb())
        }

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Wybierz kolor akcentu",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            HsvColorPicker(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                controller = controller
            )

            Spacer(modifier = Modifier.height(16.dp))

            AlphaSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                controller = controller
            )

            Spacer(modifier = Modifier.height(16.dp))

            BrightnessSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                controller = controller
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onReset,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.onBackground
                )
            ) {
                Text(
                    text = "Domyślny kolor",
                    color = MaterialTheme.colors.onSecondary
                )
            }
        }
    }
}
