package com.example.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LocalRepository
import com.example.data.PhysicsChapterData
import com.example.model.Subject
import com.example.model.Topic
import com.example.ui.theme.DeepKesari
import com.example.ui.theme.PrimarySaffron
import com.example.ui.theme.SoftPeach
import com.example.ui.theme.SuccessGreen

@Composable
fun SubjectsScreen(
    repository: LocalRepository,
    onNavigateToTopic: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSubjectId by remember { mutableStateOf("physics") }
    val completedTopics = remember { repository.getCompletedTopics() }

    val activeSubject = remember(selectedSubjectId) {
        PhysicsChapterData.subjects.find { it.id == selectedSubjectId } ?: PhysicsChapterData.subjects.first()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("subjects_screen_list"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Subjects Selector Tabs
        item {
            Column {
                Text(
                    text = "कक्षा 12वीं विषय (Class 12 MPBSE)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(PhysicsChapterData.subjects) { subject ->
                        val isSelected = subject.id == selectedSubjectId
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedSubjectId = subject.id },
                            label = {
                                Text(
                                    text = subject.nameHindi,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = if (subject.isAvailable) Icons.AutoMirrored.Filled.MenuBook else Icons.Default.Lock,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = DeepKesari,
                                selectedLeadingIconColor = DeepKesari,
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) DeepKesari else MaterialTheme.colorScheme.outline
                            )
                        )
                    }
                }
            }
        }

        // Active Subject Details Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = activeSubject.nameHindi,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(activeSubject.colorHex)
                        )

                        Surface(
                            color = if (activeSubject.isAvailable) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(6.dp),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (activeSubject.isAvailable) DeepKesari.copy(alpha = 0.3f) else MaterialTheme.colorScheme.outline
                            )
                        ) {
                            Text(
                                text = if (activeSubject.isAvailable) "सक्रिय पाठ्यक्रम" else "आगामी",
                                color = if (activeSubject.isAvailable) DeepKesari else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = activeSubject.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        if (!activeSubject.isAvailable) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.HourglassTop,
                            contentDescription = null,
                            tint = DeepKesari,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "${activeSubject.nameHindi} के नोट्स संकलित किए जा रहे हैं।",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "वर्तमान में कक्षा 12वीं भौतिक विज्ञान (Physics) के प्रथम अध्याय 'विद्युत आवेश तथा क्षेत्र' के संपूर्ण प्रमाणित नोट्स, सूत्र और अभ्यास उपलब्ध हैं।",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { selectedSubjectId = "physics" },
                            colors = ButtonDefaults.buttonColors(containerColor = DeepKesari),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("भौतिक विज्ञान पर जाएं", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        } else {
            // Chapter 1 Header
            item {
                val ch = PhysicsChapterData.currentChapter
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "अध्याय ${ch.number}: ${ch.titleHindi}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = ch.titleEnglish,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${ch.unitHindi} • ${ch.boardMarksWeightage}",
                        style = MaterialTheme.typography.labelMedium,
                        color = DeepKesari,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = ch.summaryHindi,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp
                    )
                }
            }

            item {
                Text(
                    text = "पाठ्यक्रम के सभी 12 विषय (All 12 Syllabus Topics):",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(PhysicsChapterData.topics) { topic ->
                TopicListItem(
                    topic = topic,
                    isCompleted = completedTopics.contains(topic.id),
                    onClick = { onNavigateToTopic(topic.id) }
                )
            }
        }
    }
}

@Composable
fun TopicListItem(
    topic: Topic,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("topic_item_${topic.id}")
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isCompleted) SuccessGreen.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = if (isCompleted) SuccessGreen.copy(alpha = 0.12f) else MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (isCompleted) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Completed",
                            tint = SuccessGreen,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text(
                            text = "${topic.number}",
                            fontWeight = FontWeight.Bold,
                            color = DeepKesari,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = topic.titleHindi,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = topic.titleEnglish,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "~${topic.estimatedMinutes} मिनट",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    if (topic.isCoreBoardTopic) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "एमपी बोर्ड अनिवार्य",
                                fontSize = 10.sp,
                                color = DeepKesari,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Open Topic",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
