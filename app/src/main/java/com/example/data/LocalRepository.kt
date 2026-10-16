package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.data.room.*
import com.example.model.UserNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class LocalRepository(context: Context) {
    private val appContext = context.applicationContext
    private val prefs: SharedPreferences =
        appContext.getSharedPreferences("conceptx_mp_prefs", Context.MODE_PRIVATE)

    val roomDatabase: ConceptXDatabase = ConceptXDatabase.getInstance(appContext)
    val physicsNoteDao: PhysicsNoteDao = roomDatabase.physicsNoteDao()
    val formulaDao: FormulaDao = roomDatabase.formulaDao()
    val studentNoteDao: StudentNoteDao = roomDatabase.studentNoteDao()

    private val repositoryScope = CoroutineScope(Dispatchers.IO)

    init {
        // Asynchronously populate Room with core Class 12 Physics syllabus content if empty
        repositoryScope.launch {
            seedRoomDatabaseIfEmpty()
        }
    }

    private suspend fun seedRoomDatabaseIfEmpty() {
        if (physicsNoteDao.countNotes() == 0) {
            val bookmarkedTopicIds = getBookmarkedTopics()
            val completedTopicIds = getCompletedTopics()

            val entities = PhysicsChapterData.topics.map { topic ->
                val note = PhysicsChapterData.getTopicNote(topic.id)
                PhysicsNoteEntity(
                    topicId = topic.id,
                    chapterId = topic.chapterId,
                    number = topic.number,
                    titleHindi = topic.titleHindi,
                    titleEnglish = topic.titleEnglish,
                    conceptSimpleHindi = note?.conceptSimpleHindi ?: "",
                    realLifeAnalogy = note?.realLifeAnalogy ?: "",
                    stepByStepExplanationJson = JSONArray(note?.stepByStepExplanation ?: emptyList<String>()).toString(),
                    importantDefinition = note?.importantDefinition ?: "",
                    siUnitsAndDimensions = note?.siUnitsAndDimensions ?: "",
                    diagramId = note?.diagramId,
                    diagramCaption = note?.diagramCaption,
                    solvedProblemHindi = note?.solvedNumerical?.problemStatementHindi ?: "",
                    solvedProblemEnglish = note?.solvedNumerical?.problemStatementEnglish,
                    solvedGivenValuesJson = JSONArray(note?.solvedNumerical?.givenValues ?: emptyList<String>()).toString(),
                    solvedFormulaUsed = note?.solvedNumerical?.formulaUsed ?: "",
                    solvedStepsJson = JSONArray(note?.solvedNumerical?.stepByStepSolution ?: emptyList<String>()).toString(),
                    solvedFinalAnswer = note?.solvedNumerical?.finalAnswer ?: "",
                    commonMistakesJson = JSONArray(note?.commonMistakes ?: emptyList<String>()).toString(),
                    mpBoardExamFormat = note?.mpBoardExamFormat ?: "",
                    quickRevisionPointsJson = JSONArray(note?.quickRevisionPoints ?: emptyList<String>()).toString(),
                    practiceQuestionsJson = JSONArray(note?.practiceQuestions ?: emptyList<String>()).toString(),
                    jeeEnrichment = note?.jeeEnrichment,
                    isBookmarked = bookmarkedTopicIds.contains(topic.id),
                    isCompleted = completedTopicIds.contains(topic.id),
                    lastVisitedTimestamp = if (topic.id == "top_1") System.currentTimeMillis() else 0L
                )
            }
            physicsNoteDao.insertAll(entities)
        }

        if (formulaDao.countFormulas() == 0) {
            val bookmarkedFormulaIds = getBookmarkedFormulas()
            val formulaEntities = FormulaData.allFormulas.map { f ->
                FormulaEntity(
                    formulaId = f.id,
                    topicId = f.topicId,
                    topicName = f.topicName,
                    nameHindi = f.nameHindi,
                    nameEnglish = f.nameEnglish,
                    mathExpression = f.mathExpression,
                    symbolsMeaningJson = JSONArray(f.symbolsMeaning).toString(),
                    siUnit = f.siUnit,
                    conditions = f.conditions,
                    workedExample = f.workedExample,
                    isBookmarked = bookmarkedFormulaIds.contains(f.id)
                )
            }
            formulaDao.insertAll(formulaEntities)
        }

        // Sync existing student notes to Room
        val existingNotes = getUserNotes()
        for (note in existingNotes) {
            studentNoteDao.insertStudentNote(
                StudentNoteEntity(
                    id = note.id,
                    topicId = note.topicId,
                    topicTitle = note.topicTitle,
                    content = note.content,
                    timestamp = note.timestamp
                )
            )
        }
    }

    // Room Reactive Flows
    fun getPhysicsNotesFlow(): Flow<List<PhysicsNoteEntity>> = physicsNoteDao.getAllNotes()
    fun getBookmarkedNotesFlow(): Flow<List<PhysicsNoteEntity>> = physicsNoteDao.getBookmarkedNotes()
    fun getCompletedNotesFlow(): Flow<List<PhysicsNoteEntity>> = physicsNoteDao.getCompletedNotes()
    fun getRecentlyVisitedNotesFlow(limit: Int = 6): Flow<List<PhysicsNoteEntity>> = physicsNoteDao.getRecentlyVisitedNotes(limit)
    fun getFormulasFlow(): Flow<List<FormulaEntity>> = formulaDao.getAllFormulas()
    fun getBookmarkedFormulasFlow(): Flow<List<FormulaEntity>> = formulaDao.getBookmarkedFormulas()
    fun getStudentNotesFlow(): Flow<List<StudentNoteEntity>> = studentNoteDao.getAllStudentNotes()

    // Topic Bookmark Operations
    fun getBookmarkedTopics(): Set<String> {
        return prefs.getStringSet("bookmarked_topics", emptySet()) ?: emptySet()
    }

    fun toggleTopicBookmark(topicId: String): Boolean {
        val current = getBookmarkedTopics().toMutableSet()
        val newState = if (current.contains(topicId)) {
            current.remove(topicId)
            false
        } else {
            current.add(topicId)
            true
        }
        prefs.edit().putStringSet("bookmarked_topics", current).apply()

        // Sync to Room Database
        repositoryScope.launch {
            physicsNoteDao.updateBookmarkStatus(topicId, newState)
        }
        return newState
    }

    fun isTopicBookmarked(topicId: String): Boolean {
        return getBookmarkedTopics().contains(topicId)
    }

    // Formula Bookmark Operations
    fun getBookmarkedFormulas(): Set<String> {
        return prefs.getStringSet("bookmarked_formulas", emptySet()) ?: emptySet()
    }

    fun toggleFormulaBookmark(formulaId: String): Boolean {
        val current = getBookmarkedFormulas().toMutableSet()
        val newState = if (current.contains(formulaId)) {
            current.remove(formulaId)
            false
        } else {
            current.add(formulaId)
            true
        }
        prefs.edit().putStringSet("bookmarked_formulas", current).apply()

        // Sync to Room Database
        repositoryScope.launch {
            formulaDao.updateBookmarkStatus(formulaId, newState)
        }
        return newState
    }

    fun isFormulaBookmarked(formulaId: String): Boolean {
        return getBookmarkedFormulas().contains(formulaId)
    }

    // Formula Flashcard Recall & Mastery Operations
    fun getMasteredFormulas(): Set<String> {
        return prefs.getStringSet("mastered_formulas", emptySet()) ?: emptySet()
    }

    fun markFormulaMastered(formulaId: String, isMastered: Boolean) {
        val current = getMasteredFormulas().toMutableSet()
        if (isMastered) {
            current.add(formulaId)
        } else {
            current.remove(formulaId)
        }
        prefs.edit().putStringSet("mastered_formulas", current).apply()
    }

    fun toggleFormulaMastered(formulaId: String): Boolean {
        val current = getMasteredFormulas().toMutableSet()
        val newState = if (current.contains(formulaId)) {
            current.remove(formulaId)
            false
        } else {
            current.add(formulaId)
            true
        }
        prefs.edit().putStringSet("mastered_formulas", current).apply()
        return newState
    }

    fun resetAllFormulaMastery() {
        prefs.edit().remove("mastered_formulas").apply()
    }

    // Topic Completion Operations
    fun getCompletedTopics(): Set<String> {
        return prefs.getStringSet("completed_topics", emptySet()) ?: emptySet()
    }

    fun toggleTopicCompletion(topicId: String): Boolean {
        val current = getCompletedTopics().toMutableSet()
        val newState = if (current.contains(topicId)) {
            current.remove(topicId)
            false
        } else {
            current.add(topicId)
            true
        }
        prefs.edit().putStringSet("completed_topics", current).apply()

        // Sync to Room Database
        repositoryScope.launch {
            physicsNoteDao.updateCompletionStatus(topicId, newState)
        }
        return newState
    }

    fun isTopicCompleted(topicId: String): Boolean {
        return getCompletedTopics().contains(topicId)
    }

    // Recent Topics Operations
    fun getRecentTopicIds(): List<String> {
        val raw = prefs.getString("recent_topics", "") ?: ""
        if (raw.isEmpty()) return listOf("top_1", "top_4")
        return raw.split(",").filter { it.isNotEmpty() }
    }

    fun addRecentTopic(topicId: String) {
        val current = getRecentTopicIds().toMutableList()
        current.remove(topicId)
        current.add(0, topicId)
        val limited = current.take(6)
        prefs.edit().putString("recent_topics", limited.joinToString(",")).apply()

        // Sync to Room Database
        repositoryScope.launch {
            physicsNoteDao.updateLastVisited(topicId, System.currentTimeMillis())
        }
    }

    // Student Notes Operations
    fun getUserNotes(): List<UserNote> {
        val raw = prefs.getString("user_notes_json", "[]") ?: "[]"
        val notes = mutableListOf<UserNote>()
        try {
            val jsonArray = JSONArray(raw)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                notes.add(
                    UserNote(
                        id = obj.getString("id"),
                        topicId = obj.getString("topicId"),
                        topicTitle = obj.getString("topicTitle"),
                        content = obj.getString("content"),
                        timestamp = obj.getLong("timestamp")
                    )
                )
            }
        } catch (_: Exception) {}
        return notes.sortedByDescending { it.timestamp }
    }

    fun saveUserNote(topicId: String, topicTitle: String, content: String): UserNote {
        val notes = getUserNotes().toMutableList()
        val newNote = UserNote(
            id = "note_${System.currentTimeMillis()}",
            topicId = topicId,
            topicTitle = topicTitle,
            content = content,
            timestamp = System.currentTimeMillis()
        )
        notes.add(0, newNote)
        persistUserNotes(notes)

        // Sync to Room Database
        repositoryScope.launch {
            studentNoteDao.insertStudentNote(
                StudentNoteEntity(
                    id = newNote.id,
                    topicId = newNote.topicId,
                    topicTitle = newNote.topicTitle,
                    content = newNote.content,
                    timestamp = newNote.timestamp
                )
            )
        }
        return newNote
    }

    fun deleteUserNote(noteId: String) {
        val notes = getUserNotes().filter { it.id != noteId }
        persistUserNotes(notes)

        // Sync to Room Database
        repositoryScope.launch {
            studentNoteDao.deleteStudentNoteById(noteId)
        }
    }

    private fun persistUserNotes(notes: List<UserNote>) {
        val jsonArray = JSONArray()
        for (note in notes) {
            val obj = JSONObject()
            obj.put("id", note.id)
            obj.put("topicId", note.topicId)
            obj.put("topicTitle", note.topicTitle)
            obj.put("content", note.content)
            obj.put("timestamp", note.timestamp)
            jsonArray.put(obj)
        }
        prefs.edit().putString("user_notes_json", jsonArray.toString()).apply()
    }

    fun getQuizScore(): Int {
        return prefs.getInt("quiz_total_score", 0)
    }

    fun recordQuizCompletion(correctAnswers: Int) {
        val current = getQuizScore()
        prefs.edit().putInt("quiz_total_score", current + correctAnswers).apply()
    }

    fun getQuizAttempts(): List<com.example.model.QuizAttemptRecord> {
        val raw = prefs.getString("quiz_attempts_json", "[]") ?: "[]"
        val attempts = mutableListOf<com.example.model.QuizAttemptRecord>()
        try {
            val jsonArray = JSONArray(raw)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                attempts.add(
                    com.example.model.QuizAttemptRecord(
                        id = obj.getString("id"),
                        topicTitle = obj.getString("topicTitle"),
                        correctCount = obj.getInt("correctCount"),
                        totalQuestions = obj.getInt("totalQuestions"),
                        percentage = obj.getInt("percentage"),
                        timestamp = obj.getLong("timestamp")
                    )
                )
            }
        } catch (_: Exception) {}
        return attempts
    }

    fun saveQuizAttempt(topicTitle: String, correctCount: Int, totalQuestions: Int) {
        val attempts = getQuizAttempts().toMutableList()
        val percentage = if (totalQuestions > 0) ((correctCount.toFloat() / totalQuestions) * 100).toInt() else 0
        val newRecord = com.example.model.QuizAttemptRecord(
            id = "attempt_${System.currentTimeMillis()}",
            topicTitle = topicTitle,
            correctCount = correctCount,
            totalQuestions = totalQuestions,
            percentage = percentage,
            timestamp = System.currentTimeMillis()
        )
        attempts.add(newRecord)
        val jsonArray = JSONArray()
        for (item in attempts.takeLast(20)) { // Keep last 20 attempts for tracking
            val obj = JSONObject()
            obj.put("id", item.id)
            obj.put("topicTitle", item.topicTitle)
            obj.put("correctCount", item.correctCount)
            obj.put("totalQuestions", item.totalQuestions)
            obj.put("percentage", item.percentage)
            obj.put("timestamp", item.timestamp)
            jsonArray.put(obj)
        }
        prefs.edit().putString("quiz_attempts_json", jsonArray.toString()).apply()
    }
}
