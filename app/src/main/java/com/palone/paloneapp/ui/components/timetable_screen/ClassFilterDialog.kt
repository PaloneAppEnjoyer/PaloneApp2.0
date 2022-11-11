package com.palone.paloneapp.ui.components.timetable_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.palone.paloneapp.ui.TimetableViewModel

@Composable
fun ClassFilterDialog(viewModel: TimetableViewModel, onDismissRequest: () -> Unit = {}) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            viewModel.getAllSchoolClassesNames().sortedBy { it }.forEach { schoolClassName ->
                Row {
                    Text(text = schoolClassName)
                    Checkbox(
                        checked = viewModel.uiState.collectAsState().value.selectedSchoolClass == schoolClassName,
                        onCheckedChange = { viewModel.setSchoolClassQuery(schoolClassName) })
                }
            }
        }

    }
}