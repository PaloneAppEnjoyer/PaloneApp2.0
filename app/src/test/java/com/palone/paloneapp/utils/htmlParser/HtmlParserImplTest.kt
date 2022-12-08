package com.palone.paloneapp.utils.htmlParser

import com.google.common.truth.Truth
import com.palone.paloneapp.substitutions.data.models.SubstitutionData
import com.palone.paloneapp.substitutions.data.models.SubstitutionDataEntry
import org.junit.Test

/*
* 1.given empty string, then it should return a empty list
* 2.given null, then it should return a empty list
* 3.given a valid string of one substitution, then it should return a list with one substitution
* 4.given a valid string of multiple substitutions, then it should return a list with multiple substitutions
* 5.given an invalid string. then it should crash and break apart
* */
internal class HtmlParserImplTest {
    private val subject: HtmlParserInterface = HtmlParserImpl()

    @Test
    fun `given empty string, then it should return a list with empty substitution`() {
        //given
        val inputString = ""
        //when
        val actual = subject.getSubstitutionsFromHtml(inputString)
        //then
        val expected = listOf(SubstitutionData(entries = listOf(SubstitutionDataEntry())))

        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given null, then it should return a list with empty substitution`() {
        //given
        val inputString = null
        //when
        val actual = subject.getSubstitutionsFromHtml(inputString)
        //then
        val expected = listOf(SubstitutionData(entries = listOf(SubstitutionDataEntry())))

        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given a valid string of one substitution, then it should return a list with one substitution`() {
        //given
        val inputString = getString(1)
        //when
        val actual = subject.getSubstitutionsFromHtml(inputString)
        //then
        val expected = listOf(
            SubstitutionData(
                className = "1bl",
                entries = listOf(
                    SubstitutionDataEntry(
                        lessons = "5",
                        subject = "geogr ➔ matem",
                        teacherReplacement = "Artur Delimat ➔ Agata Szafran",
                        roomChange = "Zmień salę lekcyjną: A52 ➔ A43"
                    )
                )
            )
        )
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given a valid string of multiple substitutions, then it should return a list with multiple substitutions`() {
        //given
        val inputString = getString(2)
        //when
        val actual = subject.getSubstitutionsFromHtml(inputString)
        //then
        val expected = listOf(
            SubstitutionData(
                className = "1bl",
                entries = listOf(
                    SubstitutionDataEntry(
                        lessons = "5",
                        subject = "geogr ➔ matem",
                        teacherReplacement = "Artur Delimat ➔ Agata Szafran",
                        roomChange = "Zmień salę lekcyjną: A52 ➔ A43"
                    )
                )
            ),
            SubstitutionData(
                className = "2bl",
                entries = listOf(
                    SubstitutionDataEntry(
                        lessons = "5",
                        subject = "geogr ➔ matem",
                        teacherReplacement = "Artur Delimat ➔ Agata Szafran",
                        roomChange = "Zmień salę lekcyjną: A52 ➔ A43"
                    )
                )
            ),
            SubstitutionData(
                className = "3bl",
                entries = listOf(
                    SubstitutionDataEntry(
                        lessons = "5",
                        subject = "geogr ➔ matem",
                        teacherReplacement = "Artur Delimat ➔ Agata Szafran",
                        roomChange = "Zmień salę lekcyjną: A52 ➔ A43"
                    )
                )
            )
        )
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given an invalid string, then it should crash and break apart`() {
        val inputString = getString(3)
        //when
        val actual = subject.getSubstitutionsFromHtml(inputString)
        //then
        val expected = listOf(SubstitutionData(entries = listOf(SubstitutionDataEntry())))

        Truth.assertThat(actual).isEqualTo(expected)
    }

    private fun getString(mode: Int): String {
        return when (mode) {
            1 -> """<div class=\"_RSS7173\"><style type=\"text/css\">._RSS7173 .signature {width:100px;text-Align:center;color:#a0a0a0}\n._RSS7173 .row {display:flex;flex-Direction:row;align-Items:baseline;font-Size:12px;margin-Bottom:1px}\n._RSS7173 .period {font-Size:12px;text-Align:center;white-Space:nowrap}\n._RSS7173 .period>span {}\n._RSS7173 .info {flex:1}\n._RSS7173 table {width:100%;table-Layout:auto}\n._RSS7173 tbody {border:2px solid #808080}\n._RSS7173 td {border:1px solid #808080;padding:2px}\n._RSS7173 .header {font-Size:16px;text-Align:center}\n._RSS7173 .time {font-Size:12px;text-Align:center;white-Space:pre}</style><div data-date=\"2022-11-21\"><div style=\"text-align:center\"><span class=\"print-font-resizable\">Nieobecni nauczyciele: Artur Delimat , Maciej Kania , Anna Wiklowska-Ivashkiv , Anna Grudysz  (5 - 9)</span></div><div style=\"text-align:center\"><span class=\"print-font-resizable\">Klasy nieobecne: 4bl </span></div><div style=\"height:15px;flex:none\"></div><table><tbody class=\"print-nobreak\"><tr><td rowspan=\"1\" class=\"header\">1bl</td><td class=\"period\"><span class=\"print-font-resizable\">5</span></td><td class=\"what\"><span class=\"print-font-resizable\"><s>geogr</s> ➔ matem</span></td><td class=\"info\"><span class=\"print-font-resizable\">Zastępstwa: <s>Artur Delimat</s> ➔ Agata Szafran, Zmień salę lekcyjną: <s>A52</s> ➔ A43</span></td></tr></tbody></table></div></div>"""
            2 -> """<div class=\"_RSS7173\"><style type=\"text/css\">._RSS7173 .signature {width:100px;text-Align:center;color:#a0a0a0}\n._RSS7173 .row {display:flex;flex-Direction:row;align-Items:baseline;font-Size:12px;margin-Bottom:1px}\n._RSS7173 .period {font-Size:12px;text-Align:center;white-Space:nowrap}\n._RSS7173 .period>span {}\n._RSS7173 .info {flex:1}\n._RSS7173 table {width:100%;table-Layout:auto}\n._RSS7173 tbody {border:2px solid #808080}\n._RSS7173 td {border:1px solid #808080;padding:2px}\n._RSS7173 .header {font-Size:16px;text-Align:center}\n._RSS7173 .time {font-Size:12px;text-Align:center;white-Space:pre}</style><div data-date=\"2022-11-21\"><div style=\"text-align:center\"><span class=\"print-font-resizable\">Nieobecni nauczyciele: Artur Delimat , Maciej Kania , Anna Wiklowska-Ivashkiv , Anna Grudysz  (5 - 9)</span></div><div style=\"text-align:center\"><span class=\"print-font-resizable\">Klasy nieobecne: 4bl </span></div><div style=\"height:15px;flex:none\"></div><table><tbody class=\"print-nobreak\"><tr><td rowspan=\"1\" class=\"header\">1bl</td><td class=\"period\"><span class=\"print-font-resizable\">5</span></td><td class=\"what\"><span class=\"print-font-resizable\"><s>geogr</s> ➔ matem</span></td><td class=\"info\"><span class=\"print-font-resizable\">Zastępstwa: <s>Artur Delimat</s> ➔ Agata Szafran, Zmień salę lekcyjną: <s>A52</s> ➔ A43</span></td></tr></tbody><tbody class=\"print-nobreak\"><tr><td rowspan=\"1\" class=\"header\">2bl</td><td class=\"period\"><span class=\"print-font-resizable\">5</span></td><td class=\"what\"><span class=\"print-font-resizable\"><s>geogr</s> ➔ matem</span></td><td class=\"info\"><span class=\"print-font-resizable\">Zastępstwa: <s>Artur Delimat</s> ➔ Agata Szafran, Zmień salę lekcyjną: <s>A52</s> ➔ A43</span></td></tr></tbody><tbody class=\"print-nobreak\"><tr><td rowspan=\"1\" class=\"header\">3bl</td><td class=\"period\"><span class=\"print-font-resizable\">5</span></td><td class=\"what\"><span class=\"print-font-resizable\"><s>geogr</s> ➔ matem</span></td><td class=\"info\"><span class=\"print-font-resizable\">Zastępstwa: <s>Artur Delimat</s> ➔ Agata Szafran, Zmień salę lekcyjną: <s>A52</s> ➔ A43</span></td></tr></tbody></table></div></div>"""
            3 -> """<div class=\"_RSS7173\"><style type=\"text/css\">._RSS7173 .signature {width:100px;text-Align:center;color:#a0a0a0}\n._RSS7173 .row {display:flex;flex-Direction:row;align-Items:bant-Size:12px;text-Align:center;white-Space:nowrap}\n._RSS7173 .period>span {}\n._RSS7173 .info {flex:1}\n._RSS7173 table {width:100%;table-Layout:auto}\n._RSS7173 tbody {border:2px solid #808080}\n._RSS7173 td {border:1px solid #808080;padding:2px}\n._RSS7173 .header {font-Size:16px;text-Align:center}\n._RSS7173 .time {font-Size:12px;text-Align:center;white-Space:pre}</style><div data-date=\"2022-11-21\"><div style=\"text-align:center\"><span class=\"print-font-resizable\">Nieobecni nauczyciele: Artur Delimat , Maciej Kania , Anna Wiklowska-Ivashkiv , Anna Grudysz  (5 - 9)</span></div><div style=\"text-align:center\"><span class=\"print-font-resizable\">Klasy nieobecne: 4bl </span></div><d><tbody class=\"print-nobreak\"><tr><td rowspan=\"1\" class=\"header\">1bl</td><td class=\"period\"><span class=\"print-font-resizable\">5</span></td><td class=\"what\"><span class=\"print-font-resizable\"><s>geogr</s> ➔ matem</span></td><td class=\"info\"><span class=\"print-font-resizable\">Zastępstwa: <s>Artur Delimat</s> ➔ Agata Szafran, Zmień salę lekcyjną: <s>A52</s> ➔ A43</span></td></tr></tbody><tbody class=\"print-nobreak\"><tr><td rowspan=\"1\" class=\"header\">2bl</td><pan class=\"print-font-resizable\">5</span></td><td class=\"what\"><span class=\"print-font-resizable\"><s>geogr</s> ➔ matem</span></td><td class=\"info\"><span class=\"print-font-resizable\">Zastępstwa: <s>Artur Delimat</s> ➔ Agata Szafran, Zmień salę lekcyjną: <s>A52</s> ➔ A43</span></td></tr></tbody><tbody class=\"print-nobreak\"><tr><td rowspan=\"1\" class=\"header\">3bl</td><td class=\"period\"><span class=\"print-font-resizable\">5</span></td><td class=\"what\"><span class=\"print-font-resizable\"><s>geogr</s> ➔ matem</span></td><td class=\"info\"><span class=\"print-font-resizable\">Zastępstwa: <s>Artur Delimat</s> ➔ Agata Szafran, Zmień salę lekcyjną: <s>A52</s> ➔ A43</span></td></tr></tbody></table></div></div>"""
            else -> """<div class=\"_RSS7173\"><style type=\"text/css\">._RSS7173 .signature {width:100px;text-Align:center;color:#a0a0a0}\n._RSS7173 .row {display:flex;flex-Direction:row;align-Items:baseline;font-Size:12px;margin-Bottom:1px}\n._RSS7173 .period {font-Size:12px;text-Align:center;white-Space:nowrap}\n._RSS7173 .period>span {}\n._RSS7173 .info {flex:1}\n._RSS7173 table {width:100%;table-Layout:auto}\n._RSS7173 tbody {border:2px solid #808080}\n._RSS7173 td {border:1px solid #808080;padding:2px}\n._RSS7173 .header {font-Size:16px;text-Align:center}\n._RSS7173 .time {font-Size:12px;text-Align:center;white-Space:pre}</style><div data-date=\"2022-11-21\"><div style=\"text-align:center\"><span class=\"print-font-resizable\">Nieobecni nauczyciele: Artur Delimat , Maciej Kania , Anna Wiklowska-Ivashkiv , Anna Grudysz  (5 - 9)</span></div><div style=\"text-align:center\"><span class=\"print-font-resizable\">Klasy nieobecne: 4bl </span></div><div style=\"height:15px;flex:none\"></div><table><tbody class=\"print-nobreak\"><tr><td rowspan=\"1\" class=\"header\">1bl</td><td class=\"period\"><span class=\"print-font-resizable\">5</span></td><td class=\"what\"><span class=\"print-font-resizable\"><s>geogr</s> ➔ matem</span></td><td class=\"info\"><span class=\"print-font-resizable\">Zastępstwa: <s>Artur Delimat</s> ➔ Agata Szafran, Zmień salę lekcyjną: <s>A52</s> ➔ A43</span></td></tr></tbody></table></div></div>"""
        }


    }

}