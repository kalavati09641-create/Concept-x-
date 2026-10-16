package com.example.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Screen
import com.example.ui.theme.BoardBadgeBg
import com.example.ui.theme.BoardBadgeText
import com.example.ui.theme.DeepKesari
import com.example.ui.theme.PrimarySaffron
import com.example.ui.theme.SoftPeach
import com.example.ui.theme.SubtleBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConceptXTopBar(
    title: String = "ConceptX",
    subtitle: String? = "कक्षा 12 • MP Board (MPBSE)",
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit,
    onSearchClick: () -> Unit,
    onPublishClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val borderColor = MaterialTheme.colorScheme.outline
    TopAppBar(
        title = {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "MPBSE 12th",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                        )
                    }
                }
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        actions = {
            FilledTonalButton(
                onClick = onPublishClick,
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .padding(end = 4.dp)
                    .testTag("top_publish_button")
            ) {
                Icon(
                    imageVector = Icons.Default.RocketLaunch,
                    contentDescription = "पब्लिश करें",
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "पब्लिश",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(
                onClick = onSearchClick,
                modifier = Modifier.testTag("top_search_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Concepts",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            IconButton(
                onClick = onToggleTheme,
                modifier = Modifier.testTag("top_theme_toggle")
            ) {
                Icon(
                    imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = "Toggle Theme",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier.drawBehind {
            // Subtle bottom divider
            drawLine(
                color = borderColor.copy(alpha = 0.5f),
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = 1.dp.toPx()
            )
        }
    )
}

@Composable
fun ConceptXBottomBar(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = MaterialTheme.colorScheme.outline
    NavigationBar(
        modifier = modifier
            .testTag("bottom_nav_bar")
            .drawBehind {
                // Subtle top border line
                drawLine(
                    color = borderColor.copy(alpha = 0.6f),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            },
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        val items = listOf(
            Triple(Screen.HOME, "होम", Icons.Default.Home),
            Triple(Screen.SUBJECTS, "विषय", Icons.AutoMirrored.Filled.LibraryBooks),
            Triple(Screen.FORMULA_BANK, "सूत्र बैंक", Icons.Default.Functions),
            Triple(Screen.AI_TEACHER, "AI गुरु", Icons.Default.SmartToy),
            Triple(Screen.PRACTICE, "अभ्यास", Icons.Default.Quiz),
            Triple(Screen.BOOKMARKS_NOTES, "नोट्स", Icons.Default.Bookmarks)
        )

        items.forEach { (screen, label, icon) ->
            val isSelected = currentScreen == screen
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(screen) },
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = {
                    Text(
                        text = label,
                        fontSize = 10.sp,
                        maxLines = 1,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.testTag("nav_item_${screen.name.lowercase()}")
            )
        }
    }
}
