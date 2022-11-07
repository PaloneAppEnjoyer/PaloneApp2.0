package com.palone.paloneapp.data

import com.palone.paloneapp.data.models.SubstitutionData
import com.palone.paloneapp.data.models.SubstitutionDataEntry
import com.palone.paloneapp.data.network.RetrofitInstance
import com.palone.paloneapp.domain.Parsers
import kotlinx.datetime.LocalDate
import okhttp3.RequestBody.Companion.toRequestBody

class SubstitutionsDataProvider {
    suspend fun getRemoteData(localDate: LocalDate): List<SubstitutionData> {
        return try {
            val rawData = RetrofitInstance.api.getSubstitutions(
                """{"__args":[null,{"date":"${localDate.year}-${if ((localDate.monthNumber).toString().length == 1) "0" + localDate.monthNumber else localDate.monthNumber}-${if (localDate.dayOfMonth.toString().length == 1) "0" + localDate.dayOfMonth else localDate.dayOfMonth}","mode":"classes"}],"__gsh":"00000000"}""".toRequestBody()
            ).response

            Parsers().getSubstitutionsFromHtml(rawData = rawData)
        } catch (e: Exception) {
            listOf(SubstitutionData(entries = listOf(SubstitutionDataEntry(teacherReplacement = "Sprawdź połączenie z internetem"))))
        }
    }
}