package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("ConceptX", appName)
  }

  @Test
  fun `verify chapter 1 has 12 syllabus topics`() {
    val topics = com.example.data.PhysicsChapterData.topics
    assertEquals(12, topics.size)
  }

  @Test
  fun `verify room database initializes and persists notes and bookmark status`() = kotlinx.coroutines.test.runTest {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = androidx.room.Room.inMemoryDatabaseBuilder(
      context,
      com.example.data.room.ConceptXDatabase::class.java
    ).allowMainThreadQueries().build()

    val dao = db.physicsNoteDao()
    assertEquals(0, dao.countNotes())

    val noteEntity = com.example.data.room.PhysicsNoteEntity(
      topicId = "top_1",
      chapterId = "phy_ch1",
      number = 1,
      titleHindi = "विद्युत आवेश",
      titleEnglish = "Electric Charge",
      conceptSimpleHindi = "विद्युत आवेश पदार्थ का मूल गुण है।",
      realLifeAnalogy = "कंघी और बाल का घर्षण।",
      stepByStepExplanationJson = "[]",
      importantDefinition = "आवेश की परिभाषा",
      siUnitsAndDimensions = "Coulomb",
      diagramId = "diagram_point_charges",
      diagramCaption = "बिंदु आवेश",
      solvedProblemHindi = "आंकिक प्रश्न",
      solvedProblemEnglish = null,
      solvedGivenValuesJson = "[]",
      solvedFormulaUsed = "q = ne",
      solvedStepsJson = "[]",
      solvedFinalAnswer = "6.25 x 10^18",
      commonMistakesJson = "[]",
      mpBoardExamFormat = "2 अंक",
      quickRevisionPointsJson = "[]",
      practiceQuestionsJson = "[]",
      jeeEnrichment = null,
      isBookmarked = false,
      isCompleted = false
    )

    dao.insertNote(noteEntity)
    assertEquals(1, dao.countNotes())

    val retrieved = dao.getNoteByTopicIdSync("top_1")
    org.junit.Assert.assertNotNull(retrieved)
    assertEquals("विद्युत आवेश", retrieved?.titleHindi)
    assertEquals(false, retrieved?.isBookmarked)

    dao.updateBookmarkStatus("top_1", true)
    val bookmarkedList = dao.getBookmarkedNotesSync()
    assertEquals(1, bookmarkedList.size)
    assertEquals(true, bookmarkedList.first().isBookmarked)

    db.close()
  }
}
