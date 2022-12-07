package com.palone.paloneapp.domain.timeManager

import com.palone.paloneapp.data.models.DateSystem
import java.util.*

class TimeManagerImpl : TimeManager {
    override fun getCurrentDate(): DateSystem {
        val calendar = Calendar.getInstance()
        return DateSystem(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH),
            when (calendar.get(Calendar.DAY_OF_WEEK) - 1) {
                1 -> "Pn"
                2 -> "Wt"
                3 -> "Śr"
                4 -> "Czw"
                5 -> "Pi"
                else -> "weekend"
            }
        )
    }

    override fun getTomorrowDate(): DateSystem {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1)
        return DateSystem(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH),
            when (calendar.get(Calendar.DAY_OF_WEEK) - 1) {
                1 -> "Pn"
                2 -> "Wt"
                3 -> "Śr"
                4 -> "Czw"
                5 -> "Pi"
                else -> "weekend"
            }
        )
    }
}