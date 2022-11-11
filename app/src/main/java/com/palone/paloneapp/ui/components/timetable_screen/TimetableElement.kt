package com.palone.paloneapp.ui.components.timetable_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.palone.paloneapp.data.models.TimetableDataEntry

@Composable
fun TimetableElement(
    data: List<TimetableDataEntry>,
    modifier: Modifier = Modifier, lessonNumber: Int, showSchoolClass: Boolean = false
) {
    if (data.isNotEmpty())
        Card(
            modifier = modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.primaryVariant
        ) {
            Row(modifier = Modifier.padding(15.dp)) {
                Card(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(30),
                    modifier = Modifier
                        .padding(5.dp)
                        .padding(end = 10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(5.dp),

                        ) {
                        Text(
                            text = lessonNumber.toString(),
                            fontSize = 30.sp, modifier = Modifier.padding(end = 3.dp)
                        )
                        Column {
                            Text(
                                text = "08:45",
                                fontSize = 10.sp,
                            )
                            Text(
                                text = "09:30",
                                fontSize = 10.sp,
                            )
                        }
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    data.sortedBy { it.groupName }.forEach {
                        Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.width(
                                    LocalConfiguration.current.screenWidthDp.dp - 140.dp
                                )
                            ) {
                                Text(
                                    text = it.subjectName,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.secondary
                                )

                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                Text(
                                    text = it.teacherShortName,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.primary
                                )
                                Text(text = it.groupName, color = MaterialTheme.colors.primary)
                                Text(text = it.classroomsName, fontSize = 17.sp)
                                if (showSchoolClass) it.schoolClassNames.forEach { Text(text = it) }
                            }
                        }
                    }
                }
            }

        }

}
