package com.palone.paloneapp.ui.components.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
fun FilterDialog(viewModel: SubstitutionsViewModel) {
    Dialog(onDismissRequest = { viewModel.hideTextFilterDialog() }) {

        Box(
            Modifier.background(
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(10.dp)
            )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField(
                    value = viewModel.uiState.collectAsState().value.classFilter,
                    onValueChange = { viewModel.updateTextFilter(it);viewModel.refreshFilteredSubstitutionsWithQuery() },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.secondary),
                    modifier = Modifier.padding(10.dp)
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