package com.example.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        PhysicsNoteEntity::class,
        FormulaEntity::class,
        StudentNoteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ConceptXDatabase : RoomDatabase() {

    abstract fun physicsNoteDao(): PhysicsNoteDao
    abstract fun formulaDao(): FormulaDao
    abstract fun studentNoteDao(): StudentNoteDao

    companion object {
        @Volatile
        private var INSTANCE: ConceptXDatabase? = null

        fun getInstance(context: Context): ConceptXDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ConceptXDatabase::class.java,
                    "conceptx_mp_physics.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
