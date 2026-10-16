package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.FormulaData
import com.example.data.LocalRepository
import com.example.model.FormulaItem
import com.example.ui.components.FormulaCard
import com.example.ui.components.FormulaFlashcardView
import com.example.ui.theme.DeepKesari
import com.example.ui.theme.SoftPeach
import com.example.ui.theme.SoftPeachLight

@Composable
fun FormulaBankScreen(
    repository: LocalRepository,
    onNavigateToTopic: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isFlashcardMode by remember { mutableStateOf(false) }
    var flashcardTargetFormulaId by remember { mutableStateOf<String?>(null) }

    var searchQuery by remember { mutableStateOf("") }
    var selectedFilterTopic by remember { mutableStateOf("सभी") }
    var showOnlyBookmarked by remember { mutableStateOf(false) }

    var bookmarkedSet by remember { mutableStateOf(repository.getBookmarkedFormulas()) }
    val masteredSet by remember { mutableStateOf(repository.getMasteredFormulas()) }

    val topicFilterOptions = listOf("सभी", "विद्युत आवेश", "कूलॉम का नियम", "विद्युत क्षेत्र", "विद्युत द्विध्रुव", "विद्युत फ्लक्स", "गाउस का नियम")

    val filteredFormulas = remember(searchQuery, selectedFilterTopic, showOnlyBookmarked, bookmarkedSet) {
        FormulaData.allFormulas.filter { formula ->
            val matchesSearch = searchQuery.isBlank() ||
                    formula.nameHindi.contains(searchQuery, ignoreCase = true) ||
                    formula.nameEnglish.contains(searchQuery, ignoreCase = true) ||
                    formula.mathExpression.contains(searchQuery, ignoreCase = true)

            val matchesTopic = selectedFilterTopic == "सभी" || formula.topicName.contains(selectedFilterTopic)
            val matchesBookmark = !showOnlyBookmarked || bookmarkedSet.contains(formula.id)

            matchesSearch && matchesTopic && matchesBookmark
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("formula_bank_container")
    ) {
        // Top Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text(
                text = "सूत्र बैंक (Formula Bank)",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "कक्षा 12वीं भौतिकी के सभी प्रमाणित सूत्र, SI मात्रक एवं सक्रिय स्मरण फ़्लैशकार्ड।",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // View Mode Tab Switcher: Browse List vs Flashcard Mode
        TabRow(
            selectedTabIndex = if (isFlashcardMode) 1 else 0,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("formula_mode_tab_row"),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = DeepKesari,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[if (isFlashcardMode) 1 else 0]),
                    color = DeepKesari
                )
            }
        ) {
            Tab(
                selected = !isFlashcardMode,
                onClick = {
                    isFlashcardMode = false
                    flashcardTargetFormulaId = null
                },
                text = {
                    Text(
                        text = "सूत्र सूची (${FormulaData.allFormulas.size})",
                        fontWeight = if (!isFlashcardMode) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 13.sp
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = "Browse List",
                        modifier = Modifier.size(18.dp)
                    )
                },
                modifier = Modifier.testTag("formula_tab_browse")
            )

            Tab(
                selected = isFlashcardMode,
                onClick = { isFlashcardMode = true },
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "फ़्लैशकार्ड मोड",
                            fontWeight = if (isFlashcardMode) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp
                        )
                        if (masteredSet.isNotEmpty()) {
                            Surface(
                                color = DeepKesari.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = "${masteredSet.size} याद",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepKesari,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = "Flashcard Recall Mode",
                        modifier = Modifier.size(18.dp)
                    )
                },
                modifier = Modifier.testTag("formula_tab_flashcards")
            )
        }

        if (isFlashcardMode) {
            // Interactive Flashcard Recall Mode
            FormulaFlashcardView(
                repository = repository,
                onNavigateToTopic = onNavigateToTopic,
                initialFormulaId = flashcardTargetFormulaId,
                onSwitchToBrowseMode = {
                    isFlashcardMode = false
                    flashcardTargetFormulaId = null
                },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Browse Mode: Standard list with search, filtering, and direct flashcard launches
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("formula_bank_scroll"),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Interactive Callout Banner to launch Flashcards
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("formula_flashcard_prompt_banner"),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, DeepKesari.copy(alpha = 0.35f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = DeepKesari,
                                    modifier = Modifier.size(38.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Psychology,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                }

                                Column {
                                    Text(
                                        text = "सक्रिय स्मरण (Flashcard Mode)",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = DeepKesari
                                    )
                                    Text(
                                        text = "सूत्र छिपाएं और नाम व प्रतीकों से याद करने का अभ्यास करें!",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            Button(
                                onClick = { isFlashcardMode = true },
                                colors = ButtonDefaults.buttonColors(containerColor = DeepKesari),
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.testTag("formula_start_flashcard_btn")
                            ) {
                            Text("अभ्यास करें", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // Search Input
                item {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("formula_search_input"),
                        placeholder = { Text("सूत्र या राशि खोजें (उदा: E, q, द्विध्रुव, flux)", fontSize = 13.sp) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = DeepKesari)
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DeepKesari,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }

                // Filter Pills and Bookmark Toggle
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "टॉपिक अनुसार फ़िल्टर:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            FilterChip(
                                selected = showOnlyBookmarked,
                                onClick = { showOnlyBookmarked = !showOnlyBookmarked },
                                label = { Text("सहेजे गए (${bookmarkedSet.size})", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = if (showOnlyBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp),
                                        tint = DeepKesari
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
                                    selected = showOnlyBookmarked,
                                    borderColor = if (showOnlyBookmarked) DeepKesari else MaterialTheme.colorScheme.outline
                                )
                            )
                        }

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(topicFilterOptions) { topicName ->
                                val isSelected = selectedFilterTopic == topicName
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { selectedFilterTopic = topicName },
                                    label = { Text(topicName, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
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
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "कुल सूत्र: ${filteredFormulas.size}",
                            style = MaterialTheme.typography.labelSmall,
                            color = DeepKesari,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "💡 कार्ड पर 'फ़्लैशकार्ड स्मरण' दबाकर अभ्यास करें",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (filteredFormulas.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = if (showOnlyBookmarked) "कोई बुकमार्क किया गया सूत्र नहीं मिला।" else "कोई सूत्र नहीं मिला।",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                } else {
                    items(filteredFormulas) { formula ->
                        FormulaCard(
                            formula = formula,
                            isBookmarked = bookmarkedSet.contains(formula.id),
                            onBookmarkToggle = { fid ->
                                repository.toggleFormulaBookmark(fid)
                                bookmarkedSet = repository.getBookmarkedFormulas()
                            },
                            onNavigateToTopic = onNavigateToTopic,
                            onPracticeFlashcard = { fid ->
                                flashcardTargetFormulaId = fid
                                isFlashcardMode = true
                            }
                        )
                    }
                }
            }
        }
    }
}

