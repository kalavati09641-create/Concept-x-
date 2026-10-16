package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import com.example.data.LocalRepository
import com.example.data.PhysicsChapterData
import com.example.model.TopicNote
import com.example.ui.components.FormulaCard
import com.example.ui.components.PhysicsDiagramView
import com.example.ui.theme.*
import com.example.util.PdfExporter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicDetailScreen(
    topicId: String,
    repository: LocalRepository,
    onBack: () -> Unit,
    onAskAiAboutTopic: (String) -> Unit,
    onStartPracticeQuiz: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    BackHandler { onBack() }

    val note = remember(topicId) {
        PhysicsChapterData.getTopicNote(topicId)
    }

    var isBookmarked by remember { mutableStateOf(repository.isTopicBookmarked(topicId)) }
    var isCompleted by remember { mutableStateOf(repository.isTopicCompleted(topicId)) }
    var showNoteDialog by remember { mutableStateOf(false) }
    var studentNoteText by remember { mutableStateOf("") }
    var isExportingPdf by remember { mutableStateOf(false) }
    val bookmarkedFormulas = remember { mutableStateOf(repository.getBookmarkedFormulas()) }

    fun handleExportPdf() {
        if (note == null) return
        isExportingPdf = true
        try {
            val file = PdfExporter.exportNoteToPdf(context, note)
            if (file != null && file.exists()) {
                Toast.makeText(context, "PDF तैयार हो गया: ${file.name}", Toast.LENGTH_SHORT).show()
                PdfExporter.sharePdf(context, file, note.titleHindi)
            } else {
                Toast.makeText(context, "PDF निर्यात में त्रुटि हुई", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "त्रुटि: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        } finally {
            isExportingPdf = false
        }
    }

    LaunchedEffect(topicId) {
        repository.addRecentTopic(topicId)
    }

    if (note == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("विषय विवरण उपलब्ध नहीं है।", color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(containerColor = DeepKesari)
                ) {
                    Text("वापस जाएं")
                }
            }
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = note.titleHindi,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1
                        )
                        Text(
                            text = "विद्युत आवेश तथा क्षेत्र • MPBSE 12th",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("topic_detail_back_btn")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { handleExportPdf() },
                        enabled = !isExportingPdf,
                        modifier = Modifier.testTag("topic_export_pdf_btn")
                    ) {
                        if (isExportingPdf) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = DeepKesari
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.PictureAsPdf,
                                contentDescription = "Export to PDF",
                                tint = DeepKesari
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            isBookmarked = repository.toggleTopicBookmark(topicId)
                        },
                        modifier = Modifier.testTag("topic_detail_bookmark_btn")
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) DeepKesari else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(
                        onClick = { showNoteDialog = true },
                        modifier = Modifier.testTag("topic_add_note_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = "Add Personal Note",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("topic_detail_scroll"),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Tags & Completion Toggle Bar
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "एमपी बोर्ड पाठ्यक्रम",
                                color = DeepKesari,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(6.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                        ) {
                            Text(
                                text = "Class 12 Physics",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    FilterChip(
                        selected = isCompleted,
                        onClick = {
                            isCompleted = repository.toggleTopicCompletion(topicId)
                        },
                        label = {
                            Text(
                                text = if (isCompleted) "समझ लिया ✓" else "अध्ययन बाकी",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        },
                        leadingIcon = {
                            if (isCompleted) {
                                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                            }
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SuccessGreen.copy(alpha = 0.12f),
                            selectedLabelColor = SuccessGreen,
                            selectedLeadingIconColor = SuccessGreen,
                            containerColor = MaterialTheme.colorScheme.surface,
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isCompleted,
                            borderColor = if (isCompleted) SuccessGreen else MaterialTheme.colorScheme.outline
                        )
                    )
                }
            }

            // Quick Offline PDF Export Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("topic_export_pdf_banner"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.PictureAsPdf,
                                        contentDescription = null,
                                        tint = DeepKesari,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "ऑफलाइन संदर्भ (Offline PDF)",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "परीक्षा तैयारी हेतु संपूर्ण नोट्स PDF डाउनलोड करें",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Button(
                            onClick = { handleExportPdf() },
                            enabled = !isExportingPdf,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DeepKesari,
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            if (isExportingPdf) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp,
                                    color = Color.White
                                )
                            } else {
                                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("PDF Export", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // A. Concept in Simple Hindi
            item {
                SectionCard(
                    title = "A. सरल हिंदी में संकल्पना (Intuitive Concept)",
                    icon = Icons.Default.Lightbulb,
                    accentColor = DeepKesari
                ) {
                    Text(
                        text = note.conceptSimpleHindi,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 26.sp
                    )
                }
            }

            // B. Real-Life Analogy
            item {
                SectionCard(
                    title = "B. दैनिक जीवन का उदाहरण (Real-Life Analogy)",
                    icon = Icons.Default.Explore,
                    accentColor = PrimarySaffron
                ) {
                    Text(
                        text = note.realLifeAnalogy,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 24.sp
                    )
                }
            }

            // G. Diagram / Visual Area
            if (note.diagramId != null) {
                item {
                    SectionCard(
                        title = "G. आरेख एवं दृश्य व्याख्या (Visual Diagram)",
                        icon = Icons.Default.Image,
                        accentColor = DeepKesari
                    ) {
                        PhysicsDiagramView(
                            diagramId = note.diagramId,
                            caption = note.diagramCaption ?: "संकल्पना आरेख"
                        )
                    }
                }
            }

            // C. Step-by-Step Explanation
            item {
                SectionCard(
                    title = "C. चरण-दर-चरण व्याख्या (Step-by-Step)",
                    icon = Icons.Default.FormatListNumbered,
                    accentColor = DeepKesari
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        note.stepByStepExplanation.forEach { step ->
                            Text(
                                text = step,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 24.sp
                            )
                        }
                    }
                }
            }

            // D. Important Definition (MP Board Standard)
            item {
                SectionCard(
                    title = "D. महत्वपूर्ण परिभाषा (Standard Definition)",
                    icon = Icons.Default.School,
                    accentColor = DeepKesari
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, DeepKesari.copy(alpha = 0.35f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = note.importantDefinition,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(16.dp),
                            lineHeight = 26.sp
                        )
                    }
                }
            }

            // E. Formulas and Symbols Meaning
            if (note.formulas.isNotEmpty()) {
                item {
                    SectionCard(
                        title = "E. सूत्र एवं प्रतीकों का अर्थ (Formula & Symbols)",
                        icon = Icons.Default.Functions,
                        accentColor = DeepKesari
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            note.formulas.forEach { formula ->
                                FormulaCard(
                                    formula = formula,
                                    isBookmarked = bookmarkedFormulas.value.contains(formula.id),
                                    onBookmarkToggle = { fid ->
                                        repository.toggleFormulaBookmark(fid)
                                        bookmarkedFormulas.value = repository.getBookmarkedFormulas()
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // F. SI Units and Dimensions
            item {
                SectionCard(
                    title = "F. SI मात्रक एवं विमीय सूत्र (Units & Dimensions)",
                    icon = Icons.Default.SquareFoot,
                    accentColor = PrimarySaffron
                ) {
                    Text(
                        text = note.siUnitsAndDimensions,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 24.sp
                    )
                }
            }

            // H. Solved Numerical Example
            item {
                SectionCard(
                    title = "H. हल सहित आंकिक प्रश्न (Solved Numerical)",
                    icon = Icons.Default.Calculate,
                    accentColor = DeepKesari
                ) {
                    val num = note.solvedNumerical
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "प्रश्न: ${num.problemStatementHindi}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "दत्त मान (Given):",
                            style = MaterialTheme.typography.labelMedium,
                            color = DeepKesari,
                            fontWeight = FontWeight.Bold
                        )
                        num.givenValues.forEach { g ->
                            Text(
                                text = "• $g",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Text(
                            text = "प्रयुक्त सूत्र: ${num.formulaUsed}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = DeepKesari
                        )

                        Text(
                            text = "हल के चरण:",
                            style = MaterialTheme.typography.labelMedium,
                            color = DeepKesari,
                            fontWeight = FontWeight.Bold
                        )
                        num.stepByStepSolution.forEach { step ->
                            Text(
                                text = step,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp
                            )
                        }

                        Surface(
                            color = SuccessGreen.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreen.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "अंतिम उत्तर: ${num.finalAnswer}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = SuccessGreen,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            }

            // I. Common Mistakes
            item {
                SectionCard(
                    title = "I. सामान्य गलतियाँ (Common Mistakes to Avoid)",
                    icon = Icons.Default.Warning,
                    accentColor = Color(0xFFDC2626)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        note.commonMistakes.forEach { mistake ->
                            Row(verticalAlignment = Alignment.Top) {
                                Text("⚠️ ", fontSize = 14.sp)
                                Text(
                                    text = mistake,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 22.sp
                                )
                            }
                        }
                    }
                }
            }

            // J. MP Board Exam Answer Format
            item {
                SectionCard(
                    title = "J. बोर्ड परीक्षा उत्तर लेखन प्रारूप (MP Board Marking)",
                    icon = Icons.Default.AssignmentTurnedIn,
                    accentColor = DeepKesari
                ) {
                    Text(
                        text = note.mpBoardExamFormat,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 24.sp
                    )
                }
            }

            // K. Quick Revision
            item {
                SectionCard(
                    title = "K. त्वरित दोहराव (Quick Revision)",
                    icon = Icons.Default.ElectricBolt,
                    accentColor = PrimarySaffron
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        note.quickRevisionPoints.forEach { point ->
                            Row(verticalAlignment = Alignment.Top) {
                                Text("✓ ", color = DeepKesari, fontWeight = FontWeight.Bold)
                                Text(
                                    text = point,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 22.sp
                                )
                            }
                        }
                    }
                }
            }

            // L. Practice Questions
            item {
                SectionCard(
                    title = "L. अभ्यास प्रश्न (Practice Questions)",
                    icon = Icons.Default.Quiz,
                    accentColor = DeepKesari
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        note.practiceQuestions.forEach { q ->
                            Text(
                                text = q,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 22.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Button(
                            onClick = { onStartPracticeQuiz(topicId) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = DeepKesari),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("इस टॉपिक का क्विज़ टेस्ट दें", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // JEE Enrichment (Clearly separated)
            if (note.jeeEnrichment != null) {
                item {
                    SectionCard(
                        title = "JEE संवर्धन (Optional JEE Enrichment)",
                        icon = Icons.Default.Stars,
                        accentColor = Color(0xFF7C3AED)
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "नोट: यह अंश बोर्ड परीक्षा के उपरांत JEE/NEET की तैयारी के लिए वैकल्पिक है।",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = DeepKesari,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = note.jeeEnrichment,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 22.sp
                                )
                            }
                        }
                    }
                }
            }

            // Floating Ask AI Teacher Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DeepKesari.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.SmartToy,
                                        contentDescription = null,
                                        tint = DeepKesari,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "इस टॉपिक में कोई संदेह है?",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "ConceptX AI गुरु से सरल हिंदी में सवाल पूछें, हिंट लें या स्टेप-बाय-स्टेप हल करवाएं।",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = { onAskAiAboutTopic(note.titleHindi) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = DeepKesari),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("AI शिक्षक से पूछें (Ask AI Tutor)", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // Add Note Dialog
    if (showNoteDialog) {
        AlertDialog(
            onDismissRequest = { showNoteDialog = false },
            title = { Text("व्यक्तिगत नोट्स जोड़ें (My Notes)", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(
                        text = "टॉपिक: ${note.titleHindi}",
                        style = MaterialTheme.typography.labelMedium,
                        color = DeepKesari,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = studentNoteText,
                        onValueChange = { studentNoteText = it },
                        placeholder = { Text("अपना व्यक्तिगत संस्मरण, संदेह या महत्वपूर्ण बिंदु यहाँ लिखें...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        maxLines = 5,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DeepKesari,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (studentNoteText.isNotBlank()) {
                            repository.saveUserNote(
                                topicId = topicId,
                                topicTitle = note.titleHindi,
                                content = studentNoteText.trim()
                            )
                            studentNoteText = ""
                        }
                        showNoteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepKesari)
                ) {
                    Text("सहेजें (Save)")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNoteDialog = false }) {
                    Text("रद्द करें", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        )
    }
}

@Composable
fun SectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    content: @Composable () -> Unit
) {
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
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 14.dp)
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            content()
        }
    }
}
