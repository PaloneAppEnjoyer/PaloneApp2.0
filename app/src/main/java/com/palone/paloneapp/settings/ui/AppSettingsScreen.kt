package com.palone.paloneapp.ui.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import com.palone.paloneapp.data.SettingsRepository
import com.palone.paloneapp.settings.data.ThemeSettingsRepository
import com.palone.paloneapp.settings.ui.components.ThemeEditorDialog
import com.palone.paloneapp.ui.components.DrawerItem
import kotlinx.coroutines.launch

@Composable
fun AppSettingsScreen(navHostController: NavHostController) {

    val context = LocalContext.current
    val colors = MaterialTheme.colors

    SideEffect {
        val window = (context as Activity).window

        window.navigationBarColor = colors.background.toArgb()

        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightNavigationBars = colors.isLight
        }
    }
    val repo = remember { SettingsRepository(context) }
    val scope = rememberCoroutineScope()

    val notifZastepstwa by repo.notifZastepstwa.collectAsState(initial = true)
    val notifPlan by repo.notifPlan.collectAsState(initial = false)
    val notifLekcja by repo.notifLekcja.collectAsState(initial = true)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding(),
        topBar = {
            Card(
                backgroundColor = MaterialTheme.colors.surface,
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                contentColor = MaterialTheme.colors.onBackground,
                shape = RoundedCornerShape(0.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(Icons.Default.KeyboardArrowLeft, "Cofnij")
                    }
                }
            }
        }
    ) { padding ->
        val shouldShowTodoListDialog = remember { mutableStateOf(false) }
        val shouldShowThemeEditorDialog = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier

        ) {

            // -------------------------
            // KATEGORIA: POWIADOMIENIA
            // -------------------------
            Text(
                text = "POWIADOMIENIA",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.secondary, // czerwony
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

            SettingsSwitchItem(
                title = "Nowe zastępstwa",
                checked = notifZastepstwa,
                onCheckedChange = { scope.launch { repo.setNotifZastepstwa(it) } }
            )

            SettingsSwitchItem(
                title = "Zmiany planu lekcji",
                checked = notifPlan,
                onCheckedChange = { scope.launch { repo.setNotifPlan(it) } }
            )

            SettingsSwitchItem(
                title = "Aktualna lekcja",
                checked = notifLekcja,
                onCheckedChange = { scope.launch { repo.setNotifLekcja(it) } }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // -------------------------
            // KATEGORIA: INNE
            // -------------------------
            Text(
                text = "INNE",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

            DrawerItem(title = "Donate", onClick = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.buymeacoffee.com/paloneapp")
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                ContextCompat.startActivity(context, intent, null)
            })

            DrawerItem(title = "Kontakt", onClick = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("mailto:info@palone.app")
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                ContextCompat.startActivity(context, intent, null)
            })

            DrawerItem(
                title = "Edytuj kolory",
                onClick = { shouldShowThemeEditorDialog.value = true }
            )

            DrawerItem(
                title = "Więcej opcji wkrótce...",
                onClick = { shouldShowTodoListDialog.value = true }
            )
        }

        if (shouldShowTodoListDialog.value)
            Dialog(onDismissRequest = { shouldShowTodoListDialog.value = false }) {
                Column {
                    Text(text = "Wyświetlanie obecnej lekcji jako powiadomienie")
                }
            }

        if (shouldShowThemeEditorDialog.value)
            ThemeEditorDialog(
                onDismissRequest = { shouldShowThemeEditorDialog.value = false },
                onColorChanged = { newColor ->
                    scope.launch {
                        ThemeSettingsRepository(context).setSurfaceColor(newColor)
                    }
                },
                onReset = {
                    scope.launch {
                        ThemeSettingsRepository(context).resetSurfaceColor()
                    }
                    shouldShowThemeEditorDialog.value = false
                }
            )
    }
}

@Composable
fun SettingsSwitchItem(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.secondary
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.surface, // kolor kropka
                checkedTrackColor = MaterialTheme.colors.surface.copy(alpha = 0.4f), // kolor tło
                uncheckedThumbColor = MaterialTheme.colors.onSurface, // szara kropka
                uncheckedTrackColor = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)  // szare tło
            )
        )

    }
}



