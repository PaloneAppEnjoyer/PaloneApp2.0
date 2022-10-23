package com.palone.paloneapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.palone.paloneapp.domain.UseCases
import com.palone.paloneapp.ui.MainViewModel
import com.palone.paloneapp.ui.PaloneApp
import com.palone.paloneapp.ui.theme.PaloneAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<MainViewModel>()

        lifecycleScope.launchWhenCreated {
            Log.i(
                "TEEEESSSTTTT", UseCases().getSubstitutionsDataWithDayOffset(-5).toString()
            )
        } // TODO move it to domain layer (prepare function to change selected day), make a class out of it, send it to viewmodel, make a class HomeScreenUiState, pass there any important info, send it to Ui
        setContent {
            PaloneAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PaloneApp(viewModel = viewModel)
                }
            }
        }
    }
}

