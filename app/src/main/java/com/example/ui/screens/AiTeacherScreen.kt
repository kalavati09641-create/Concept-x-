package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PhysicsChapterData
import com.example.model.ChatMessage
import com.example.model.MessageSender
import com.example.network.GeminiTeacherService
import com.example.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiTeacherScreen(
    initialTopicPrompt: String? = null,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val teacherService = remember { GeminiTeacherService() }
    val listState = rememberLazyListState()

    var selectedTopicId by remember { mutableStateOf("top_4") } // Default to Coulomb's Law
    var inputText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                id = "welcome",
                sender = MessageSender.AI_TEACHER,
                text = "नमस्ते! मैं ConceptX AI शिक्षक हूँ। कक्षा 12वीं एमपी बोर्ड भौतिकी के किसी भी संप्रत्यय, परिभाषा, सूत्र या आंकिक प्रश्न के बारे में आप हिंदी या अंग्रेजी में पूछ सकते हैं।\n\nआप किसी कठिन प्रश्न का हिंट मांग सकते हैं या सरल भाषा में समझाने का अनुरोध कर सकते हैं।"
            )
        )
    }

    LaunchedEffect(initialTopicPrompt) {
        if (!initialTopicPrompt.isNullOrBlank()) {
            val prompt = "$initialTopicPrompt को सरल हिंदी में समझाएं।"
            inputText = prompt
        }
    }

    val selectedTopic = remember(selectedTopicId) {
        PhysicsChapterData.topics.find { it.id == selectedTopicId }
    }

    val quickActionPrompts = listOf(
        "सरल भाषा में समझाइए",
        "हिंट दीजिए (Hint first)",
        "संख्यात्मक हल (Step-by-step)",
        "अंतर स्पष्ट कीजिए",
        "सूत्र का भौतिक अर्थ बताइए"
    )

    fun sendMessage(query: String) {
        if (query.isBlank() || isLoading) return
        val userMsg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            sender = MessageSender.USER,
            text = query.trim()
        )
        messages.add(userMsg)
        inputText = ""
        isLoading = true

        coroutineScope.launch {
            listState.animateScrollToItem(messages.size - 1)

            val result = teacherService.askTutor(
                userMessage = userMsg.text,
                subjectName = "भौतिक विज्ञान (Physics)",
                chapterName = "विद्युत आवेश तथा क्षेत्र (अध्याय 1)",
                topicName = selectedTopic?.titleHindi,
                conversationHistory = messages.toList()
            )

            isLoading = false
            result.onSuccess { reply ->
                messages.add(
                    ChatMessage(
                        id = "ai_${System.currentTimeMillis()}",
                        sender = MessageSender.AI_TEACHER,
                        text = reply
                    )
                )
            }.onFailure { err ->
                messages.add(
                    ChatMessage(
                        id = "ai_err_${System.currentTimeMillis()}",
                        sender = MessageSender.AI_TEACHER,
                        text = "त्रुटि: ${err.message ?: "नेटवर्क समस्या। कृपया पुनः प्रयास करें।"}",
                        isError = true
                    )
                )
            }
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    val borderColor = MaterialTheme.colorScheme.outline

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("ai_teacher_screen")
    ) {
        // Topic Selector Bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawLine(
                        color = borderColor.copy(alpha = 0.5f),
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.size(24.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.SmartToy,
                                    contentDescription = null,
                                    tint = DeepKesari,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "सक्रिय विषय संदर्भ:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(6.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, DeepKesari.copy(alpha = 0.3f))
                    ) {
                        Text(
                            text = "Gemini 3.5 Live",
                            color = DeepKesari,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(PhysicsChapterData.topics) { topic ->
                        val isSelected = topic.id == selectedTopicId
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedTopicId = topic.id },
                            label = {
                                Text(
                                    topic.titleHindi,
                                    fontSize = 11.sp,
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
        }

        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(messages) { message ->
                ChatMessageItem(message = message)
            }

            if (isLoading) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = DeepKesari
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "ConceptX शिक्षक विचार कर रहे हैं...",
                            style = MaterialTheme.typography.bodySmall,
                            color = DeepKesari,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Quick Suggestion Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .drawBehind {
                    drawLine(
                        color = borderColor.copy(alpha = 0.5f),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(quickActionPrompts) { promptText ->
                SuggestionChip(
                    onClick = {
                        val fullPrompt = "${selectedTopic?.titleHindi ?: ""}: $promptText"
                        sendMessage(fullPrompt)
                    },
                    label = {
                        Text(
                            promptText,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    border = SuggestionChipDefaults.suggestionChipBorder(
                        enabled = true,
                        borderColor = MaterialTheme.colorScheme.outline
                    ),
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        }

        // Message Input Row
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 14.dp, vertical = 10.dp)
                    .navigationBarsPadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = {
                        Text(
                            "संदेह या प्रश्न पूछें (उदा: E और V में क्या अंतर है?)",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("ai_message_input"),
                    shape = RoundedCornerShape(20.dp),
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DeepKesari,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )

                Spacer(modifier = Modifier.width(10.dp))

                FilledIconButton(
                    onClick = { sendMessage(inputText) },
                    enabled = inputText.isNotBlank() && !isLoading,
                    modifier = Modifier.testTag("ai_send_button"),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = DeepKesari,
                        contentColor = Color.White,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ChatMessageItem(message: ChatMessage) {
    val isUser = message.sender == MessageSender.USER
    val isDark = MaterialTheme.colorScheme.background == DarkInkBackground

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 3.dp)
        ) {
            if (!isUser) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.size(16.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.SmartToy,
                            contentDescription = null,
                            tint = DeepKesari,
                            modifier = Modifier.size(11.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "ConceptX शिक्षक",
                    style = MaterialTheme.typography.labelSmall,
                    color = DeepKesari,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "आप (Student)",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        val containerColor = when {
            message.isError -> Color(0xFFFEE2E2)
            isUser -> DeepKesari
            else -> if (isDark) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surface
        }

        val contentColor = when {
            message.isError -> Color(0xFF991B1B)
            isUser -> Color.White
            else -> MaterialTheme.colorScheme.onSurface
        }

        Surface(
            color = containerColor,
            contentColor = contentColor,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isUser) 16.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 16.dp
            ),
            border = if (!isUser && !message.isError) androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline) else null,
            modifier = Modifier.widthIn(max = 320.dp),
            shadowElevation = if (isUser) 1.dp else 0.5.dp
        ) {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                lineHeight = 22.sp
            )
        }
    }
}
