package com.palone.paloneapp.domain.timeManager

import com.palone.paloneapp.data.models.DateSystem

interface TimeManager {
    fun getCurrentDate(): DateSystem
    fun getTomorrowDate(): DateSystem
}