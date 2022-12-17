package com.palone.paloneapp.utils.timeManager

import com.palone.paloneapp.data.models.DateSystem
import java.util.*

class TimeManagerImpl(private val calendar: Calendar) : TimeManager {
    override fun getCurrentDate(): DateSystem {
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