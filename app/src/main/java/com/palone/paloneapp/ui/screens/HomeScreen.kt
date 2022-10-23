package com.palone.paloneapp.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.palone.paloneapp.ui.MainViewModel

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = false),
        onRefresh = { /*TODO*/ }) {
        Text(text = "Welcome to Home Screen") // TODO
    }
}