package com.palone.paloneapp.utils.timeManager

import com.google.common.truth.Truth
import com.palone.paloneapp.data.models.DateSystem
import org.junit.Test
import java.util.*

/*
* 1.given current calendar, then getCurrentDate should return today's DateSystem
* 2.given current calendar, then getTomorrowDate should return tomorrow's DateSystem
* */

internal class TimeManagerImplTest {
    private val subject = TimeManagerImpl(Calendar.getInstance())

    @Test
    fun `given current calendar, then getCurrentDate should return today's DateSystem`() {
        //given
        //when
        val actual = subject.getCurrentDate()
        //then
        val expected = DateSystem(2022, 12, 8, "Czw")
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given current calendar, then getTomorrowDate should return tomorrow's DateSystem`() {
        //given
        //when
        val actual = subject.getTomorrowDate()
        //then
        val expected = DateSystem(2022, 12, 9, "Pi")
        Truth.assertThat(actual).isEqualTo(expected)
    }
}