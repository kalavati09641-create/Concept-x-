package com.example.model

data class QuizAttemptRecord(
    val id: String,
    val topicTitle: String,
    val correctCount: Int,
    val totalQuestions: Int,
    val percentage: Int,
    val timestamp: Long
)
