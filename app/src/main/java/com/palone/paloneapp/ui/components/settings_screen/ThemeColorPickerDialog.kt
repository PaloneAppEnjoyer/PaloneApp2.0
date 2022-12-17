package com.palone.paloneapp.ui.components.settings_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.*

@Composable
fun ThemeColoPickerDialog(onDismissRequest: () -> Unit = {}) {
    val colorController = rememberColorPickerController()
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            HsvColorPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp),
                controller = colorController,
                onColorChanged = { Log.i("Color", it.toString()) })
            AlphaSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                controller = colorController,
                tileEvenColor = Color.White,
                tileOddColor = Color.Black
            )
            BrightnessSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp), controller = colorController
            )
            AlphaTile(modifier = Modifier, controller = colorController)


        }

    }


    /*primary = Color(pref.getLong("primary",0xFF6F6F6F)),
    primaryVariant = Color(pref.getLong("primaryVariant",0xFFFCFCFC)),//subs elements
    secondary = Color(pref.getLong("secondary",0xFF9B2720)),
    background = Color(pref.getLong("background",0xFFF9F1EF)),//Light Pink
    surface = Color(pref.getLong("surface",0xFF9B2720)), //TEXT COLOR
    onBackground = Color(pref.getLong("onBackground",0xFFF9F1EF))*/ //TODO
}