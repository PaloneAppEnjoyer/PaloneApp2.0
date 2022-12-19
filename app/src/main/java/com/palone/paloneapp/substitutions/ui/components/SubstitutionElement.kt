package com.palone.paloneapp.substitutions.ui.substitutions_screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.palone.paloneapp.substitutions.data.models.SubstitutionData

@Composable
fun SubstitutionElement(substitutionData: SubstitutionData, onLongPress: () -> Unit = {}) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.primaryVariant,
        contentColor = MaterialTheme.colors.secondary
    ) {
        Row(modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onLongPress = {
                onLongPress()
            })
        }) {
            if (substitutionData.className.isNotEmpty())
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
                substitutionData.entries.forEach {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = it.lessons, fontSize = 30.sp)
                        Spacer(modifier = Modifier.width(5.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = if (it.teacherReplacement.contains("W tym dniu nie ma żadnych") || it.teacherReplacement.contains(
                                    "Brak informacji"
                                )
                            ) Alignment.CenterHorizontally else Alignment.Start
                        ) {
                            if (it.subject.isNotEmpty())
                                Text(
                                    modifier = Modifier.padding(top = 5.dp),
                                    text = it.subject,
                                    color = MaterialTheme.colors.secondary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            if (it.teacherReplacement.isNotEmpty())
                                Text(
                                    color = MaterialTheme.colors.primary,
                                    text = it.teacherReplacement
                                )

                            if (it.roomChange.isNotEmpty())
                                Text(
                                    text = it.roomChange,
                                    color = MaterialTheme.colors.primary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}