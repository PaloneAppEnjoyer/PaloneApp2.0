package com.palone.paloneapp.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.palone.paloneapp.settings.ui.components.ThemeEditorDialog
import com.palone.paloneapp.ui.components.DrawerItem

@Composable
fun AppSettingsScreen(navHostController: NavHostController) {
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
        val context = LocalContext.current
        val shouldShowTodoListDialog = remember {
            mutableStateOf(false)
        }
        val shouldShowThemeEditorDialog = remember {
            mutableStateOf(false)
        }
        Column(modifier = Modifier.padding(paddingValues = it)) {
            DrawerItem(title = "Donate", onClick = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.buymeacoffee.com/paloneapp")
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                ContextCompat.startActivity(
                    context,
                    intent,
                    null
                )
            })
            DrawerItem(title = "Kontakt", onClick = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("mailto:info@palone.app")
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                ContextCompat.startActivity(
                    context,
                    intent,
                    null
                )
            })
            DrawerItem(
                title = "Edytuj kolory",
                onClick = { shouldShowThemeEditorDialog.value = true })
            DrawerItem(
                title = "Więcej opcji wkrótce...",
                onClick = { shouldShowTodoListDialog.value = true })
        }
        if (shouldShowTodoListDialog.value)
            Dialog(onDismissRequest = { shouldShowTodoListDialog.value = false }) {
                Column {
                    Text(text = "Color picker do zmieniania motywu aplikacji")
                    Text(text = "Wyświetlanie obecnej lekcji jako powiadomienie")
                }

            }
        if (shouldShowThemeEditorDialog.value)
            ThemeEditorDialog { shouldShowThemeEditorDialog.value = false }
    }
}


