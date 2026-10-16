package com.example.network

import com.example.BuildConfig
import com.example.model.ChatMessage
import com.example.model.MessageSender
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiTeacherService {

    companion object {
        private const val MODEL_NAME = "gemini-3.5-flash"
        private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models"

        const val SYSTEM_PROMPT = """You are ConceptX Teacher, a patient and accurate Class 12 MP Board tutor. Explain primarily in simple Hindi, using English technical terms where helpful. Start with intuition, then explain the concept, formula, symbol meanings and a worked example. Adapt to the student's level. Never invent textbook references, official exam questions or unsupported facts. If you are uncertain, say so. Do not give a fabricated answer merely to sound confident. Clearly distinguish verified syllabus content from optional JEE enrichment."""
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun askTutor(
        userMessage: String,
        subjectName: String,
        chapterName: String,
        topicName: String?,
        conversationHistory: List<ChatMessage>
    ): Result<String> = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            // Provide a pedagogical syllabus-guided response if API key is not configured in Secrets
            return@withContext Result.success(
                generateLocalCurriculumResponse(userMessage, topicName ?: chapterName)
            )
        }

        try {
            val url = "$BASE_URL/$MODEL_NAME:generateContent?key=$apiKey"

            val rootJson = JSONObject()

            // System instructions
            val systemInstructionObj = JSONObject()
            val systemParts = JSONArray()
            val contextualSystemInstruction = "$SYSTEM_PROMPT\n\nCURRENT CONTEXT:\nविषय: $subjectName\nअध्याय: $chapterName" +
                    (if (!topicName.isNullOrEmpty()) "\nवर्तमान विषय: $topicName" else "")
            systemParts.put(JSONObject().put("text", contextualSystemInstruction))
            systemInstructionObj.put("parts", systemParts)
            rootJson.put("systemInstruction", systemInstructionObj)

            // Conversation history (last 6 messages for context)
            val contentsArray = JSONArray()
            val recentHistory = conversationHistory.takeLast(6)
            for (msg in recentHistory) {
                val role = if (msg.sender == MessageSender.USER) "user" else "model"
                val contentObj = JSONObject()
                contentObj.put("role", role)
                val partsArray = JSONArray()
                partsArray.put(JSONObject().put("text", msg.text))
                contentObj.put("parts", partsArray)
                contentsArray.put(contentObj)
            }

            // Current prompt
            val currentUserObj = JSONObject()
            currentUserObj.put("role", "user")
            val currentPartsArray = JSONArray()
            currentPartsArray.put(JSONObject().put("text", userMessage))
            currentUserObj.put("parts", currentPartsArray)
            contentsArray.put(currentUserObj)

            rootJson.put("contents", contentsArray)

            // Generation config
            val generationConfig = JSONObject()
            generationConfig.put("temperature", 0.6)
            generationConfig.put("topP", 0.95)
            generationConfig.put("maxOutputTokens", 1200)
            rootJson.put("generationConfig", generationConfig)

            val requestBody = rootJson.toString().toRequestBody("application/json".toMediaType())

            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val response = okHttpClient.newCall(request).execute()
            val responseBody = response.body?.string().orEmpty()

            if (!response.isSuccessful) {
                val errorMsg = try {
                    val errJson = JSONObject(responseBody)
                    val errorObj = errJson.optJSONObject("error")
                    errorObj?.optString("message") ?: "HTTP Error ${response.code}"
                } catch (_: Exception) {
                    "HTTP ${response.code}: $responseBody"
                }
                return@withContext Result.failure(Exception("AI Tutor Response Error: $errorMsg"))
            }

            val jsonResponse = JSONObject(responseBody)
            val candidates = jsonResponse.optJSONArray("candidates")
            if (candidates != null && candidates.length() > 0) {
                val firstCandidate = candidates.getJSONObject(0)
                val contentObj = firstCandidate.optJSONObject("content")
                val parts = contentObj?.optJSONArray("parts")
                if (parts != null && parts.length() > 0) {
                    val replyText = parts.getJSONObject(0).optString("text")
                    if (replyText.isNotBlank()) {
                        return@withContext Result.success(replyText)
                    }
                }
            }

            Result.failure(Exception("Empty response from ConceptX AI Teacher"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun generateLocalCurriculumResponse(query: String, topic: String): String {
        val qLower = query.lowercase()
        return when {
            qLower.contains("सरल") || qLower.contains("simple") || qLower.contains("समझा") -> {
                "नमस्ते! ConceptX शिक्षक के अनुसार '$topic' को ऐसे समझिए:\n\n" +
                        "1. **मूल विचार**: स्थिर वैद्युतिकी में सभी प्रभाव आवेशों के स्थानांतरण या उनके विद्युत क्षेत्र द्वारा उत्पन्न होते हैं।\n" +
                        "2. **दैनिक जीवन का उदाहरण**: जैसे चुंबक बिना छुए लोहे को खींचता है, वैसे ही एक आवेश अपने चारों ओर के अदृश्य 'प्रभाव क्षेत्र' में दूसरे आवेश पर बल लगाता है।\n" +
                        "3. **बोर्ड परीक्षा टिप**: एमपी बोर्ड में हमेशा परिभाषा के साथ गणितीय सूत्र और SI मात्रक अवश्य लिखें।"
            }
            qLower.contains("हिंट") || qLower.contains("hint") -> {
                "💡 **सॉल्विंग हिंट (Hint)**:\n\n" +
                        "• सबसे पहले प्रश्न में दिए गए सभी मानों (Given Values) को SI मात्रक (Meter, Coulomb, Second) में बदलें।\n" +
                        "• कूलॉम बल के लिए F = (1 / 4πε₀) · (|q₁ q₂| / r²) अथवा विद्युत क्षेत्र के लिए E = F / q₀ का चयन करें।\n" +
                        "• ध्यान रहे: सदिश राशियों में दिशा का उल्लेख करना 1 अंक दिलाता है!"
            }
            qLower.contains("संख्यात्मक") || qLower.contains("numerical") -> {
                "📐 **आंकिक प्रश्न हल करने की विधि**:\n\n" +
                        "1. सूत्र: F = 9 × 10⁹ × (q₁ · q₂) / r²\n" +
                        "2. यदि दूरी सेंटीमीटर में हो तो उसे मीटर में (r × 10⁻² m) अवश्य बदलें।\n" +
                        "3. अंतिम उत्तर के बाद मात्रक (N अथवा N/C) लिखना न भूलें।"
            }
            else -> {
                "ConceptX AI Teacher: '$topic' के बारे में आपका प्रश्न प्राप्त हुआ।\n\n" +
                        "कक्षा 12वीं एमपी बोर्ड के पाठ्यक्रम अनुसार, इस अध्याय में स्थिर आवेशों के अंतःसंबंध, कूलॉम का व्युत्क्रम वर्ग नियम तथा गाउस की प्रमेय सर्वाधिक महत्वपूर्ण हैं।\n\n" +
                        "यदि आपके पास कोई विशिष्ट आंकिक प्रश्न या परिभाषा का संदेह है, तो नीचे दिए गए त्वरित विकल्पों में से चुनिए!"
            }
        }
    }
}
