package com.palone.paloneapp.ui.components.timetable_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.palone.paloneapp.ui.TimetableViewModel

@Composable
fun HideGroupDialog(viewModel: TimetableViewModel, onDismissRequest: () -> Unit = {}) {
    val groups: MutableList<String> = mutableListOf()
    Dialog(onDismissRequest = onDismissRequest) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            viewModel.uiState.collectAsState().value.lessonsList.forEach {
                it.entries.forEach { it2 ->
                    if (!viewModel.uiState.value.hiddenGroups.contains(it2.groupName) && !groups.contains(
                            it2.groupName
                        )
                    ) {
                        groups.add(it2.groupName)
                    }
                }
            }
            groups.forEach { groupName ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = groupName)
                    Checkbox(
                        checked = viewModel.uiState.collectAsState().value.hiddenGroups.contains(
                            groupName
                        ),
                        onCheckedChange = { viewModel.setThisGroupHidden(groupName) })
                }
            }
            viewModel.uiState.collectAsState().value.hiddenGroups.distinct()
                .forEach { hiddenGroupName ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Text(text = hiddenGroupName)
                        Checkbox(
                            checked = viewModel.uiState.collectAsState().value.hiddenGroups.contains(
                                hiddenGroupName
                            ),
                            onCheckedChange = {
                                viewModel.unsetThisGroupHidden(hiddenGroupName);groups.add(
                                hiddenGroupName
                            )
                            }
                        )
                    }
                }
        }

    }
}