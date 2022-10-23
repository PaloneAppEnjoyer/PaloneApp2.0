package com.palone.paloneapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.glance.text.Text
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen() {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = false),
        onRefresh = { /*TODO*/ }) {
        Text(text = "Welcome to Home Screen") // TODO
    }
}