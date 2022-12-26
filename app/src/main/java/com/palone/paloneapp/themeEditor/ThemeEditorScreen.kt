package com.palone.paloneapp.themeEditor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ThemeEditorScreen(navHostController: NavHostController) {
    val colorNames = listOf(
        listOf(MaterialTheme.colors.primary, "primary"),
        listOf(MaterialTheme.colors.primaryVariant, "primaryVariant"),
        listOf(MaterialTheme.colors.secondary, "secondary"),
        listOf(MaterialTheme.colors.background, "background"),
        listOf(MaterialTheme.colors.surface, "surface"),
        listOf(MaterialTheme.colors.onBackground, "onBackground")
    )
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        modifier = Modifier.fillMaxSize(),
        drawerBackgroundColor = MaterialTheme.colors.background,
        drawerContentColor = MaterialTheme.colors.secondary, topBar = {
            Card(
                backgroundColor = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                contentColor = MaterialTheme.colors.onBackground,
                shape = RoundedCornerShape(0.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(Icons.Default.KeyboardArrowLeft, "Cofnij")
                    }
                }
            }
        }) {
        Column(modifier = Modifier.padding(it)) {
            colorNames.forEach {
                Row {
                    Card(backgroundColor = it[0] as Color, modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clickable { }) {
                    }
                }
            }
        }
    }
}