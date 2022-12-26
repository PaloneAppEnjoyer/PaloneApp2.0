package com.palone.paloneapp.substitutions.ui.substitutions_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.palone.paloneapp.ui.SubstitutionsViewModel

@Composable
fun QueryFilterDialog(viewModel: SubstitutionsViewModel) {
    Dialog(onDismissRequest = { viewModel.hideTextFilterDialog() }) {

        Card(
            backgroundColor = MaterialTheme.colors.background,
            shape = RoundedCornerShape(10.dp),
            contentColor = MaterialTheme.colors.onPrimary
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "Filtruj po klasie:", modifier = Modifier.padding(bottom = 5.dp))
                TextField(
                    value = viewModel.uiState.collectAsState().value.classFilter,
                    onValueChange = { viewModel.updateClassFilter(it);viewModel.refreshFilteredSubstitutionsWithQuery() },

                    )
                Button(
                    onClick = { viewModel.hideTextFilterDialog() },
                    modifier = Modifier.padding(bottom = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary,
                        contentColor = MaterialTheme.colors.background
                    )
                ) {
                    Text(text = "Zamknij")
                }
            }
        }
    }

}