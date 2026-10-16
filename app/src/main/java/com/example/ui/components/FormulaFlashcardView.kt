package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.FormulaData
import com.example.data.LocalRepository
import com.example.model.FormulaItem
import com.example.ui.theme.*

@Composable
fun FormulaFlashcardView(
    repository: LocalRepository,
    onNavigateToTopic: (String) -> Unit,
    initialFormulaId: String? = null,
    onSwitchToBrowseMode: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = MaterialTheme.colorScheme.background == DarkInkBackground

    var selectedTopicFilter by remember { mutableStateOf("सभी") }
    var filterOnlyBookmarked by remember { mutableStateOf(false) }
    var filterOnlyReviewNeeded by remember { mutableStateOf(false) }

    var bookmarkedSet by remember { mutableStateOf(repository.getBookmarkedFormulas()) }
    var masteredSet by remember { mutableStateOf(repository.getMasteredFormulas()) }

    var isDeckShuffled by remember { mutableStateOf(false) }

    val topicFilterOptions = listOf("सभी", "विद्युत आवेश", "कूलॉम का नियम", "विद्युत क्षेत्र", "विद्युत द्विध्रुव", "विद्युत फ्लक्स", "गाउस का नियम")

    // Build the active study deck
    val currentDeck = remember(selectedTopicFilter, filterOnlyBookmarked, filterOnlyReviewNeeded, bookmarkedSet, masteredSet, isDeckShuffled) {
        var list = FormulaData.allFormulas.filter { formula ->
            val matchesTopic = selectedTopicFilter == "सभी" || formula.topicName.contains(selectedTopicFilter)
            val matchesBookmark = !filterOnlyBookmarked || bookmarkedSet.contains(formula.id)
            val matchesReview = !filterOnlyReviewNeeded || !masteredSet.contains(formula.id)
            matchesTopic && matchesBookmark && matchesReview
        }
        if (isDeckShuffled) {
            list = list.shuffled()
        }
        list
    }

    // Current index in deck
    var currentIndex by remember {
        val initialIdx = if (initialFormulaId != null) {
            FormulaData.allFormulas.indexOfFirst { it.id == initialFormulaId }.takeIf { it >= 0 } ?: 0
        } else {
            0
        }
        mutableIntStateOf(initialIdx)
    }

    // Ensure valid index when deck changes
    LaunchedEffect(currentDeck.size) {
        if (currentIndex >= currentDeck.size && currentDeck.isNotEmpty()) {
            currentIndex = 0
        }
    }

    val currentFormula = currentDeck.getOrNull(currentIndex)
    var isRevealed by remember(currentFormula?.id) { mutableStateOf(false) }
    var showCompletionSummary by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Mode Header & Quick Stats
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("flashcard_header_card"),
            colors = CardDefaults.cardColors(
                containerColor = if (isDark) DarkInkSurface else SoftPeachLight
            ),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, if (isDark) DarkInkBorder else SubtleBorder)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = DeepKesari.copy(alpha = 0.15f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Psychology,
                                    contentDescription = null,
                                    tint = DeepKesari,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "सक्रिय स्मरण (Active Recall)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = DeepKesari
                            )
                            Text(
                                text = "नाम व प्रतीकों से सूत्र याद करें",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Shuffle & Reset controls
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(
                            onClick = {
                                isDeckShuffled = !isDeckShuffled
                                currentIndex = 0
                                isRevealed = false
                            },
                            modifier = Modifier.testTag("flashcard_shuffle_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shuffle,
                                contentDescription = "Shuffle Flashcards",
                                tint = if (isDeckShuffled) DeepKesari else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        IconButton(
                            onClick = {
                                repository.resetAllFormulaMastery()
                                masteredSet = emptySet()
                            },
                            modifier = Modifier.testTag("flashcard_reset_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reset Mastery",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                // Progress Bar & Counter
                if (currentDeck.isNotEmpty()) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "कार्ड ${currentIndex + 1} / ${currentDeck.size}",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text(
                                    text = "✓ याद: ${masteredSet.size}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SuccessGreen,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "कुल सूत्र: ${FormulaData.allFormulas.size}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        val progressFraction = (currentIndex + 1).toFloat() / currentDeck.size.toFloat()
                        LinearProgressIndicator(
                            progress = { progressFraction },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = DeepKesari,
                            trackColor = if (isDark) DarkInkBorder else Color(0xFFFFE5C4)
                        )
                    }
                }
            }
        }

        // Topic Filter Chips
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "फ़िल्टर व टॉपिक चयन:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                FilterChip(
                    selected = filterOnlyBookmarked,
                    onClick = { filterOnlyBookmarked = !filterOnlyBookmarked },
                    label = { Text("बुकमार्क (${bookmarkedSet.size})", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    leadingIcon = {
                        Icon(
                            imageVector = if (filterOnlyBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = null,
                            modifier = Modifier.size(13.dp),
                            tint = DeepKesari
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = DeepKesari
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = filterOnlyBookmarked,
                        borderColor = if (filterOnlyBookmarked) DeepKesari else MaterialTheme.colorScheme.outline
                    )
                )
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(topicFilterOptions) { topicName ->
                    val isSelected = selectedTopicFilter == topicName
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            selectedTopicFilter = topicName
                            currentIndex = 0
                            isRevealed = false
                        },
                        label = {
                            Text(
                                topicName,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = DeepKesari
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

        // Main Flashcard Surface
        if (currentDeck.isEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = null,
                        tint = DeepKesari,
                        modifier = Modifier.size(36.dp)
                    )
                    Text(
                        text = "इस फ़िल्टर में कोई सूत्र उपलब्ध नहीं है।",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "कृपया अन्य विषय चुनें अथवा बुकमार्क फ़िल्टर हटाएं।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Button(
                        onClick = {
                            selectedTopicFilter = "सभी"
                            filterOnlyBookmarked = false
                            filterOnlyReviewNeeded = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DeepKesari)
                    ) {
                        Text("सभी सूत्र दिखाएं")
                    }
                }
            }
        } else if (currentFormula != null) {
            val isCurrentMastered = masteredSet.contains(currentFormula.id)
            val isCurrentBookmarked = bookmarkedSet.contains(currentFormula.id)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("flashcard_main_card")
                    .clickable { isRevealed = !isRevealed },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(
                    1.5.dp,
                    if (isRevealed) DeepKesari else MaterialTheme.colorScheme.outline
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Card Top Meta Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, DeepKesari.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = currentFormula.topicName,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = DeepKesari,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            if (isCurrentMastered) {
                                Surface(
                                    color = Color(0xFFDCFCE7),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "✓ याद है",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = SuccessGreen,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }

                            IconButton(
                                onClick = {
                                    repository.toggleFormulaBookmark(currentFormula.id)
                                    bookmarkedSet = repository.getBookmarkedFormulas()
                                },
                                modifier = Modifier.testTag("flashcard_bookmark_btn")
                            ) {
                                Icon(
                                    imageVector = if (isCurrentBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = "Bookmark",
                                    tint = if (isCurrentBookmarked) DeepKesari else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Formula Name
                    Column {
                        Text(
                            text = currentFormula.nameHindi,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 22.sp
                        )
                        Text(
                            text = currentFormula.nameEnglish,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Given Symbols & Meanings Section
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = if (isDark) DarkInkSurfaceVariant else SoftPeachLight,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, if (isDark) DarkInkBorder else SubtleBorder)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FormatListBulleted,
                                    contentDescription = null,
                                    tint = DeepKesari,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "दिए गए प्रतीक एवं अर्थ (Given Symbols):",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepKesari
                                )
                            }

                            currentFormula.symbolsMeaning.forEach { symbol ->
                                Text(
                                    text = "• $symbol",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 18.sp,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "SI मात्रक: ${currentFormula.siUnit}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    // Formula Section: Either Hidden Recall Prompt OR Revealed Math Expression
                    AnimatedContent(
                        targetState = isRevealed,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(220)) togetherWith
                                    fadeOut(animationSpec = tween(150))
                        },
                        label = "formula_reveal_animation"
                    ) { revealed ->
                        if (!revealed) {
                            // HIDDEN STATE: Prompts the student to actively recall
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("flashcard_hidden_box"),
                                color = if (isDark) Color(0xFF241B13) else Color(0xFFFFF7ED),
                                shape = RoundedCornerShape(14.dp),
                                border = BorderStroke(1.5.dp, DeepKesari.copy(alpha = 0.5f))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(18.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Hidden Formula",
                                        tint = DeepKesari,
                                        modifier = Modifier.size(28.dp)
                                    )

                                    Text(
                                        text = "सूत्र छिपा हुआ है (Formula Hidden)",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = DeepKesari
                                    )

                                    Text(
                                        text = "नाम व ऊपर दिए गए प्रतीकों के आधार पर सूत्र मन में याद करें। तैयार होने पर नीचे बटन दबाएं!",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = TextAlign.Center,
                                        lineHeight = 18.sp
                                    )

                                    Button(
                                        onClick = { isRevealed = true },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(44.dp)
                                            .testTag("flashcard_reveal_btn"),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = DeepKesari,
                                            contentColor = Color.White
                                        ),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Visibility,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "सूत्र देखें (Reveal Formula)",
                                            style = MaterialTheme.typography.labelLarge,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        } else {
                            // REVEALED STATE: Shows formula & self-assessment controls
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("flashcard_revealed_box"),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = if (isDark) Color(0xFF221A12) else SoftPeach,
                                    shape = RoundedCornerShape(14.dp),
                                    border = BorderStroke(1.5.dp, DeepKesari)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "सत्यापित सूत्र (Formula):",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = DeepKesari
                                            )
                                            TextButton(
                                                onClick = { isRevealed = false },
                                                modifier = Modifier
                                                    .height(28.dp)
                                                    .testTag("flashcard_hide_btn"),
                                                contentPadding = PaddingValues(horizontal = 6.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.VisibilityOff,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(14.dp),
                                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = "पुनः छिपाएं",
                                                    fontSize = 11.sp,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        }

                                        Text(
                                            text = currentFormula.mathExpression,
                                            color = if (isDark) SaffronLight else DeepKesari,
                                            fontSize = 21.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Serif,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.padding(vertical = 8.dp)
                                        )

                                        Text(
                                            text = "शर्त: ${currentFormula.conditions}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontStyle = FontStyle.Italic,
                                            fontSize = 11.sp,
                                            textAlign = TextAlign.Center
                                        )

                                        if (currentFormula.workedExample != null) {
                                            HorizontalDivider(
                                                modifier = Modifier.padding(vertical = 4.dp),
                                                color = DeepKesari.copy(alpha = 0.2f)
                                            )
                                            Text(
                                                text = "उदाहरण: ${currentFormula.workedExample}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                fontSize = 11.sp
                                            )
                                        }
                                    }
                                }

                                // Self-Assessment Recall Feedback
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = if (isDark) DarkInkSurface else Color(0xFFFAFAFA),
                                    shape = RoundedCornerShape(12.dp),
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = "क्या आपको सूत्र सही याद आया?",
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            // Review again button
                                            OutlinedButton(
                                                onClick = {
                                                    repository.markFormulaMastered(currentFormula.id, false)
                                                    masteredSet = repository.getMasteredFormulas()
                                                    if (currentIndex < currentDeck.size - 1) {
                                                        currentIndex++
                                                    }
                                                },
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .testTag("flashcard_review_btn"),
                                                shape = RoundedCornerShape(8.dp),
                                                border = BorderStroke(1.dp, WarningAmber)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Refresh,
                                                    contentDescription = null,
                                                    tint = WarningAmber,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    text = "दोहराना है 🔁",
                                                    fontSize = 12.sp,
                                                    color = WarningAmber,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }

                                            // Knew it button
                                            Button(
                                                onClick = {
                                                    repository.markFormulaMastered(currentFormula.id, true)
                                                    masteredSet = repository.getMasteredFormulas()
                                                    if (currentIndex < currentDeck.size - 1) {
                                                        currentIndex++
                                                    }
                                                },
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .testTag("flashcard_knew_btn"),
                                                shape = RoundedCornerShape(8.dp),
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = SuccessGreen,
                                                    contentColor = Color.White
                                                )
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    text = "हाँ, याद था 👍",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Direct Link to Topic Notes
                    FilledTonalButton(
                        onClick = { onNavigateToTopic(currentFormula.topicId) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("flashcard_topic_btn"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = DeepKesari
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.MenuBook,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "संबंधित नोट्स पढ़ें (${currentFormula.topicName})",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Bottom Navigation Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = {
                        if (currentIndex > 0) {
                            currentIndex--
                        }
                    },
                    enabled = currentIndex > 0,
                    modifier = Modifier.testTag("flashcard_prev_btn"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Previous Card",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("पिछला")
                }

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "${currentIndex + 1} / ${currentDeck.size}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                Button(
                    onClick = {
                        if (currentIndex < currentDeck.size - 1) {
                            currentIndex++
                        } else {
                            showCompletionSummary = true
                        }
                    },
                    modifier = Modifier.testTag("flashcard_next_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepKesari),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(if (currentIndex == currentDeck.size - 1) "समाप्त" else "अगला")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next Card",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Session Completion Modal/Card
        if (showCompletionSummary) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("flashcard_summary_card"),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDark) DarkInkSurface else Color(0xFFF0FDF4)
                ),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, SuccessGreen)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = DeepKesari,
                        modifier = Modifier.size(40.dp)
                    )

                    Text(
                        text = "शाबाश! आपने फ़्लैशकार्ड सत्र पूरा कर लिया 🎉",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "कुल सूत्र: ${currentDeck.size} | याद किए गए: ${masteredSet.size}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                showCompletionSummary = false
                                currentIndex = 0
                                isRevealed = false
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("flashcard_restart_deck"),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("पुनः अभ्यास करें")
                        }

                        Button(
                            onClick = {
                                showCompletionSummary = false
                                onSwitchToBrowseMode()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("flashcard_return_browse"),
                            colors = ButtonDefaults.buttonColors(containerColor = DeepKesari),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("सूची दृश्य")
                        }
                    }
                }
            }
        }
    }
}
