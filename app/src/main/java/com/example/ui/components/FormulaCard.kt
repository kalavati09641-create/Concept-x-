package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FormulaItem
import com.example.ui.theme.DarkInkBackground
import com.example.ui.theme.DeepKesari
import com.example.ui.theme.PrimarySaffron
import com.example.ui.theme.SaffronLight
import com.example.ui.theme.SoftPeach

@Composable
fun FormulaCard(
    formula: FormulaItem,
    isBookmarked: Boolean,
    onBookmarkToggle: (String) -> Unit,
    onNavigateToTopic: ((String) -> Unit)? = null,
    onPracticeFlashcard: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val isDark = MaterialTheme.colorScheme.background == DarkInkBackground
    var isHiddenForRecall by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("formula_card_${formula.id}"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = formula.nameHindi,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = formula.nameEnglish,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { isHiddenForRecall = !isHiddenForRecall },
                        modifier = Modifier.testTag("toggle_hide_formula_${formula.id}")
                    ) {
                        Icon(
                            imageVector = if (isHiddenForRecall) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (isHiddenForRecall) "Show Formula" else "Hide Formula for Recall",
                            tint = if (isHiddenForRecall) DeepKesari else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(
                        onClick = { onBookmarkToggle(formula.id) },
                        modifier = Modifier.testTag("bookmark_formula_${formula.id}")
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = if (isBookmarked) "Remove Bookmark" else "Bookmark Formula",
                            tint = if (isBookmarked) DeepKesari else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // High-Contrast Formula Panel with Cream Background
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (isHiddenForRecall) isHiddenForRecall = false
                    },
                color = if (isDark) Color(0xFF221A12) else SoftPeach,
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isDark) Color(0xFF6B3A14) else Color(0xFFFFD8A8)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isHiddenForRecall) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "🔒 सूत्र छिपा है (स्मरण करें)",
                                color = DeepKesari,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "देखने हेतु टैप करें",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }
                    } else {
                        Text(
                            text = formula.mathExpression,
                            color = if (isDark) SaffronLight else DeepKesari,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            letterSpacing = 0.5.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Symbols Breakdown
            Text(
                text = "प्रतीकों का अर्थ (Symbols Meaning):",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = DeepKesari
            )
            Spacer(modifier = Modifier.height(4.dp))
            formula.symbolsMeaning.forEach { symbol ->
                Text(
                    text = "• $symbol",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 4.dp, bottom = 2.dp),
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // SI Unit and Conditions
            Row(modifier = Modifier.fillMaxWidth()) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DeepKesari.copy(alpha = 0.25f))
                ) {
                    Text(
                        text = "SI मात्रक: ${formula.siUnit}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = DeepKesari,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "लागू होने की शर्त: ${formula.conditions}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                lineHeight = 18.sp
            )

            if (onNavigateToTopic != null || onPracticeFlashcard != null) {
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (onPracticeFlashcard != null) {
                        OutlinedButton(
                            onClick = { onPracticeFlashcard(formula.id) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("card_flashcard_btn_${formula.id}"),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, DeepKesari)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Psychology,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = DeepKesari
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "फ़्लैशकार्ड स्मरण",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = DeepKesari
                            )
                        }
                    }

                    if (onNavigateToTopic != null) {
                        FilledTonalButton(
                            onClick = { onNavigateToTopic(formula.topicId) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("card_notes_btn_${formula.id}"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = DeepKesari
                            )
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "नोट्स पढ़ें",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
