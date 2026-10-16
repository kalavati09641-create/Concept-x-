package com.example.data

import com.example.model.QuizQuestion

object QuizData {

    val allQuestions: List<QuizQuestion> = listOf(
        QuizQuestion(
            id = "q_1",
            topicId = "top_1",
            topicTitle = "विद्युत आवेश",
            questionHindi = "1 कूलॉम ऋणावेश में उपस्थित इलेक्ट्रॉनों की संख्या कितनी होती है?",
            questionEnglish = "How many electrons constitute 1 Coulomb of negative charge?",
            optionsHindi = listOf(
                "6.25 × 10¹⁶ इलेक्ट्रॉन",
                "6.25 × 10¹⁸ इलेक्ट्रॉन",
                "1.6 × 10⁻¹⁹ इलेक्ट्रॉन",
                "9 × 10⁹ इलेक्ट्रॉन"
            ),
            correctIndex = 1,
            explanationHindi = "आवेश के क्वांटीकरण सूत्र q = n · e से, n = q / e = 1 / (1.6 × 10⁻¹⁹) = 6.25 × 10¹⁸ इलेक्ट्रॉन। अतः 1 कूलॉम आवेश में 6.25 × 10¹⁸ इलेक्ट्रॉन होते हैं।"
        ),
        QuizQuestion(
            id = "q_2",
            topicId = "top_1",
            topicTitle = "विद्युत आवेश",
            questionHindi = "निम्नलिखित में से कौन-सा आवेश किसी वस्तु पर संभव नहीं है?",
            questionEnglish = "Which of the following charges is NOT possible on a body?",
            optionsHindi = listOf(
                "3.2 × 10⁻¹⁹ C",
                "6.4 × 10⁻¹⁹ C",
                "2.4 × 10⁻¹⁹ C",
                "1.6 × 10⁻¹⁸ C"
            ),
            correctIndex = 2,
            explanationHindi = "आवेश के क्वांटीकरण के अनुसार किसी भी वस्तु पर आवेश q = ne (जहाँ n पूर्ण संख्या होनी चाहिए)। विकल्प 3 में n = (2.4 × 10⁻¹⁹) / (1.6 × 10⁻¹⁹) = 1.5 आता है जो कि एक भिन्न (Fraction) है, अतः यह संभव नहीं है।"
        ),
        QuizQuestion(
            id = "q_3",
            topicId = "top_4",
            topicTitle = "कूलॉम का नियम",
            questionHindi = "यदि दो बिंदु आवेशों के बीच की दूरी को आधा कर दिया जाए, तो उनके बीच लगने वाला स्थिर वैद्युत बल हो जाएगा:",
            questionEnglish = "If the distance between two point charges is halved, the electrostatic force between them becomes:",
            optionsHindi = listOf(
                "आधा (1/2 गुना)",
                "दोगुना (2 गुना)",
                "चार गुना (4 गुना)",
                "अपरिवर्तित रहेगा"
            ),
            correctIndex = 2,
            explanationHindi = "कूलॉम के व्युत्क्रम वर्ग नियम के अनुसार बल F ∝ 1/r² होता है। यदि दूरी r/2 हो जाए, तो F' ∝ 1/(r/2)² = 4/r² = 4F। अतः बल 4 गुना हो जाएगा।"
        ),
        QuizQuestion(
            id = "q_4",
            topicId = "top_4",
            topicTitle = "कूलॉम का नियम",
            questionHindi = "निर्वात की विद्युतशीलता ε₀ का SI मात्रक क्या है?",
            questionEnglish = "What is the SI unit of permittivity of free space ε₀?",
            optionsHindi = listOf(
                "N·m² / C²",
                "C² / (N·m²)",
                "C / (N·m)",
                "N / C"
            ),
            correctIndex = 1,
            explanationHindi = "कूलॉम नियम F = (1/4πε₀) · (q₁q₂/r²) से ε₀ = (q₁q₂) / (4π · F · r²)। अतः इसका मात्रक C² / (N·m²) अथवा फैराड/मीटर (F/m) होता है।"
        ),
        QuizQuestion(
            id = "q_5",
            topicId = "top_6",
            topicTitle = "विद्युत क्षेत्र",
            questionHindi = "विद्युत क्षेत्र की तीव्रता (E) का SI मात्रक क्या है?",
            questionEnglish = "What is the SI unit of electric field intensity?",
            optionsHindi = listOf(
                "न्यूटन-मीटर (N·m)",
                "न्यूटन/कूलॉम (N/C)",
                "कूलॉम/न्यूटन (C/N)",
                "जूल/कूलॉम (J/C)"
            ),
            correctIndex = 1,
            explanationHindi = "विद्युत क्षेत्र की तीव्रता E = F / q₀ होती है, जहाँ बल F न्यूटन में तथा परीक्षण आवेश कूलॉम में होता है। अतः SI मात्रक न्यूटन/कूलॉम (N/C) या वोल्ट/मीटर (V/m) है।"
        ),
        QuizQuestion(
            id = "q_6",
            topicId = "top_7",
            topicTitle = "विद्युत क्षेत्र रेखाएँ",
            questionHindi = "दो विद्युत क्षेत्र रेखाएँ एक-दूसरे को कभी नहीं काटती हैं, क्योंकि:",
            questionEnglish = "Two electric field lines never intersect each other because:",
            optionsHindi = listOf(
                "वे हमेशा समांतर होती हैं",
                "प्रतिच्छेदन बिंदु पर विद्युत क्षेत्र की दो दिशाएँ हो जाएंगी, जो असंभव है",
                "वे सीधी रेखाओं में नहीं चलती हैं",
                "वे ऊर्जा का ह्रास करती हैं"
            ),
            correctIndex = 1,
            explanationHindi = "यदि दो बल रेखाएँ एक-दूसरे को काटेंगी, तो कटान बिंदु पर दो स्पर्श रेखाएँ खींची जा सकेंगी, जिसका अर्थ होगा उस बिंदु पर विद्युत क्षेत्र की दो दिशाएँ हैं—जो कि भौतिक रूप से असंभव है।"
        ),
        QuizQuestion(
            id = "q_7",
            topicId = "top_8",
            topicTitle = "विद्युत द्विध्रुव",
            questionHindi = "विद्युत द्विध्रुव आघूर्ण (p⃗) की दिशा भौतिक विज्ञान में मानी जाती है:",
            questionEnglish = "In physics, the direction of electric dipole moment vector p is taken from:",
            optionsHindi = listOf(
                "धनावेश से ऋणावेश की ओर (+q to -q)",
                "ऋणावेश से धनावेश की ओर (-q to +q)",
                "दोनों आवेशों के लंबवत",
                "इसकी कोई निश्चित दिशा नहीं होती"
            ),
            correctIndex = 1,
            explanationHindi = "भौतिक विज्ञान में विद्युत द्विध्रुव आघूर्ण p⃗ एक सदिश राशि है जिसकी दिशा परिपाटी के अनुसार सदैव ऋणावेश (-q) से धनावेश (+q) की ओर मानी जाती है।"
        ),
        QuizQuestion(
            id = "q_8",
            topicId = "top_10",
            topicTitle = "द्विध्रुव पर बल आघूर्ण",
            questionHindi = "एकसमान विद्युत क्षेत्र E में रखे विद्युत द्विध्रुव p पर अधिकतम बल आघूर्ण कब लगता है?",
            questionEnglish = "When does a dipole experience maximum torque in a uniform electric field?",
            optionsHindi = listOf(
                "जब द्विध्रुव क्षेत्र के समांतर हो (θ = 0°)",
                "जब द्विध्रुव क्षेत्र के प्रति-समांतर हो (θ = 180°)",
                "जब द्विध्रुव क्षेत्र के लंबवत हो (θ = 90°)",
                "जब कोण θ = 45° हो"
            ),
            correctIndex = 2,
            explanationHindi = "बल आघूर्ण का सूत्र τ = pE sin θ होता है। sin θ का अधिकतम मान 1 होता है जब θ = 90°। अतः जब द्विध्रुव विद्युत क्षेत्र के लंबवत होता है, तब उस पर अधिकतम बल आघूर्ण τ_max = pE लगता है।"
        ),
        QuizQuestion(
            id = "q_9",
            topicId = "top_11",
            topicTitle = "विद्युत फ्लक्स",
            questionHindi = "यदि किसी बंद पृष्ठ के अंदर नेट आवेश शून्य (Σq = 0) हो, तो उस पृष्ठ से गुजरने वाला कुल विद्युत फ्लक्स होगा:",
            questionEnglish = "If the net charge enclosed by a closed surface is zero, the total electric flux is:",
            optionsHindi = listOf(
                "अनंत",
                "शून्य (Zero)",
                "q / ε₀",
                "ऋणात्मक"
            ),
            correctIndex = 1,
            explanationHindi = "गाउस की प्रमेय Φ = q_enclosed / ε₀ के अनुसार, यदि बंद पृष्ठ के अंदर नेट परिबद्ध आवेश शून्य है, तो पृष्ठ से गुजरने वाला कुल विद्युत फ्लक्स भी शून्य होगा (जितनी बल रेखाएं प्रवेश करेंगी, उतनी ही बाहर निकलेंगी)।"
        ),
        QuizQuestion(
            id = "q_10",
            topicId = "top_12",
            topicTitle = "गाउस का नियम",
            questionHindi = "एक समान रूप से आवेशित पतले खोखले चालक गोले के भीतर विद्युत क्षेत्र की तीव्रता कितनी होती है?",
            questionEnglish = "What is the electric field intensity inside a uniformly charged hollow spherical shell?",
            optionsHindi = listOf(
                "शून्य (Zero)",
                "सतह के बराबर",
                "अनंत",
                "दूरी r के व्युत्क्रमानुपाती"
            ),
            correctIndex = 0,
            explanationHindi = "किसी आवेशित चालक खोल के भीतर कोई आवेश उपस्थित नहीं होता (सभी आवेश बाहरी सतह पर रहता है)। अतः गोले के अंदर किसी भी काल्पनिक गाउसीय पृष्ठ में q_enclosed = 0 होने के कारण विद्युत क्षेत्र E = 0 होता है।"
        )
    )

    fun getQuestionsForTopic(topicId: String): List<QuizQuestion> {
        val filtered = allQuestions.filter { it.topicId == topicId }
        return if (filtered.isNotEmpty()) filtered else allQuestions.take(5)
    }
}
