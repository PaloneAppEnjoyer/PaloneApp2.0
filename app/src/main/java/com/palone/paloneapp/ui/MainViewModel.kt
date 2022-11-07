package com.palone.paloneapp.ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController


sealed class MainViewModel : ViewModel() {
    abstract suspend fun openDrawer()
    abstract suspend fun closeDrawer()
    abstract fun onFabClick(navHostController: NavHostController)
    val directory = "/data/user/0/com.palone.paloneapp/files" //TODO("can't do this like that")
}