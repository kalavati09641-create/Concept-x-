package com.example.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.data.QuizData
import com.example.model.QuizQuestion
import com.example.ui.theme.*

@Composable
fun PracticeScreen(
    repository: LocalRepository,
    initialTopicId: String? = null,
    modifier: Modifier = Modifier
) {
    var selectedTopicFilter by remember { mutableStateOf(initialTopicId ?: "all") }
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var isSubmitted by remember { mutableStateOf(false) }
    var correctCount by remember { mutableIntStateOf(0) }
    var quizCompleted by remember { mutableStateOf(false) }

    val questions = remember(selectedTopicFilter) {
        if (selectedTopicFilter == "all") QuizData.allQuestions
        else QuizData.getQuestionsForTopic(selectedTopicFilter)
    }

    fun resetQuiz() {
        currentIndex = 0
        selectedOptionIndex = null
        isSubmitted = false
        correctCount = 0
        quizCompleted = false
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("practice_quiz_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "अभ्यास क्विज़ (Practice Mode)",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "अवधारणात्मक बहुविकल्पीय प्रश्न (MCQs) • तात्कालिक स्पष्टीकरण सहित",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Topic Filter Chips
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    val isAllSelected = selectedTopicFilter == "all"
                    FilterChip(
                        selected = isAllSelected,
                        onClick = {
                            selectedTopicFilter = "all"
                            resetQuiz()
                        },
                        label = {
                            Text(
                                "संपूर्ण अध्याय (All Questions)",
                                fontWeight = if (isAllSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = DeepKesari,
                            containerColor = MaterialTheme.colorScheme.surface,
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isAllSelected,
                            borderColor = if (isAllSelected) DeepKesari else MaterialTheme.colorScheme.outline
                        )
                    )
                }
                items(PhysicsChapterData.topics.take(6)) { topic ->
                    val isSelected = selectedTopicFilter == topic.id
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            selectedTopicFilter = topic.id
                            resetQuiz()
                        },
                        label = {
                            Text(
                                topic.titleHindi,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = DeepKesari,
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

        if (questions.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Box(modifier = Modifier.padding(24.dp), contentAlignment = Alignment.Center) {
                        Text("इस विषय के लिए कोई प्रश्न नहीं मिला।", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        } else if (quizCompleted) {
            // Quiz Result Screen
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    shape = RoundedCornerShape(18.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            color = SoftPeach,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.size(72.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.EmojiEvents,
                                    contentDescription = null,
                                    tint = PrimarySaffron,
                                    modifier = Modifier.size(44.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "क्विज़ पूर्ण हुई!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "आपका परिणाम: $correctCount / ${questions.size} सही",
                            style = MaterialTheme.typography.titleMedium,
                            color = DeepKesari,
                            fontWeight = FontWeight.Bold
                        )

                        val percent = (correctCount.toFloat() / questions.size * 100).toInt()
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "सफलता दर: $percent%",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = { resetQuiz() },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = DeepKesari),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("पुनः अभ्यास करें (Retry)", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        } else {
            val q = questions.getOrNull(currentIndex) ?: questions.first()

            // Progress Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "प्रश्न ${currentIndex + 1} / ${questions.size}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = DeepKesari
                    )

                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(6.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, DeepKesari.copy(alpha = 0.2f))
                    ) {
                        Text(
                            text = q.topicTitle,
                            style = MaterialTheme.typography.labelSmall,
                            color = DeepKesari,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Question Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = q.questionHindi,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 26.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = q.questionEnglish,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Options
            items(q.optionsHindi.indices.toList()) { optIdx ->
                val optText = q.optionsHindi[optIdx]
                val isSelected = selectedOptionIndex == optIdx
                val isCorrect = optIdx == q.correctIndex

                val borderColor = when {
                    isSubmitted && isCorrect -> SuccessGreen
                    isSubmitted && isSelected && !isCorrect -> Color(0xFFDC2626)
                    isSelected -> DeepKesari
                    else -> MaterialTheme.colorScheme.outline
                }

                val containerColor = when {
                    isSubmitted && isCorrect -> Color(0xFFDCFCE7)
                    isSubmitted && isSelected && !isCorrect -> Color(0xFFFEE2E2)
                    isSelected -> MaterialTheme.colorScheme.primaryContainer
                    else -> MaterialTheme.colorScheme.surface
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("quiz_option_$optIdx")
                        .clickable(enabled = !isSubmitted) {
                            selectedOptionIndex = optIdx
                        },
                    colors = CardDefaults.cardColors(containerColor = containerColor),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        if (isSelected || (isSubmitted && isCorrect)) 1.5.dp else 1.dp,
                        borderColor
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = when {
                                isSubmitted && isCorrect -> SuccessGreen
                                isSubmitted && isSelected && !isCorrect -> Color(0xFFDC2626)
                                isSelected -> DeepKesari
                                else -> MaterialTheme.colorScheme.surfaceVariant
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.size(34.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${('A'.code + optIdx).toChar()}",
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected || (isSubmitted && isCorrect) || (isSubmitted && isSelected && !isCorrect)) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Text(
                            text = optText,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = when {
                                isSubmitted && isCorrect -> SuccessGreen
                                isSubmitted && isSelected && !isCorrect -> Color(0xFF991B1B)
                                else -> MaterialTheme.colorScheme.onSurface
                            },
                            modifier = Modifier.weight(1f)
                        )

                        if (isSubmitted) {
                            if (isCorrect) {
                                Icon(Icons.Default.CheckCircle, contentDescription = "Correct", tint = SuccessGreen)
                            } else if (isSelected) {
                                Icon(Icons.Default.Cancel, contentDescription = "Wrong", tint = Color(0xFFDC2626))
                            }
                        }
                    }
                }
            }

            // Explanation box when submitted
            if (isSubmitted) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = DeepKesari,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "अवधारणात्मक स्पष्टीकरण (Explanation):",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepKesari
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = q.explanationHindi,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }

            // Action Buttons
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (!isSubmitted) {
                        Button(
                            onClick = {
                                if (selectedOptionIndex != null) {
                                    isSubmitted = true
                                    if (selectedOptionIndex == q.correctIndex) {
                                        correctCount++
                                        repository.recordQuizCompletion(1)
                                    }
                                }
                            },
                            enabled = selectedOptionIndex != null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("quiz_submit_btn"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DeepKesari,
                                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("उत्तर जमा करें (Submit Answer)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    } else {
                        Button(
                            onClick = {
                                if (currentIndex < questions.size - 1) {
                                    currentIndex++
                                    selectedOptionIndex = null
                                    isSubmitted = false
                                } else {
                                    quizCompleted = true
                                    val filterTitle = if (selectedTopicFilter == "all") "संपूर्ण भौतिकी क्विज़" else (questions.firstOrNull()?.topicTitle ?: "क्विज़")
                                    repository.saveQuizAttempt(filterTitle, correctCount, questions.size)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("quiz_next_btn"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DeepKesari,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                if (currentIndex < questions.size - 1) "अगला प्रश्न (Next Question) →" else "परिणाम देखें (View Results)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
