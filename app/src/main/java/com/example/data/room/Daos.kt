package com.example.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PhysicsNoteDao {
    @Query("SELECT * FROM physics_notes ORDER BY number ASC")
    fun getAllNotes(): Flow<List<PhysicsNoteEntity>>

    @Query("SELECT * FROM physics_notes WHERE topicId = :topicId LIMIT 1")
    fun getNoteByTopicId(topicId: String): Flow<PhysicsNoteEntity?>

    @Query("SELECT * FROM physics_notes WHERE topicId = :topicId LIMIT 1")
    suspend fun getNoteByTopicIdSync(topicId: String): PhysicsNoteEntity?

    @Query("SELECT * FROM physics_notes WHERE isBookmarked = 1 ORDER BY number ASC")
    fun getBookmarkedNotes(): Flow<List<PhysicsNoteEntity>>

    @Query("SELECT * FROM physics_notes WHERE isBookmarked = 1 ORDER BY number ASC")
    suspend fun getBookmarkedNotesSync(): List<PhysicsNoteEntity>

    @Query("SELECT * FROM physics_notes WHERE isCompleted = 1 ORDER BY number ASC")
    fun getCompletedNotes(): Flow<List<PhysicsNoteEntity>>

    @Query("SELECT * FROM physics_notes WHERE isCompleted = 1 ORDER BY number ASC")
    suspend fun getCompletedNotesSync(): List<PhysicsNoteEntity>

    @Query("SELECT * FROM physics_notes WHERE lastVisitedTimestamp > 0 ORDER BY lastVisitedTimestamp DESC LIMIT :limit")
    fun getRecentlyVisitedNotes(limit: Int = 6): Flow<List<PhysicsNoteEntity>>

    @Query("SELECT * FROM physics_notes WHERE lastVisitedTimestamp > 0 ORDER BY lastVisitedTimestamp DESC LIMIT :limit")
    suspend fun getRecentlyVisitedNotesSync(limit: Int = 6): List<PhysicsNoteEntity>

    @Query("SELECT COUNT(*) FROM physics_notes")
    suspend fun countNotes(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notes: List<PhysicsNoteEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: PhysicsNoteEntity)

    @Query("UPDATE physics_notes SET isBookmarked = :isBookmarked WHERE topicId = :topicId")
    suspend fun updateBookmarkStatus(topicId: String, isBookmarked: Boolean)

    @Query("UPDATE physics_notes SET isCompleted = :isCompleted WHERE topicId = :topicId")
    suspend fun updateCompletionStatus(topicId: String, isCompleted: Boolean)

    @Query("UPDATE physics_notes SET lastVisitedTimestamp = :timestamp WHERE topicId = :topicId")
    suspend fun updateLastVisited(topicId: String, timestamp: Long)
}

@Dao
interface FormulaDao {
    @Query("SELECT * FROM formulas ORDER BY formulaId ASC")
    fun getAllFormulas(): Flow<List<FormulaEntity>>

    @Query("SELECT * FROM formulas WHERE isBookmarked = 1 ORDER BY formulaId ASC")
    fun getBookmarkedFormulas(): Flow<List<FormulaEntity>>

    @Query("SELECT * FROM formulas WHERE isBookmarked = 1 ORDER BY formulaId ASC")
    suspend fun getBookmarkedFormulasSync(): List<FormulaEntity>

    @Query("SELECT COUNT(*) FROM formulas")
    suspend fun countFormulas(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(formulas: List<FormulaEntity>)

    @Query("UPDATE formulas SET isBookmarked = :isBookmarked WHERE formulaId = :formulaId")
    suspend fun updateBookmarkStatus(formulaId: String, isBookmarked: Boolean)
}

@Dao
interface StudentNoteDao {
    @Query("SELECT * FROM student_notes ORDER BY timestamp DESC")
    fun getAllStudentNotes(): Flow<List<StudentNoteEntity>>

    @Query("SELECT * FROM student_notes ORDER BY timestamp DESC")
    suspend fun getAllStudentNotesSync(): List<StudentNoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudentNote(note: StudentNoteEntity)

    @Query("DELETE FROM student_notes WHERE id = :id")
    suspend fun deleteStudentNoteById(id: String)
}
