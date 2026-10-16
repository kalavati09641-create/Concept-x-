package com.example.model

enum class Screen {
    HOME,
    SUBJECTS,
    TOPIC_DETAIL,
    FORMULA_BANK,
    AI_TEACHER,
    PRACTICE,
    BOOKMARKS_NOTES
}

data class Subject(
    val id: String,
    val nameHindi: String,
    val nameEnglish: String,
    val code: String,
    val chaptersCount: Int,
    val description: String,
    val iconName: String,
    val colorHex: Long,
    val isAvailable: Boolean = true
)

data class Chapter(
    val id: String,
    val subjectId: String,
    val number: Int,
    val titleHindi: String,
    val titleEnglish: String,
    val unitHindi: String,
    val boardMarksWeightage: String, // e.g. "6-8 अंक (Unit 1: स्थिर विद्युतिकी)"
    val topicsCount: Int,
    val summaryHindi: String
)

data class Topic(
    val id: String,
    val chapterId: String,
    val number: Int,
    val titleHindi: String,
    val titleEnglish: String,
    val estimatedMinutes: Int = 15,
    val isCoreBoardTopic: Boolean = true,
    val hasFullNotes: Boolean = true
)

data class FormulaItem(
    val id: String,
    val nameHindi: String,
    val nameEnglish: String,
    val mathExpression: String,
    val symbolsMeaning: List<String>,
    val siUnit: String,
    val conditions: String,
    val topicId: String,
    val topicName: String,
    val workedExample: String? = null
)

data class NumericalExample(
    val problemStatementHindi: String,
    val problemStatementEnglish: String? = null,
    val givenValues: List<String>,
    val formulaUsed: String,
    val stepByStepSolution: List<String>,
    val finalAnswer: String
)

data class TopicNote(
    val topicId: String,
    val titleHindi: String,
    val titleEnglish: String,
    // 12-section Concept Notes Engine:
    val conceptSimpleHindi: String,               // A. सरल हिंदी में संकल्पना
    val realLifeAnalogy: String,                  // B. दैनिक जीवन का उदाहरण
    val stepByStepExplanation: List<String>,      // C. चरण-दर-चरण व्याख्या
    val importantDefinition: String,              // D. महत्वपूर्ण परिभाषा (बोर्ड प्रारूप)
    val formulas: List<FormulaItem>,              // E. सूत्र एवं प्रतीकों का अर्थ
    val siUnitsAndDimensions: String,             // F. SI मात्रक एवं विमीय सूत्र
    val diagramId: String? = null,                // G. चित्र अथवा आरेख (SVG/Canvas)
    val diagramCaption: String? = null,
    val solvedNumerical: NumericalExample,        // H. हल सहित आंकिक प्रश्न
    val commonMistakes: List<String>,             // I. छात्रों द्वारा सामान्य गलतियाँ
    val mpBoardExamFormat: String,                // J. बोर्ड परीक्षा में उत्तर लेखन प्रारूप
    val quickRevisionPoints: List<String>,        // K. त्वरित दोहराव बिंदु
    val practiceQuestions: List<String>,          // L. अभ्यास प्रश्न
    val jeeEnrichment: String? = null             // Optional JEE Enrichment
)

data class QuizQuestion(
    val id: String,
    val topicId: String,
    val topicTitle: String,
    val questionHindi: String,
    val questionEnglish: String,
    val optionsHindi: List<String>,
    val correctIndex: Int,
    val explanationHindi: String,
    val marks: Int = 1
)

data class UserNote(
    val id: String,
    val topicId: String,
    val topicTitle: String,
    val content: String,
    val timestamp: Long
)

data class ChatMessage(
    val id: String,
    val sender: MessageSender,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isError: Boolean = false,
    val isStreaming: Boolean = false
)

enum class MessageSender {
    USER,
    AI_TEACHER
}
