package com.palone.paloneapp.domain

import com.palone.paloneapp.data.models.SubstitutionData
import com.palone.paloneapp.data.models.SubstitutionDataEntry
import com.palone.paloneapp.data.models.TimetableData
import com.palone.paloneapp.data.models.TimetableDataEntry
import com.palone.paloneapp.data.models.responses.timetable.TimetableRemoteDataResponse
import com.palone.paloneapp.data.models.timetableVariables.*

class Parsers {
    fun getTimetableDataResponseParsedToListOfTimetableData(dataResponse: TimetableRemoteDataResponse): List<TimetableData> {
        val timetableData = mutableListOf<TimetableData>()
        var lessonsSorted = mutableListOf<TimetableDataEntry>()
        val subjects = mutableListOf<Subject>()
        val mapTeachers = mutableListOf<Teacher>()
        val mapClasses = mutableListOf<SchoolClass>()
        val mapDays = mutableListOf<Day>()
        val mapDaysdefs = mutableListOf<DaysDef>()
        val mapCards = mutableListOf<Card>()
        val mapCardsPeriod = mutableMapOf<String, String>() //lessonid to period
        val mapClassrooms = mutableListOf<ClassRoom>()
        val mapGroups = mutableListOf<Group>()
        val lessonsList = mutableListOf<Lesson>()
        val actualLessonsList = mutableListOf<TimetableDataEntry>()
        val mapClassIdToGroup = mutableMapOf<String, MutableMap<String, String>>()






        dataResponse.response.dbiAccessorRes.tables.forEach { it1 ->
            if (it1.data_rows.isNotEmpty() && it1.id == "subjects")
                it1.data_rows.forEach { subjects.add(Subject(it.id, it.name, it.short)) }
        }
        dataResponse.response.dbiAccessorRes.tables.forEach { it1 ->
            if (it1.data_rows.isNotEmpty() && it1.id == "teachers")
                it1.data_rows.forEach { mapTeachers.add(Teacher(it.id, it.short)) }
        }
        dataResponse.response.dbiAccessorRes.tables.forEach { it1 ->
            if (it1.data_rows.isNotEmpty() && it1.id == "classes")
                it1.data_rows.forEach {
                    mapClasses.add(
                        SchoolClass(
                            it.id, it.name.replace("(", "")
                                .replace(")", "")
                                .replace("{", "")
                                .replace("}", "")
                                .replace(" ", "")
                        )
                    )
                }
        }
        dataResponse.response.dbiAccessorRes.tables.forEach { it1 ->
            if (it1.data_rows.isNotEmpty() && it1.id == "days")
                it1.data_rows.forEach { mapDays.add(Day(it.id, it.short)) }
        }
        dataResponse.response.dbiAccessorRes.tables.forEach { it1 ->
            if (it1.data_rows.isNotEmpty() && it1.id == "daysdefs")
                it1.data_rows.forEach {
                    mapDaysdefs.add(DaysDef(it.id, it.short, it.vals))
                }
        }
        dataResponse.response.dbiAccessorRes.tables.forEach { it1 ->
            if (it1.data_rows.isNotEmpty() && it1.id == "classrooms")
                it1.data_rows.forEach { mapClassrooms.add(ClassRoom(it.id, it.short)) }
        }
        dataResponse.response.dbiAccessorRes.tables.forEach { it1 ->
            if (it1.data_rows.isNotEmpty() && it1.id == "groups")
                it1.data_rows.forEach {
                    mapGroups.add(
                        Group(
                            it.id,
                            it.name,
                            mapClasses.find { it2 -> it2.id == it.classid } ?: SchoolClass(
                                "9999",
                                "Brak klasy"
                            ), divisionId = it.divisionid
                        )
                    )
                }
        }
        dataResponse.response.dbiAccessorRes.tables.forEach { it1 ->
            if (it1.data_rows.isNotEmpty() && it1.id == "lessons")
                it1.data_rows.forEach {
                    if (it.id.isNotEmpty()) {
                        lessonsList.add(
                            Lesson(
                                it.id,
                                subjects.find { it2 -> it2.id == it.subjectid } ?: Subject(
                                    "9999",
                                    "Brak przedmiotu",
                                    "Brak Przedniotu"
                                ),
                                mapTeachers.filter { it2 -> it.teacherids.contains(it2.id) },
                                mapGroups.filter { it2 -> it.groupids.contains(it2.id) },
                                mapClasses.filter { it2 -> it.classids.contains(it2.id) },
                                mapClassrooms.filter { it2 ->
                                    if (!it.classroomidss.isNullOrEmpty()) it.classroomidss[0].contains(
                                        it2.id
                                    ) else false
                                },
                                it.durationperiods
                            )
                        )
                    }
                }

        }
        dataResponse.response.dbiAccessorRes.tables.forEach { it1 ->
            if (it1.data_rows.isNotEmpty() && it1.id == "cards") {
                it1.data_rows.forEach {
                    mapCards.add(
                        Card(
                            it.id,
                            lessonsList.find { it2 -> it.lessonid == it2.id } ?: Lesson(
                                "9999",
                                Subject(
                                    "9999",
                                    "Brak przedmiotu",
                                    "Brak Przedniotu"
                                ),
                                listOf(Teacher("9999", "Brak danych")),
                                listOf(
                                    Group(
                                        "9999",
                                        "Brak danych",
                                        SchoolClass("9999", "Brak danych"),
                                        "9999"
                                    )
                                ),
                                listOf(SchoolClass("9999", "Brak danych")),
                                listOf(ClassRoom("9999", "Brak danych")),
                                9999
                            ),
                            if (it.period != "") it.period.toInt() else 9999,
                            mapDaysdefs.find { it2 -> it2.values.contains(it.days) } ?: DaysDef(
                                "9999", "Brak danych",
                                listOf("Brak danych")
                            ),
                            mapClassrooms.filter { it2 ->
                                if (it.classroomids.isNotEmpty()) it.classroomids.contains(
                                    it2.id
                                ) else false
                            }

                        )
                    )
                }
            }
        }
        val allSchoolClasses = mapClasses.distinct()


        allSchoolClasses.forEach { parentSchoolClass ->
            mapCards.filter { it.lesson.group.any { it2 -> parentSchoolClass == it2.schoolClass } }
                .forEach { it3 ->
                    if (it3.classRooms.size == 1) {
                        val classesInGroups = mutableListOf<String>()
                        it3.lesson.schoolClass.forEach { it4 -> classesInGroups.add(it4.name) }
                        timetableData.add(
                            TimetableData(
                                className = parentSchoolClass.name,
                                entries = listOf(
                                    TimetableDataEntry(
                                        it3.lesson.subject.shortName,
                                        it3.classRooms[0].shortName,
                                        it3.lesson.teacher[0].name,
                                        classesInGroups.toList(),
                                        it3.lesson.durationPeriod,
                                        it3.day.shortName,
                                        it3.period,
                                        it3.lesson.durationPeriod + it3.period - 1,
                                        it3.lesson.group.find { it4 -> it4.schoolClass == parentSchoolClass }?.name
                                            ?: "Brak danych",
                                        it3.day.id.replace("*", "").toInt()
                                    )
                                )
                            )
                        )
                    }
                }
        }
        return timetableData
    }

    fun getSubstitutionsFromHtml(rawData: String?): List<SubstitutionData> {
        var string = rawData
        if (string != null) {
            val match = "_RSS\\w{4}".toRegex().find(string)?.value ?: "_RSS\\w{3}".toRegex()
                .find(string)?.value

            //Log.i("match::", match!!)
            val datereg = "2022-\\w{2}-\\w{2}".toRegex()
            val date = datereg.find(string)?.value
            string = string.replace("$date", "")
            string = string.replace("$match", "")
            string = string.replace((10).toChar(), '_')
            string = string.replace("_", "")
            string = string.replace(
                """<div class=""><style type="text/css">. .signature {width:100px;text-Align:center;color:#a0a0a0}. .row {display:flex;flex-Direction:row;align-Items:baseline;font-Size:12px;margin-Bottom:1px}. .period {font-Size:12px;text-Align:center;white-Space:nowrap}. .period>span {}. .info {flex:1}. table {width:100%;table-Layout:auto}. tbody {border:2px solid #808080}. td {border:1px solid #808080;padding:2px}. .header {font-Size:16px;text-Align:center}. .time {font-Size:12px;text-Align:center;white-Space:pre}</style><div data-date=""><div style="text-align:center"><span class="print-font-resizable">""",
                ""
            )
            string = string.replace(
                """<div class=\"${match}\"><style type=\"text\/css\">.${match} .signature {width:100px;text-Align:center;color:#a0a0a0}\n.${match} .row {display:flex;flex-Direction:row;align-Items:baseline;font-Size:12px;margin-Bottom:1px}\n.${match} .period {font-Size:12px;text-Align:center;white-Space:nowrap}\n.${match} .period>span {}\n.${match} .info {flex:1}\n.${match} table {width:100%;table-Layout:auto}\n.${match} tbody {border:2px solid #808080}\n.${match} td {border:1px solid #808080;padding:2px}\n.${match} .header {font-Size:16px;text-Align:center}\n.${match} .time {font-Size:12px;text-Align:center;white-Space:pre}<\/style><div data-date=\"${date}\"><div style=\"text-align:center\"><span class=\"print-font-resizable\">""",
                ""
            )
            string = string.replace("""<style type=\"text\/css\">""", "")
            string = (string.replace(
                """<\/span><\/div><div style=\"text-align:center\"><span class=\"print-font-resizable\">""",
                ""
            ))
            string = (string.replace(
                """<\/span><\/div><div style=\"height:15px;flex:none\"><\/div><table><tbody class=\"print-nobreak\"><tr><td """,
                "|||"
            ))
            string = (string.replace(""" class=\"header\">""", ""))
            string = string.replace("""rowspan=\"""", "   ||||")
            string = string.replace(
                """<\/td><td class=\"period\"><span class=\"print-font-resizable\">""",
                " ||| "
            )
            string = string.replace(
                """<\/span><\/td><td class=\"what\"><span class=\"print-font-resizable\">""",
                " ||| "
            )
            string = string.replace(
                """<\/span><\/td><td class=\"info\"><span class=\"print-font-resizable\">Zastępstwa:""",
                " ||| "
            )
            string = string.replace(
                """<\/span><\/td><\/tr><\/tbody><tbody class=\"print-nobreak\"><tr><td""",
                ""
            )
            string = string.replace(
                """<\/span><\/td><td class=\"info\"><span class=\"print-font-resizable\">""",
                " ||| "
            )
            string = string.replace(
                """<\/span><\/td><\/tr><tr><td class=\"period\"><span class=\"print-font-resizable\">""",
                " ||| "
            )
            string = string.replace(
                """<img src=\"\/global\/pics\/ui\/absent_32.svg\" style=\"height:16px;display:inline-block;vertical-align:text-bottom;margin-right:5px\"\/>""",
                ""
            )
            string = string.replace("""<\/span><\/td><\/tr><\/tbody><\/table><\/div><\/div>""", "")
            string = string.replace("""Nieobecni nauczyciele:""", "   Nieobecni nauczyciele:")
            string = string.replace("""<s>""", "")
            string = string.replace("""<\/s>""", "")


            string = string.replace(
                """<div class=\"${match}\"><style type=\"text/css\">.${match} .signature {width:100px;text-Align:center;color:#a0a0a0}\n.${match} .row {display:flex;flex-Direction:row;align-Items:baseline;font-Size:12px;margin-Bottom:1px}\n.${match} .period {font-Size:12px;text-Align:center;white-Space:nowrap}\n.${match} .period>span {}\n.${match} .info {flex:1}\n.${match} table {width:100%;table-Layout:auto}\n.${match} tbody {border:2px solid #808080}\n.${match} td {border:1px solid #808080;padding:2px}\n.${match} .header {font-Size:16px;text-Align:center}\n.${match} .time {font-Size:12px;text-Align:center;white-Space:pre}</style><div data-date=\"${date}\"><div style=\"text-align:center\"><span class=\"print-font-resizable\">""",
                ""
            )
            string = string.replace("""<style type=\"text/css\">""", "")
            string = (string.replace(
                """</span></div><div style=\"text-align:center\"><span class=\"print-font-resizable\">""",
                ""
            ))
            string = (string.replace(
                """</span></div><div style=\"height:15px;flex:none\"></div><table><tbody class=\"print-nobreak\"><tr><td """,
                "|||"
            ))
            string = (string.replace(""" class=\"header\">""", ""))
            string = string.replace("""rowspan=\"""", "   ||||")
            string = string.replace(
                """</td><td class=\"period\"><span class=\"print-font-resizable\">""",
                "||| "
            )
            string = string.replace(
                """</span></td><td class=\"what\"><span class=\"print-font-resizable\">""",
                " ||| "
            )
            string = string.replace(
                """</span></td><td class=\"info\"><span class=\"print-font-resizable\">Zastępstwa:""",
                " ||| "
            )
            string = string.replace(
                """</span></td></tr></tbody><tbody class=\"print-nobreak\"><tr><td""",
                ""
            )
            string = string.replace(
                """</span></td><td class=\"info\"><span class=\"print-font-resizable\">""",
                " ||| "
            )
            string = string.replace(
                """</span></td></tr><tr><td class=\"period\"><span class=\"print-font-resizable\">""",
                " ||| "
            )
            string = string.replace(
                """<img src=\"/global/pics/ui/absent_32.svg\" style=\"height:16px;display:inline-block;vertical-align:text-bottom;margin-right:5px\"/>""",
                ""
            )
            string = string.replace("""</span></td></tr></tbody></table></div></div>""", "")
            string = string.replace("""Nieobecni nauczyciele:""", "   Nieobecni nauczyciele:")
            string = string.replace("""<s>""", "")
            string = string.replace("""</s>""", "")

            string = string.replace("""<style type="text/css">""", "")
            string = (string.replace(
                """</span></div><div style="text-align:center"><span class="print-font-resizable">""",
                ""
            ))
            string = (string.replace(
                """</span></div><div style="height:15px;flex:none"></div><table><tbody class="print-nobreak"><tr><td """,
                "|||"
            ))
            string = (string.replace(""" class="header">""", ""))
            string = string.replace("""rowspan="""", "   ||||")
            string = string.replace(
                """</td><td class="period"><span class="print-font-resizable">""",
                " ||| "
            )
            string = string.replace(
                """</span></td><td class="what"><span class="print-font-resizable">""",
                " ||| "
            )
            string = string.replace(
                """</span></td><td class="info"><span class="print-font-resizable">Zastępstwa:""",
                " ||| "
            )
            string = string.replace(
                """</span></td></tr></tbody><tbody class="print-nobreak"><tr><td""",
                ""
            )
            string = string.replace(
                """</span></td><td class="info"><span class="print-font-resizable">""",
                " ||| "
            )
            string = string.replace(
                """</span></td></tr><tr><td class="period"><span class="print-font-resizable">""",
                " ||| "
            )
            string = string.replace(
                """<img src="/global/pics/ui/absent_32.svg" style="height:16px;display:inline-block;vertical-align:text-bottom;margin-right:5px"/>""",
                ""
            )
            string = string.replace("""</span></td></tr></tbody></table></div></div>""", "")
            string = string.replace("""Nieobecni nauczyciele:""", "   Nieobecni nauczyciele:")
            string = string.replace("""<s>""", "")
            string = string.replace("""</s>""", "")


            string = string.replace(
                """<div class="${match}"><style type="text/css">.${match} .signature {width:100px;text-Align:center;color:#a0a0a0}n.${match} .row {display:flex;flex-Direction:row;align-Items:baseline;font-Size:12px;margin-Bottom:1px}n.${match} .period {font-Size:12px;text-Align:center;white-Space:nowrap}n.${match} .period>span {}n.${match} .info {flex:1}n.${match} table {width:100%;table-Layout:auto}n.${match} tbody {border:2px solid #808080}n.${match} td {border:1px solid #808080;padding:2px}n.${match} .header {font-Size:16px;text-Align:center}n.${match} .time {font-Size:12px;text-Align:center;white-Space:pre}</style><div data-date="${date}"><div style="text-align:center"><span class="print-font-resizable">""",
                ""
            )
            string = string.replace("""<style type="text/css">""", "")
            string = (string.replace(
                """</span></div><div style="text-align:center"><span class="print-font-resizable">""",
                ""
            ))
            string = (string.replace(
                """</span></div><div style="height:15px;flex:none"></div><table><tbody class="print-nobreak"><tr><td """,
                "|||"
            ))
            string = (string.replace(""" class="header">""", ""))
            string = string.replace("""rowspan="""", "   ||||")
            string = string.replace(
                """</td><td class="period"><span class="print-font-resizable">""",
                "||| "
            )
            string = string.replace(
                """</span></td><td class="what"><span class="print-font-resizable">""",
                " ||| "
            )
            string = string.replace(
                """</span></td><td class="info"><span class="print-font-resizable">Zastępstwa:""",
                " ||| "
            )
            string = string.replace(
                """</span></td></tr></tbody><tbody class="print-nobreak"><tr><td""",
                ""
            )
            string = string.replace(
                """</span></td><td class="info"><span class="print-font-resizable">""",
                " ||| "
            )
            string = string.replace(
                """</span></td></tr><tr><td class="period"><span class="print-font-resizable">""",
                " ||| "
            )
            string = string.replace(
                """<img src="/global/pics/ui/absent_32.svg" style="height:16px;display:inline-block;vertical-align:text-bottom;margin-right:5px"/>""",
                ""
            )
            string = string.replace("""</span></td></tr></tbody></table></div></div>""", "")
            string = string.replace("""Nieobecni nauczyciele:""", "   Nieobecni nauczyciele:")
            string = string.replace("""<s>""", "")
            string = string.replace("""</s>""", "")
            string = string.replace(
                """<img src="/global/pics/ui/absent32.svg" style="height:16px;display:inline-block;vertical-align:text-bottom;margin-right:5px"/>""",
                ""
            )
            string = string.replace(".", "")
            //string = string.replace(":","")
            val match2 = "\\w{8} \\w{2}:\\w{2}:\\w{2}".toRegex().find(string)?.value
                ?: "\\w{8} \\w{1}:\\w{2}:\\w{2}".toRegex().find(string)?.value
            string = string.replace(
                """</span></td></tr></tbody></table></div><div style="text-align:center;font-size:12px"><a href="https://wwwasctimetablescom" target="blank">wwwasctimetablescom</a> - $match2</div></div>""",
                ""
            )
            string = string.replace(
                """ Zmień salę lekcyjną:""",
                "\nZmień salę lekcyjną:"
            )
            string = string.replace(
                """  """,
                "\n"
            )
            string = string.replace(
                "\n\n",
                ""
            )
            string = string.replace(
                """ Nauczyciel:""",
                "\nNauczyciel:"
            )
            string = string.replace(
                """ <img src="/global/pics/ui/events32svg" style="height:16px;display:inline-block;vertical-align:text-bottom;margin-right:5px"/>""",
                ""
            )


            //val kek = string.split("|||").toMutableList()
            //Log.i("nice", string)
            val kek = string.split("||||").toMutableList()
            val xd: MutableList<MutableList<String>> = mutableListOf(mutableListOf(""))

            for (i in 0 until kek.size) {
                kek[i] = kek[i].removeRange(0, 2)
                xd.add(kek[i].split("|||").toMutableList())
            }
            //Log.i("asd", xd.toString())
            //println(xd)
            val result = mutableListOf<SubstitutionData>()
            for (a in 2 until xd.size) {
                var pickedClass = ""
                val lessons = mutableListOf<String>()
                val description = mutableListOf<String>()

                for (b in 0 until xd[a].size) {
                    if (b == 0)
                        pickedClass = xd[a][b]
                    if (b == 1) {
                        for (k in 1..xd[a].size step 3)
                            if (k < xd[a].size) {
                                lessons.add(xd[a][k])//ja bym to tu wjebał
                                description.add(xd[a][k + 1] + " " + xd[a][k + 2])
                            }
                    }
                }
                pickedClass = pickedClass.replace("(", "")
                pickedClass = pickedClass.replace(")", "")
                pickedClass = pickedClass.replace("{", "")
                pickedClass = pickedClass.replace("}", "")
                pickedClass = pickedClass.replace(" ", "")
                val list = mutableListOf<SubstitutionDataEntry>()
                for (i in 0 until description.size) {
                    val listOfDescription = listOf(
                        "(.* \\n)|(.* {3})".toRegex().find(
                            description[i]
                        )?.value?.replace("\n", ""),
                        description[i].replace(
                            "${
                                "(.* \\n)|(.* {3})".toRegex().find(
                                    description[i]
                                )?.value
                            }", ""
                        ).replace(
                            "${
                                "(,\\nZmień salę lekcyjną: .* ➔ .*)|(, Anulowano)".toRegex().find(
                                    description[i]
                                )?.value
                            }", ""
                        ).replace("\n", ""),
                        "(Zmień salę lekcyjną: .* ➔ .*)|(Anulowano)".toRegex().find(
                            description[i]
                        )?.value?.replace("\n", "")
                    )
                    list.add(
                        SubstitutionDataEntry(
                            lessons[i],
                            listOfDescription[0] ?: "",
                            listOfDescription[1] ?: "",
                            listOfDescription[2] ?: ""
                        )
                    )

                }
//                Log.i("mapka","$lessonToDescription")
                result.add(
                    SubstitutionData(
                        className = pickedClass,
                        entries = list
                    )
                )
            }

            return result//dodać do returna i pog, to wyżej już działą
        }
        return listOf(SubstitutionData(entries = listOf(SubstitutionDataEntry())))
    }//lepiej nie patrzeć, syf, cud że działa
}