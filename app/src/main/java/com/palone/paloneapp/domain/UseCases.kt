package com.palone.paloneapp.domain

import com.palone.paloneapp.data.SubstitutionsDataProvider
import com.palone.paloneapp.data.models.SubstitutionData
import java.util.*

class UseCases {

    suspend fun getSubstitutionsDataWithDayOffset(dayNumber: Int): List<SubstitutionData> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_YEAR, Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + dayNumber)
        return SubstitutionsDataProvider().getRemoteData(cal)
    }
}