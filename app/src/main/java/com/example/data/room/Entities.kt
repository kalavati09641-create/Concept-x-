package com.example.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "physics_notes")
data class PhysicsNoteEntity(
    @PrimaryKey val topicId: String,
    val chapterId: String,
    val number: Int,
    val titleHindi: String,
    val titleEnglish: String,
    val conceptSimpleHindi: String,
    val realLifeAnalogy: String,
    val stepByStepExplanationJson: String,
    val importantDefinition: String,
    val siUnitsAndDimensions: String,
    val diagramId: String?,
    val diagramCaption: String?,
    val solvedProblemHindi: String,
    val solvedProblemEnglish: String?,
    val solvedGivenValuesJson: String,
    val solvedFormulaUsed: String,
    val solvedStepsJson: String,
    val solvedFinalAnswer: String,
    val commonMistakesJson: String,
    val mpBoardExamFormat: String,
    val quickRevisionPointsJson: String,
    val practiceQuestionsJson: String,
    val jeeEnrichment: String?,
    val isBookmarked: Boolean = false,
    val isCompleted: Boolean = false,
    val lastVisitedTimestamp: Long = 0L
)

@Entity(tableName = "formulas")
data class FormulaEntity(
    @PrimaryKey val formulaId: String,
    val topicId: String,
    val topicName: String,
    val nameHindi: String,
    val nameEnglish: String,
    val mathExpression: String,
    val symbolsMeaningJson: String,
    val siUnit: String,
    val conditions: String,
    val workedExample: String?,
    val isBookmarked: Boolean = false
)

@Entity(tableName = "student_notes")
data class StudentNoteEntity(
    @PrimaryKey val id: String,
    val topicId: String,
    val topicTitle: String,
    val content: String,
    val timestamp: Long
)
