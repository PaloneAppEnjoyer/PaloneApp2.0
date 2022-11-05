package com.palone.paloneapp.ui.components.home_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.palone.paloneapp.data.models.SubstitutionData

@Composable
fun SubstitutionElement(substitutionData: SubstitutionData) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        backgroundColor = MaterialTheme.colors.primaryVariant,
        contentColor = MaterialTheme.colors.secondary
    ) {
        Row {
            if (!substitutionData.className.isNullOrEmpty())
                Card(
                    shape = RoundedCornerShape(14.dp),
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.primaryVariant, modifier = Modifier
                        .padding(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text(text = substitutionData.className, fontSize = 15.sp)
                    }
                }

            Column(modifier = Modifier.padding(10.dp)) {
                substitutionData.entries?.forEach {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = it.lessons ?: "", fontSize = 30.sp)
                        Spacer(modifier = Modifier.width(5.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = if (it.teacherReplacement?.contains("W tym dniu nie ma żadnych") == true || it.teacherReplacement?.contains(
                                    "Brak informacji"
                                ) == true
                            ) Alignment.CenterHorizontally else Alignment.Start
                        ) {
                            if (!it.subject.isNullOrEmpty())
                                Text(
                                    modifier = Modifier.padding(top = 5.dp),
                                    text = it.subject,
                                    color = MaterialTheme.colors.secondary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            if (!it.teacherReplacement.isNullOrEmpty())
                                Text(
                                    color = MaterialTheme.colors.primary,
                                    text = it.teacherReplacement
                                )

                            if (!it.roomChange.isNullOrEmpty())
                                Text(
                                    text = it.roomChange,
                                    color = MaterialTheme.colors.primary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }//(([A-ZĘ€ÓŁŚĄŻŹĆŃ][a-zę€ółśążźćń]*\s[A-ZĘ€ÓŁŚĄŻŹĆŃ][a-zę€ółśążźćń]*-[A-ZĘ€ÓŁŚĄŻŹĆŃ][a-zę€ółśążźćń]*)|([A-ZĘ€ÓŁŚĄŻŹĆŃ][a-zę€ółśążźćń]*\s[A-ZĘ€ÓŁŚĄŻŹĆŃ][a-zę€ółśążźćń]*))\s➔\s(([A-ZĘ€ÓŁŚĄŻŹĆŃ][a-zę€ółśążźćń]*\s[A-ZĘ€ÓŁŚĄŻŹĆŃ][a-zę€ółśążźćń]*-[A-ZĘ€ÓŁŚĄŻŹĆŃ][a-zę€ółśążźćń]*)|([A-ZĘ€ÓŁŚĄŻŹĆŃ][a-zę€ółśążźćń]*\s[A-ZĘ€ÓŁŚĄŻŹĆŃ][a-zę€ółśążźćń]*))
            }
        }
    }
}