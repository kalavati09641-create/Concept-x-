package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.QuizAttemptRecord
import com.example.ui.theme.*

/**
 * Modern Academic Tracking Dashboard Visualization Suite
 * Modeled after professional motion-graphics chart suites (Recharts visual styling adapted for native Jetpack Compose):
 * 1. Chapter Completion Radial Ring Progress with % breakdown
 * 2. Bar Chart of Quiz Performance & Accuracy
 * 3. Topic-by-Topic Mastery Level Grid
 * 4. Academic Stats Summary Cards
 */
@Composable
fun AcademicTrackingDashboardCard(
    completedTopicCount: Int,
    totalTopicCount: Int,
    quizScore: Int,
    quizAttempts: List<QuizAttemptRecord>,
    onPracticeClick: () -> Unit,
    onContinueChapterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedChartTab by remember { mutableIntStateOf(0) } // 0: Quiz Performance, 1: Chapter Progress, 2: Mastery breakdown

    val chapterProgressRatio = if (totalTopicCount > 0) completedTopicCount.toFloat() / totalTopicCount else 0f
    val chapterPercent = (chapterProgressRatio * 100).toInt()

    val totalAttemptCount = quizAttempts.size
    val averageScorePercent = if (quizAttempts.isNotEmpty()) {
        quizAttempts.map { it.percentage }.average().toInt()
    } else {
        if (quizScore > 0) 80 else 0
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("academic_tracking_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Header with badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Analytics,
                                contentDescription = "Academic Tracking",
                                tint = DeepKesari,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "शैक्षणिक प्रगति ट्रैकिंग (Academic Tracking)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "क्विज़ प्रदर्शन एवं अध्याय समापन विश्लेषण",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Surface(
                    color = SoftPeach,
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFD8A8))
                ) {
                    Text(
                        text = "कक्षा 12",
                        color = DeepKesari,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Recharts-style Interactive Navigation Segment Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val tabs = listOf(
                    "क्विज़ चार्ट" to Icons.Default.BarChart,
                    "अध्याय प्रगति" to Icons.Default.PieChart,
                    "टॉपिक स्तर" to Icons.Default.Leaderboard
                )

                tabs.forEachIndexed { index, (label, icon) ->
                    val isSelected = selectedChartTab == index
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedChartTab = index },
                        shape = RoundedCornerShape(9.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent,
                        shadowElevation = if (isSelected) 1.dp else 0.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = if (isSelected) DeepKesari else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) DeepKesari else MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Chart Content based on Selected Tab
            when (selectedChartTab) {
                0 -> {
                    // Recharts Bar Chart: Quiz Performance
                    QuizPerformanceBarChart(
                        attempts = quizAttempts,
                        totalScore = quizScore,
                        onPracticeClick = onPracticeClick
                    )
                }
                1 -> {
                    // Recharts Radial / Donut Ring: Chapter Completion Progress
                    ChapterCompletionRadialChart(
                        completedCount = completedTopicCount,
                        totalCount = totalTopicCount,
                        onContinueClick = onContinueChapterClick
                    )
                }
                2 -> {
                    // Topic Mastery Progression
                    TopicMasteryBreakdownView(
                        completedCount = completedTopicCount,
                        totalCount = totalTopicCount,
                        averageAccuracy = averageScorePercent
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom Metric KPI Tiles
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricKpiTile(
                    modifier = Modifier.weight(1f),
                    title = "अध्याय पूर्णता",
                    value = "$chapterPercent%",
                    subText = "$completedTopicCount/$totalTopicCount टॉपिक",
                    accentColor = PrimarySaffron,
                    icon = Icons.Default.CheckCircle
                )
                MetricKpiTile(
                    modifier = Modifier.weight(1f),
                    title = "औसत स्कोर",
                    value = if (quizAttempts.isNotEmpty() || quizScore > 0) "$averageScorePercent%" else "--",
                    subText = "${quizAttempts.size} टेस्ट दिए",
                    accentColor = DeepKesari,
                    icon = Icons.Default.Stars
                )
                MetricKpiTile(
                    modifier = Modifier.weight(1f),
                    title = "कुल अंक",
                    value = "${quizScore} Pts",
                    subText = "सटीक उत्तर",
                    accentColor = SuccessGreen,
                    icon = Icons.Default.EmojiEvents
                )
            }
        }
    }
}

/**
 * Recharts-inspired Animated Bar Chart for Quiz Scores & Accuracy
 */
@Composable
fun QuizPerformanceBarChart(
    attempts: List<QuizAttemptRecord>,
    totalScore: Int,
    onPracticeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Generate realistic data points: either from actual attempts, or syllabus baseline if fresh
    val displayData = remember(attempts) {
        if (attempts.isNotEmpty()) {
            attempts.takeLast(5).map { attempt ->
                val shortLabel = if (attempt.topicTitle.length > 8) attempt.topicTitle.take(7) + ".." else attempt.topicTitle
                ChartBarItem(
                    label = shortLabel,
                    fullTitle = attempt.topicTitle,
                    scorePercent = attempt.percentage,
                    isReal = true
                )
            }
        } else {
            // Default curriculum practice topics baseline to display Recharts bar chart
            listOf(
                ChartBarItem("विद्युत आवेश", "विद्युत आवेश (Electric Charge)", if (totalScore > 0) 80 else 0, false),
                ChartBarItem("कूलॉम नियम", "कूलॉम का नियम (Coulomb's Law)", 0, false),
                ChartBarItem("विद्युत क्षेत्र", "विद्युत क्षेत्र रेखाएं (Electric Field)", 0, false),
                ChartBarItem("गाउस प्रमेय", "गाउस का प्रमेय (Gauss Theorem)", 0, false),
                ChartBarItem("द्विध्रुव आघूर्ण", "विद्युत द्विध्रुव (Dipole Moment)", 0, false)
            )
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "क्विज़ सटीकता दर (Quiz Accuracy %)",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "अंतिम अभ्यास सत्रों का प्रदर्शन",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Legend
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(DeepKesari)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "सटीकता %", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Bar Chart Canvas Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            val barCount = displayData.size
            val gridColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
            val barBrush = Brush.verticalGradient(
                colors = listOf(PrimarySaffron, DeepKesari)
            )
            val baselineColor = MaterialTheme.colorScheme.surfaceVariant

            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height - 24.dp.toPx() // Reserve space for bottom labels
                val baselineY = canvasHeight

                // Draw horizontal guide lines (0%, 50%, 100%)
                val guideLines = listOf(1.0f, 0.5f, 0.0f)
                guideLines.forEach { ratio ->
                    val yPos = canvasHeight * (1f - ratio)
                    drawLine(
                        color = gridColor,
                        start = Offset(0f, yPos),
                        end = Offset(canvasWidth, yPos),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                // Draw bars
                val barSlotWidth = canvasWidth / barCount
                val barWidth = (barSlotWidth * 0.48f).coerceAtMost(32.dp.toPx())

                displayData.forEachIndexed { index, item ->
                    val centerX = (index * barSlotWidth) + (barSlotWidth / 2f)
                    val barLeft = centerX - (barWidth / 2f)

                    // Height calculation
                    val heightRatio = (item.scorePercent / 100f).coerceIn(0f, 1f)
                    val barHeight = canvasHeight * heightRatio

                    // Background unfilled track (light subtle bar)
                    drawRoundRect(
                        color = baselineColor,
                        topLeft = Offset(barLeft, 0f),
                        size = Size(barWidth, canvasHeight),
                        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                    )

                    // Active filled bar with gradient
                    if (barHeight > 2.dp.toPx()) {
                        drawRoundRect(
                            brush = barBrush,
                            topLeft = Offset(barLeft, canvasHeight - barHeight),
                            size = Size(barWidth, barHeight),
                            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                        )
                    }
                }
            }

            // Overlay Labels at the bottom
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                displayData.forEach { item ->
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (item.scorePercent > 0) "${item.scorePercent}%" else item.label,
                            fontSize = 9.sp,
                            fontWeight = if (item.scorePercent > 0) FontWeight.Bold else FontWeight.Medium,
                            color = if (item.scorePercent > 0) DeepKesari else MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // CTA for practicing more quizzes to update chart
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (attempts.isEmpty()) "क्विज़ देकर अपना स्कोर चार्ट बनाएं" else "नया टेस्ट देकर सटीकता बढ़ाएं",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            TextButton(
                onClick = onPracticeClick,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "टेस्ट शुरू करें →",
                    fontWeight = FontWeight.Bold,
                    color = DeepKesari,
                    fontSize = 12.sp
                )
            }
        }
    }
}

data class ChartBarItem(
    val label: String,
    val fullTitle: String,
    val scorePercent: Int,
    val isReal: Boolean
)

/**
 * Recharts Radial / Donut Ring Chart: Visualizing Chapter Topics Completion
 */
@Composable
fun ChapterCompletionRadialChart(
    completedCount: Int,
    totalCount: Int,
    onContinueClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f
    val percent = (progress * 100).toInt()
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(progress) {
        animatedProgress.animateTo(
            targetValue = progress,
            animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left: Animated Donut / Radial Ring
        Box(
            modifier = Modifier.size(110.dp),
            contentAlignment = Alignment.Center
        ) {
            val trackColor = MaterialTheme.colorScheme.surfaceVariant
            val primaryColor = DeepKesari
            val secondaryColor = PrimarySaffron

            Canvas(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                val strokeWidth = 10.dp.toPx()
                val radius = (size.minDimension - strokeWidth) / 2f
                val centerOffset = Offset(size.width / 2f, size.height / 2f)

                // Background track circle
                drawCircle(
                    color = trackColor,
                    radius = radius,
                    center = centerOffset,
                    style = Stroke(width = strokeWidth)
                )

                // Foreground sweep arc
                val sweepAngle = 360f * animatedProgress.value
                if (sweepAngle > 0f) {
                    drawArc(
                        brush = Brush.sweepGradient(
                            listOf(secondaryColor, primaryColor, secondaryColor)
                        ),
                        startAngle = -90f,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        topLeft = Offset(centerOffset.x - radius, centerOffset.y - radius),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                }
            }

            // Center Text
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$percent%",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = DeepKesari
                )
                Text(
                    text = "पूर्ण",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Right Breakdown Stats
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "अध्याय 1: विद्युत आवेश तथा क्षेत्र",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Topic progress pills
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(SuccessGreen)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "समाप्त टॉपिक: $completedCount",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.outline)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "शेष टॉपिक: ${totalCount - completedCount}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            FilledTonalButton(
                onClick = onContinueClick,
                modifier = Modifier.fillMaxWidth().height(36.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = DeepKesari
                ),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text("अध्ययन जारी रखें", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.ChevronRight, contentDescription = null, modifier = Modifier.size(14.dp))
            }
        }
    }
}

/**
 * Topic Mastery Level Grid showing detailed syllabus milestones
 */
@Composable
fun TopicMasteryBreakdownView(
    completedCount: Int,
    totalCount: Int,
    averageAccuracy: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "टॉपिक निपुणता स्तर (Topic Mastery Levels)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Surface(
                color = if (completedCount > 2) SuccessGreen.copy(alpha = 0.15f) else SoftPeach,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = if (completedCount >= 4) "उन्नत स्तर (Advanced)" else if (completedCount >= 1) "मध्यम (Intermediate)" else "प्रारंभिक (Beginner)",
                    color = if (completedCount >= 4) SuccessGreen else DeepKesari,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        val syllabusTopics = listOf(
            "विद्युत आवेश एवं इसके मूल गुण",
            "कूलॉम का नियम (Coulomb's Law)",
            "विद्युत क्षेत्र एवं क्षेत्र रेखाएं",
            "विद्युत फ्लक्स एवं गाउस प्रमेय",
            "विद्युत द्विध्रुव तथा द्विध्रुव आघूर्ण"
        )

        syllabusTopics.forEachIndexed { index, title ->
            val isCompleted = index < completedCount
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                        contentDescription = null,
                        tint = if (isCompleted) SuccessGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = title,
                        fontSize = 12.sp,
                        color = if (isCompleted) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = if (isCompleted) FontWeight.SemiBold else FontWeight.Normal,
                        maxLines = 1
                    )
                }

                Surface(
                    color = if (isCompleted) SuccessGreen.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = if (isCompleted) "सत्यापित" else "अपूर्ण",
                        fontSize = 10.sp,
                        color = if (isCompleted) SuccessGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

/**
 * Metric KPI Tile for Dashboard Overview
 */
@Composable
fun MetricKpiTile(
    title: String,
    value: String,
    subText: String,
    accentColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    fontSize = 10.sp
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(14.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )
            Text(
                text = subText,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 9.sp,
                maxLines = 1
            )
        }
    }
}
