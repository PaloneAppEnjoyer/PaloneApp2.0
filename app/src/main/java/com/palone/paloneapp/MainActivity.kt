package com.palone.paloneapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.palone.paloneapp.ui.PaloneApp
import com.palone.paloneapp.ui.SubstitutionsViewModel
import com.palone.paloneapp.ui.TimetableViewModel
import com.palone.paloneapp.ui.theme.PaloneAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val substitutionsViewModel by viewModels<SubstitutionsViewModel>()
        val timetableViewModel by viewModels<TimetableViewModel>()

        setContent {
            PaloneAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PaloneApp(
                        substitutionsViewModel = substitutionsViewModel,
                        timetableViewModel = timetableViewModel
                    )
                }
            }
        }
    }
}

