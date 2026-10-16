package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.data.LocalRepository
import com.example.model.Screen
import com.example.ui.components.ConceptXBottomBar
import com.example.ui.components.ConceptXTopBar
import com.example.ui.components.PublishWebsiteDialog
import com.example.ui.screens.*
import com.example.ui.theme.ConceptXTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = LocalRepository(this)

        setContent {
            var isDarkMode by remember { mutableStateOf(false) } // Default to warm white & saffron theme
            var currentScreen by remember { mutableStateOf(Screen.HOME) }
            val screenBackStack = remember { mutableStateListOf<Screen>() }

            var activeTopicId by remember { mutableStateOf("top_1") }
            var aiInitialTopicPrompt by remember { mutableStateOf<String?>(null) }
            var quizTopicId by remember { mutableStateOf<String?>(null) }
            var showPublishDialog by remember { mutableStateOf(false) }

            fun navigateTo(screen: Screen) {
                if (currentScreen != screen) {
                    screenBackStack.add(currentScreen)
                    currentScreen = screen
                }
            }

            fun navigateBack() {
                if (screenBackStack.isNotEmpty()) {
                    currentScreen = screenBackStack.removeAt(screenBackStack.size - 1)
                } else if (currentScreen != Screen.HOME) {
                    currentScreen = Screen.HOME
                }
            }

            BackHandler(enabled = currentScreen != Screen.HOME || screenBackStack.isNotEmpty()) {
                navigateBack()
            }

            ConceptXTheme(darkTheme = isDarkMode) {
                Scaffold(
                    topBar = {
                        if (currentScreen != Screen.TOPIC_DETAIL) {
                            ConceptXTopBar(
                                title = "ConceptX",
                                subtitle = "Class 12 MP Board (MPBSE)",
                                isDarkMode = isDarkMode,
                                onToggleTheme = { isDarkMode = !isDarkMode },
                                onSearchClick = {
                                    navigateTo(Screen.HOME)
                                },
                                onPublishClick = {
                                    showPublishDialog = true
                                }
                            )
                        }
                    },
                    bottomBar = {
                        if (currentScreen != Screen.TOPIC_DETAIL) {
                            ConceptXBottomBar(
                                currentScreen = currentScreen,
                                onNavigate = { screen ->
                                    if (currentScreen != screen) {
                                        screenBackStack.add(currentScreen)
                                        currentScreen = screen
                                    }
                                }
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    val screenModifier = Modifier.padding(innerPadding)

                    when (currentScreen) {
                        Screen.HOME -> {
                            HomeScreen(
                                repository = repository,
                                onNavigateToTopic = { topicId ->
                                    activeTopicId = topicId
                                    navigateTo(Screen.TOPIC_DETAIL)
                                },
                                onNavigateToScreen = { screen ->
                                    navigateTo(screen)
                                },
                                onOpenPublishDialog = {
                                    showPublishDialog = true
                                },
                                modifier = screenModifier
                            )
                        }

                        Screen.SUBJECTS -> {
                            SubjectsScreen(
                                repository = repository,
                                onNavigateToTopic = { topicId ->
                                    activeTopicId = topicId
                                    navigateTo(Screen.TOPIC_DETAIL)
                                },
                                modifier = screenModifier
                            )
                        }

                        Screen.TOPIC_DETAIL -> {
                            TopicDetailScreen(
                                topicId = activeTopicId,
                                repository = repository,
                                onBack = { navigateBack() },
                                onAskAiAboutTopic = { topicTitle ->
                                    aiInitialTopicPrompt = topicTitle
                                    navigateTo(Screen.AI_TEACHER)
                                },
                                onStartPracticeQuiz = { topicId ->
                                    quizTopicId = topicId
                                    navigateTo(Screen.PRACTICE)
                                },
                                modifier = Modifier // TopicDetailScreen manages its own Scaffold and insets
                            )
                        }

                        Screen.FORMULA_BANK -> {
                            FormulaBankScreen(
                                repository = repository,
                                onNavigateToTopic = { topicId ->
                                    activeTopicId = topicId
                                    navigateTo(Screen.TOPIC_DETAIL)
                                },
                                modifier = screenModifier
                            )
                        }

                        Screen.AI_TEACHER -> {
                            AiTeacherScreen(
                                initialTopicPrompt = aiInitialTopicPrompt,
                                modifier = screenModifier
                            )
                        }

                        Screen.PRACTICE -> {
                            PracticeScreen(
                                repository = repository,
                                initialTopicId = quizTopicId,
                                modifier = screenModifier
                            )
                        }

                        Screen.BOOKMARKS_NOTES -> {
                            BookmarksNotesScreen(
                                repository = repository,
                                onNavigateToTopic = { topicId ->
                                    activeTopicId = topicId
                                    navigateTo(Screen.TOPIC_DETAIL)
                                },
                                modifier = screenModifier
                            )
                        }
                    }
                }

                if (showPublishDialog) {
                    PublishWebsiteDialog(
                        onDismiss = { showPublishDialog = false }
                    )
                }
            }
        }
    }
}
