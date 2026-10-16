package com.example.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import com.example.data.FormulaData
import com.example.data.LocalRepository
import com.example.data.PhysicsChapterData
import com.example.model.UserNote
import com.example.ui.components.FormulaCard
import com.example.ui.theme.DeepKesari
import com.example.util.PdfExporter
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BookmarksNotesScreen(
    repository: LocalRepository,
    onNavigateToTopic: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Bookmarks, 1: My Notes

    var bookmarkedTopics by remember { mutableStateOf(repository.getBookmarkedTopics()) }
    var bookmarkedFormulas by remember { mutableStateOf(repository.getBookmarkedFormulas()) }
    var userNotes by remember { mutableStateOf(repository.getUserNotes()) }

    var showAddNoteDialog by remember { mutableStateOf(false) }
    var newNoteContent by remember { mutableStateOf("") }
    var selectedTopicForNote by remember { mutableStateOf(PhysicsChapterData.topics.first().id) }

    val savedTopicsList = remember(bookmarkedTopics) {
        PhysicsChapterData.topics.filter { bookmarkedTopics.contains(it.id) }
    }

    val savedFormulasList = remember(bookmarkedFormulas) {
        FormulaData.allFormulas.filter { bookmarkedFormulas.contains(it.id) }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("bookmarks_notes_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column {
                Text(
                    text = "सहेजे गए संकलन (Saved & Notes)",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "आपकी व्यक्तिगत अध्ययन सामग्री एवं त्वरित पुनरावलोकन बैंक",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Tab Selector
        item {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("बुकमार्क (${savedTopicsList.size + savedFormulasList.size})") },
                    icon = { Icon(Icons.Default.Bookmark, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("मेरे नोट्स (${userNotes.size})") },
                    icon = { Icon(Icons.Default.EditNote, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
            }
        }

        if (selectedTab == 0) {
            // Bookmarks Tab
            item {
                Text(
                    text = "बुकमार्क किए गए टॉपिक (${savedTopicsList.size}):",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            if (savedTopicsList.isEmpty()) {
                item {
                    Text(
                        text = "अभी कोई टॉपिक बुकमार्क नहीं किया गया है। किसी भी टॉपिक के शीर्ष पर बुकमार्क आइकन दबाकर सहेजें।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                items(savedTopicsList) { topic ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToTopic(topic.id) },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Bookmark, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(topic.titleHindi, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                                Text(topic.titleEnglish, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            IconButton(onClick = {
                                val topicNote = PhysicsChapterData.getTopicNote(topic.id)
                                if (topicNote != null) {
                                    val file = PdfExporter.exportNoteToPdf(context, topicNote)
                                    if (file != null && file.exists()) {
                                        Toast.makeText(context, "PDF तैयार हो गया: ${file.name}", Toast.LENGTH_SHORT).show()
                                        PdfExporter.sharePdf(context, file, topicNote.titleHindi)
                                    } else {
                                        Toast.makeText(context, "PDF तैयार नहीं हो सका", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }) {
                                Icon(Icons.Default.PictureAsPdf, contentDescription = "Export to PDF", tint = DeepKesari)
                            }
                            IconButton(onClick = {
                                repository.toggleTopicBookmark(topic.id)
                                bookmarkedTopics = repository.getBookmarkedTopics()
                            }) {
                                Icon(Icons.Default.DeleteOutline, contentDescription = "Remove")
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "बुकमार्क किए गए सूत्र (${savedFormulasList.size}):",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            if (savedFormulasList.isEmpty()) {
                item {
                    Text(
                        text = "अभी कोई सूत्र बुकमार्क नहीं किया गया है। सूत्र बैंक से किसी भी सूत्र को बुकमार्क करें।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                items(savedFormulasList) { formula ->
                    FormulaCard(
                        formula = formula,
                        isBookmarked = true,
                        onBookmarkToggle = { fid ->
                            repository.toggleFormulaBookmark(fid)
                            bookmarkedFormulas = repository.getBookmarkedFormulas()
                        },
                        onNavigateToTopic = onNavigateToTopic
                    )
                }
            }
        } else {
            // My Notes Tab
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "मेरे व्यक्तिगत संस्मरण एवं नोट्स:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    FilledTonalButton(onClick = { showAddNoteDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("नया नोट")
                    }
                }
            }

            if (userNotes.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.EditNote, contentDescription = null, modifier = Modifier.size(36.dp), tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "कोई व्यक्तिगत नोट उपलब्ध नहीं है।",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "अध्ययन के दौरान अपने संदेह या मुख्य फॉर्मूले यहाँ लिखकर सुरक्षित रख सकते हैं।",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                items(userNotes) { note ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = note.topicTitle,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                IconButton(onClick = {
                                    repository.deleteUserNote(note.id)
                                    userNotes = repository.getUserNotes()
                                }) {
                                    Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", modifier = Modifier.size(20.dp))
                                }
                            }
                            Text(
                                text = note.content,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            val dateStr = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(note.timestamp))
                            Text(
                                text = dateStr,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Local Storage Explanatory Card (Requirement 12)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "स्थानीय सुरक्षा: आपके बुकमार्क और नोट्स आपके डिवाइस पर सुरक्षित रूप से स्थानीय रूप से सहेजे गए हैं और ऑफलाइन भी उपलब्ध रहते हैं।",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }

    if (showAddNoteDialog) {
        val topic = PhysicsChapterData.topics.find { it.id == selectedTopicForNote } ?: PhysicsChapterData.topics.first()
        AlertDialog(
            onDismissRequest = { showAddNoteDialog = false },
            title = { Text("नया व्यक्तिगत नोट (Add Note)") },
            text = {
                Column {
                    Text("संबंधित टॉपिक: ${topic.titleHindi}", style = MaterialTheme.typography.labelMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newNoteContent,
                        onValueChange = { newNoteContent = it },
                        placeholder = { Text("अपने विचार या महत्वपूर्ण बिंदु लिखें...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (newNoteContent.isNotBlank()) {
                        repository.saveUserNote(
                            topicId = selectedTopicForNote,
                            topicTitle = topic.titleHindi,
                            content = newNoteContent.trim()
                        )
                        userNotes = repository.getUserNotes()
                        newNoteContent = ""
                    }
                    showAddNoteDialog = false
                }) {
                    Text("सहेजें")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddNoteDialog = false }) {
                    Text("रद्द करें")
                }
            }
        )
    }
}
