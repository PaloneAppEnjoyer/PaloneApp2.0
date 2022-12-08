package com.palone.paloneapp.timetable.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.palone.paloneapp.ui.TimetableViewModel

@Composable
fun ClassFilterDialog(viewModel: TimetableViewModel, onDismissRequest: () -> Unit = {}) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                viewModel.getAllSchoolClassesNames().sortedBy { it }.forEach { schoolClassName ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start, modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clickable { viewModel.setSchoolClassQuery(schoolClassName) }
                    ) {
                        RadioButton(
                            selected = viewModel.uiState.collectAsState().value.selectedSchoolClass == schoolClassName,
                            onClick = { viewModel.setSchoolClassQuery(schoolClassName) })
                        Text(text = schoolClassName)
                    }


                }
            }

        }
    }
}
