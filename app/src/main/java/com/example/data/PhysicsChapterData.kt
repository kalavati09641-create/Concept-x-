package com.example.data

import com.example.model.*

object PhysicsChapterData {

    val subjects: List<Subject> = listOf(
        Subject(
            id = "physics",
            nameHindi = "भौतिक विज्ञान",
            nameEnglish = "Physics",
            code = "PHY-12",
            chaptersCount = 14,
            description = "एमपी बोर्ड कक्षा 12वीं भौतिकी: स्थिर विद्युतिकी से लेकर आधुनिक भौतिकी तक के सभी अध्याय।",
            iconName = "physics",
            colorHex = 0xFF00B4D8,
            isAvailable = true
        ),
        Subject(
            id = "chemistry",
            nameHindi = "रसायन विज्ञान",
            nameEnglish = "Chemistry",
            code = "CHEM-12",
            chaptersCount = 10,
            description = "विलयन, विद्युत रसायन, d एवं f-ब्लॉक तथा कार्बनिक रसायन। (जल्द आ रहा है)",
            iconName = "chemistry",
            colorHex = 0xFF10B981,
            isAvailable = false
        ),
        Subject(
            id = "maths",
            nameHindi = "गणित",
            nameEnglish = "Mathematics",
            code = "MATH-12",
            chaptersCount = 13,
            description = "संबंध एवं फलन, आव्यूह, अवकलन, समाकलन एवं सदिश बीजगणित। (जल्द आ रहा है)",
            iconName = "maths",
            colorHex = 0xFFF59E0B,
            isAvailable = false
        ),
        Subject(
            id = "hindi",
            nameHindi = "हिंदी (आरोह एवं वितान)",
            nameEnglish = "Hindi Core",
            code = "HIN-12",
            chaptersCount = 18,
            description = "काव्य खंड, गद्य खंड, व्याकरण एवं पत्र लेखन। (जल्द आ रहा है)",
            iconName = "hindi",
            colorHex = 0xFFEC4899,
            isAvailable = false
        ),
        Subject(
            id = "english",
            nameHindi = "अंग्रेजी (Flamingo & Vistas)",
            nameEnglish = "English Core",
            code = "ENG-12",
            chaptersCount = 14,
            description = "Prose, Poetry, Reading Comprehension & Writing Skills. (Coming Soon)",
            iconName = "english",
            colorHex = 0xFF8B5CF6,
            isAvailable = false
        )
    )

    val currentChapter = Chapter(
        id = "phy_ch1",
        subjectId = "physics",
        number = 1,
        titleHindi = "विद्युत आवेश तथा क्षेत्र",
        titleEnglish = "Electric Charges and Fields",
        unitHindi = "इकाई 1: स्थिर विद्युतिकी (Electrostatics)",
        boardMarksWeightage = "6 से 8 अंक (इकाई भार)",
        topicsCount = 12,
        summaryHindi = "स्थिर विद्युत आवेश, उनके मूल गुण, कूलॉम का व्युत्क्रम वर्ग नियम, विद्युत क्षेत्र एवं रेखाएँ, विद्युत द्विध्रुव, विद्युत फ्लक्स तथा गाउस की प्रमेय व उसके महत्वपूर्ण अनुप्रयोग।"
    )

    val topics: List<Topic> = listOf(
        Topic(
            id = "top_1",
            chapterId = "phy_ch1",
            number = 1,
            titleHindi = "विद्युत आवेश (Electric Charge)",
            titleEnglish = "Electric Charge",
            estimatedMinutes = 12,
            isCoreBoardTopic = true,
            hasFullNotes = true
        ),
        Topic(
            id = "top_2",
            chapterId = "phy_ch1",
            number = 2,
            titleHindi = "चालक और विद्युतरोधी (Conductors & Insulators)",
            titleEnglish = "Conductors and Insulators",
            estimatedMinutes = 10,
            isCoreBoardTopic = true,
            hasFullNotes = true
        ),
        Topic(
            id = "top_3",
            chapterId = "phy_ch1",
            number = 3,
            titleHindi = "आवेश के मूल गुण (Properties of Charge)",
            titleEnglish = "Properties of Electric Charge",
            estimatedMinutes = 15,
            isCoreBoardTopic = true,
            hasFullNotes = true
        ),
        Topic(
            id = "top_4",
            chapterId = "phy_ch1",
            number = 4,
            titleHindi = "कूलॉम का नियम (Coulomb's Law)",
            titleEnglish = "Coulomb's Law",
            estimatedMinutes = 20,
            isCoreBoardTopic = true,
            hasFullNotes = true
        ),
        Topic(
            id = "top_5",
            chapterId = "phy_ch1",
            number = 5,
            titleHindi = "अनेक आवेशों के कारण बल एवं अध्यारोपण सिद्धांत",
            titleEnglish = "Forces between Multiple Charges & Superposition",
            estimatedMinutes = 15,
            isCoreBoardTopic = true,
            hasFullNotes = true
        ),
        Topic(
            id = "top_6",
            chapterId = "phy_ch1",
            number = 6,
            titleHindi = "विद्युत क्षेत्र एवं तीव्रता (Electric Field)",
            titleEnglish = "Electric Field",
            estimatedMinutes = 18,
            isCoreBoardTopic = true,
            hasFullNotes = true
        ),
        Topic(
            id = "top_7",
            chapterId = "phy_ch1",
            number = 7,
            titleHindi = "विद्युत क्षेत्र रेखाएँ और उनके गुण",
            titleEnglish = "Electric Field Lines",
            estimatedMinutes = 15,
            isCoreBoardTopic = true,
            hasFullNotes = true
        ),
        Topic(
            id = "top_8",
            chapterId = "phy_ch1",
            number = 8,
            titleHindi = "विद्युत द्विध्रुव एवं द्विध्रुव आघूर्ण",
            titleEnglish = "Electric Dipole & Dipole Moment",
            estimatedMinutes = 16,
            isCoreBoardTopic = true,
            hasFullNotes = true
        ),
        Topic(
            id = "top_9",
            chapterId = "phy_ch1",
            number = 9,
            titleHindi = "विद्युत द्विध्रुव के कारण विद्युत क्षेत्र (अक्षीय व निरक्षीय)",
            titleEnglish = "Electric Field due to a Dipole (Axial & Equatorial)",
            estimatedMinutes = 25,
            isCoreBoardTopic = true,
            hasFullNotes = true
        ),
        Topic(
            id = "top_10",
            chapterId = "phy_ch1",
            number = 10,
            titleHindi = "एकसमान विद्युत क्षेत्र में द्विध्रुव पर बल आघूर्ण",
            titleEnglish = "Torque on a Dipole in Uniform Electric Field",
            estimatedMinutes = 18,
            isCoreBoardTopic = true,
            hasFullNotes = true
        ),
        Topic(
            id = "top_11",
            chapterId = "phy_ch1",
            number = 11,
            titleHindi = "विद्युत फ्लक्स (Electric Flux)",
            titleEnglish = "Electric Flux",
            estimatedMinutes = 15,
            isCoreBoardTopic = true,
            hasFullNotes = true
        ),
        Topic(
            id = "top_12",
            chapterId = "phy_ch1",
            number = 12,
            titleHindi = "गाउस का नियम और उसके अनुप्रयोग",
            titleEnglish = "Gauss's Law and Applications",
            estimatedMinutes = 25,
            isCoreBoardTopic = true,
            hasFullNotes = true
        )
    )

    val topicNotes: Map<String, TopicNote> = mapOf(
        "top_1" to TopicNote(
            topicId = "top_1",
            titleHindi = "विद्युत आवेश (Electric Charge)",
            titleEnglish = "Electric Charge",
            conceptSimpleHindi = "विद्युत आवेश किसी भी पदार्थ के मूल कणों (जैसे इलेक्ट्रॉन और प्रोटॉन) का वह आंतरिक गुणधर्म है, जिसके कारण वह विद्युत तथा चुंबकीय प्रभाव उत्पन्न करता है और उन्हें अनुभव करता है। जैसे द्रव्यमान (Mass) के कारण गुरुत्वाकर्षण बल लगता है, ठीक वैसे ही आवेश (Charge) के कारण विद्युत बल लगता है।",
            realLifeAnalogy = "सर्दियों में जब आप ऊनी स्वेटर उतारते हैं, तो चट-चट की आवाज सुनाई देती है या सूखे बालों में प्लास्टिक की कंघी घुमाने पर वह कागज के छोटे टुकड़ों को अपनी ओर आकर्षित करने लगती है। यह घर्षण द्वारा आवेश उत्पन्न होने का प्रत्यक्ष दैनिक प्रमाण है।",
            stepByStepExplanation = listOf(
                "1. ब्रह्मांड के सभी पदार्थ परमाणुओं से बने हैं। परमाणु के नाभिक में धनावेशित प्रोटॉन और उदासीन न्यूट्रॉन होते हैं, जबकि नाभिक के चारों ओर ऋणावेशित इलेक्ट्रॉन चक्कर लगाते हैं।",
                "2. सामान्य अवस्था में किसी परमाणु में इलेक्ट्रॉनों की संख्या और प्रोटॉनों की संख्या बराबर होती है, इसलिए परमाणु वैद्युत रूप से उदासीन (Neutral) होता है।",
                "3. जब किसी वस्तु से इलेक्ट्रॉन बाहर निकलते हैं, तो उस पर इलेक्ट्रॉनों की कमी हो जाती है और वह धनावेशित (Positively Charged) हो जाती है।",
                "4. जब कोई वस्तु अतिरिक्त इलेक्ट्रॉन ग्रहण करती है, तो उस पर इलेक्ट्रॉनों की अधिकता हो जाती है और वह ऋणावेशित (Negatively Charged) हो जाती है।",
                "5. प्रोटॉन नाभिक में प्रबल नाभिकीय बल से बंधे होते हैं, इसलिए आवेश के स्थानांतरण के लिए केवल 'इलेक्ट्रॉन' ही उत्तरदायी होते हैं।"
            ),
            importantDefinition = "विद्युत आवेश पदार्थ का वह मूल गुण है जिसके कारण वह वैद्युत एवं चुंबकीय क्षेत्रों की उत्पत्ति तथा उनकी अंतःक्रिया को प्रदर्शित करता है। यह एक अदिश राशि (Scalar Quantity) है।",
            formulas = listOf(
                FormulaItem(
                    id = "f_q_ne",
                    nameHindi = "आवेश का क्वांटीकरण सूत्र",
                    nameEnglish = "Quantization of Charge Formula",
                    mathExpression = "q = ± n · e",
                    symbolsMeaning = listOf(
                        "q = वस्तु पर कुल आवेश (Coulomb)",
                        "n = स्थानांतरित इलेक्ट्रॉनों की पूर्णांक संख्या (1, 2, 3...)",
                        "e = एक मूल आवेश (इलेक्ट्रॉन पर आवेश का परिमाण = 1.6 × 10⁻¹⁹ C)"
                    ),
                    siUnit = "कूलॉम (Coulomb - C)",
                    conditions = "यह सूत्र सूक्ष्म स्तर पर लागू होता है। आवेश कभी भी भिन्न (Fraction) जैसे 0.5e या 1.7e के रूप में स्थानांतरित नहीं हो सकता।",
                    topicId = "top_1",
                    topicName = "विद्युत आवेश",
                    workedExample = "यदि 1 कूलॉम ऋणावेश हो, तो इलेक्ट्रॉनों की संख्या n = q/e = 1 / (1.6 × 10⁻¹⁹) = 6.25 × 10¹⁸ इलेक्ट्रॉन।"
                ),
                FormulaItem(
                    id = "f_q_it",
                    nameHindi = "विद्युत धारा एवं आवेश संबंध",
                    nameEnglish = "Current and Charge Relation",
                    mathExpression = "q = I · t",
                    symbolsMeaning = listOf(
                        "q = आवेश (Coulomb)",
                        "I = विद्युत धारा (Ampere)",
                        "t = समय (Second)"
                    ),
                    siUnit = "एम्पियर-सेकंड (A·s) अथवा कूलॉम (C)",
                    conditions = "जब नियत धारा I समय t के लिए प्रवाहित हो।",
                    topicId = "top_1",
                    topicName = "विद्युत आवेश"
                )
            ),
            siUnitsAndDimensions = "• SI मात्रक: कूलॉम (Coulomb, प्रतीक C) या एम्पियर-सेकंड (A·s)\n• CGS मात्रक: स्टेटकूलॉम (esu) या फ्रेंकलिन\n• संबंध: 1 C = 3 × 10⁹ statC\n• विमीय सूत्र (Dimensional Formula): [M⁰ L⁰ T¹ A¹] या [AT]",
            diagramId = "diagram_point_charges",
            diagramCaption = "धनावेश (+q) एवं ऋणावेश (-q) की बल रेखाएँ - धनावेश से बाहर की ओर, ऋणावेश की ओर अंदर आती हुई।",
            solvedNumerical = NumericalExample(
                problemStatementHindi = "एक पॉलीथीन के टुकड़े को ऊन से रगड़ने पर उस पर 3.2 × 10⁻⁷ C का ऋणावेश पाया जाता है। स्थानांतरित इलेक्ट्रॉनों की संख्या ज्ञात कीजिए तथा बताइए कि क्या ऊन से पॉलीथीन पर द्रव्यमान का भी स्थानांतरण हुआ है?",
                problemStatementEnglish = "A polythene piece rubbed with wool is found to have a negative charge of 3.2 × 10⁻⁷ C. Estimate the number of electrons transferred and check if there is mass transfer.",
                givenValues = listOf(
                    "आवेश q = -3.2 × 10⁻⁷ C",
                    "मूल आवेश e = 1.6 × 10⁻¹⁹ C",
                    "इलेक्ट्रॉन का द्रव्यमान mₑ = 9.1 × 10⁻³¹ kg"
                ),
                formulaUsed = "n = q / e तथा Δm = n · mₑ",
                stepByStepSolution = listOf(
                    "चरण 1: आवेश के क्वांटीकरण सूत्र q = n · e से इलेक्ट्रॉनों की संख्या n ज्ञात करेंगे।",
                    "n = (3.2 × 10⁻⁷ C) / (1.6 × 10⁻¹⁹ C) = 2 × 10¹² इलेक्ट्रॉन।",
                    "चरण 2: चूंकि पॉलीथीन पर ऋणावेश है, अतः इलेक्ट्रॉन ऊन से पॉलीथीन पर स्थानांतरित हुए हैं।",
                    "चरण 3: स्थानांतरित द्रव्यमान Δm = n × mₑ = (2 × 10¹²) × (9.1 × 10⁻³¹ kg) = 1.82 × 10⁻¹⁸ kg।"
                ),
                finalAnswer = "इलेक्ट्रॉनों की संख्या = 2 × 10¹² (ऊन से पॉलीथीन की ओर); स्थानांतरित द्रव्यमान = 1.82 × 10⁻¹⁸ kg (नगण्य किंतु वास्तविक)।"
            ),
            commonMistakes = listOf(
                "गलती 1: यह सोचना कि धनावेशित होने पर वस्तु में प्रोटॉन जुड़े हैं। (सत्य: प्रोटॉन कभी नहीं चलते, केवल इलेक्ट्रॉन निकलने से धनावेश आता है)।",
                "गलती 2: क्वांटीकरण में n को दशमलव मान लेना। n हमेशा पूर्ण संख्या (Integer) ही होता है।",
                "गलती 3: आवेश को सदिश (Vector) मानना। आवेश एक अदिश (Scalar) राशि है, इसे बीजगणितीय रूप से सीधे जोड़ा जा सकता है।"
            ),
            mpBoardExamFormat = "एमपी बोर्ड परीक्षा प्रारूप (अंक 2):\n• प्रश्न: 'आवेश के क्वांटीकरण से आप क्या समझते हैं?'\n• उत्तर लिखने की आदर्श विधि:\n  1. परिभाषा लिखें (1 अंक): 'प्रकृति में प्रत्येक आवेशित वस्तु पर आवेश सदैव मूल आवेश e का पूर्ण गुणज होता है, अर्थात् q = ±ne। इसे आवेश का क्वांटीकरण कहते हैं।'\n  2. सूत्र एवं प्रतीकों का अर्थ स्पष्ट करें (0.5 अंक): जहाँ n = 1, 2, 3... तथा e = 1.6 × 10⁻¹⁹ C।\n  3. SI मात्रक व विमीय सूत्र लिखें (0.5 अंक)।",
            quickRevisionPoints = listOf(
                "आवेश दो प्रकार के होते हैं: धनावेश (+) और ऋणावेश (-)।",
                "समान आवेश एक-दूसरे को प्रतिकर्षित तथा असमान आवेश आकर्षित करते हैं।",
                "आवेश सदैव द्रव्यमान से संबद्ध होता है (बिना द्रव्यमान के आवेश संभव नहीं)।",
                "आवेश का न्यूनतम संभव स्वतंत्र मान 1.6 × 10⁻¹⁹ C (मूल आवेश e) है।"
            ),
            practiceQuestions = listOf(
                "प्रश्न 1: क्या किसी पिंड पर 4.8 × 10⁻²⁰ C आवेश संभव है? कारण सहित समझाइए।",
                "प्रश्न 2: 1 कूलॉम आवेश में कितने इलेक्ट्रॉन होते हैं? (उत्तर: 6.25 × 10¹⁸)",
                "प्रश्न 3: स्थिर आवेश केवल विद्युत क्षेत्र उत्पन्न करता है अथवा चुंबकीय क्षेत्र भी? (उत्तर: केवल विद्युत क्षेत्र)"
            ),
            jeeEnrichment = "[JEE संवर्धन] क्वार्क कण (Quarks): प्रोटॉन और न्यूट्रॉन उप-परमाण्विक कणों 'क्वार्कों' से बने होते हैं जिन पर आवेश ±(1/3)e तथा ±(2/3)e होता है। परंतु क्वार्क कभी भी स्वतंत्र अवस्था में नहीं पाए जाते, इसलिए प्रकृति में न्यूनतम स्वतंत्र आवेश अभी भी 'e' ही मान्य है।"
        ),

        "top_4" to TopicNote(
            topicId = "top_4",
            titleHindi = "कूलॉम का नियम (Coulomb's Law)",
            titleEnglish = "Coulomb's Law",
            conceptSimpleHindi = "कूलॉम का नियम यह बताता है कि दो स्थिर बिंदु आवेश एक-दूसरे पर कितना आकर्षण या प्रतिकर्षण बल लगाते हैं। यह नियम न्यूटन के सार्वत्रिक गुरुत्वाकर्षण नियम जैसा ही है, बस यहाँ द्रव्यमानों की जगह आवेश आते हैं। आवेशों का परिमाण जितना अधिक होगा, बल उतना ही अधिक होगा; और उनके बीच की दूरी जितनी बढ़ेगी, बल उतनी ही तेजी से घटेगा।",
            realLifeAnalogy = "जब आप दो चुंबकों के समान ध्रुवों को पास लाते हैं, तो जैसे-जैसे वे करीब आते हैं, उन्हें दबाना बहुत कठिन हो जाता है क्योंकि प्रतिकर्षण बल दूरी कम होने पर बहुत तेजी से (दूरी के वर्ग के व्युत्क्रमानुपाती) बढ़ता है। ठीक ऐसा ही दो आवेशों के बीच होता है।",
            stepByStepExplanation = listOf(
                "1. फ्रांसीसी वैज्ञानिक चार्ल्स ऑगस्टिन डी कूलॉम ने 1785 में प्रयोगों द्वारा दो स्थिर बिंदु आवेशों के बीच कार्य करने वाले बल का नियम प्रतिपादित किया।",
                "2. कथन (पहला भाग): दो स्थिर बिंदु आवेशों के बीच लगने वाला आकर्षण या प्रतिकर्षण बल दोनों आवेशों के परिमाणों के गुणनफल के अनुक्रमानुपाती होता है (F ∝ |q₁ q₂|)।",
                "3. कथन (दूसरा भाग): यह बल उनके बीच की दूरी के वर्ग के व्युत्क्रमानुपाती होता है (F ∝ 1/r²)। इसे व्युत्क्रम वर्ग नियम कहते हैं।",
                "4. दोनों को मिलाने पर: F ∝ (|q₁ q₂| / r²) ⇒ F = k · (|q₁ q₂| / r²)।",
                "5. वायु या निर्वात के लिए: k = 1 / (4πε₀) ≈ 9 × 10⁹ N·m²/C²।",
                "6. बल की दिशा सदैव दोनों आवेशों को मिलाने वाली रेखा के अनुदिश (केंद्रीय बल) होती है।"
            ),
            importantDefinition = "कूलॉम का नियम: किन्हीं दो स्थिर बिंदु आवेशों के मध्य कार्य करने वाला स्थिर वैद्युत आकर्षण या प्रतिकर्षण बल दोनों आवेशों के परिमाणों के गुणनफल के अनुक्रमानुपाती तथा उनके बीच की दूरी के वर्ग के व्युत्क्रमानुपाती होता है। यह बल दोनों आवेशों को मिलाने वाली रेखा के अनुदिश कार्य करता है।",
            formulas = listOf(
                FormulaItem(
                    id = "f_coulomb_scalar",
                    nameHindi = "कूलॉम का नियम (अदिश रूप - निर्वात में)",
                    nameEnglish = "Coulomb's Law in Vacuum",
                    mathExpression = "F = \\frac{1}{4\\pi\\varepsilon_0} \\frac{|q_1 q_2|}{r^2}",
                    symbolsMeaning = listOf(
                        "F = स्थिर वैद्युत बल (Newton)",
                        "q₁, q₂ = बिंदु आवेशों के परिमाण (Coulomb)",
                        "r = आवेशों के बीच की दूरी (Meter)",
                        "ε₀ = निर्वात की विद्युतशीलता (Permittivity of Free Space = 8.854 × 10⁻¹² C²/N·m²)",
                        "1/(4πε₀) = 9 × 10⁹ N·m²/C² (गणना स्थिरांक)"
                    ),
                    siUnit = "न्यूटन (N)",
                    conditions = "1. आवेश बिंदुवत (Point Charges) होने चाहिए। 2. आवेश स्थिर (At Rest) होने चाहिए। 3. दूरी 10⁻¹⁵ m (नाभिकीय दूरी) से अधिक होनी चाहिए।",
                    topicId = "top_4",
                    topicName = "कूलॉम का नियम"
                ),
                FormulaItem(
                    id = "f_coulomb_medium",
                    nameHindi = "माध्यम में कूलॉम बल (परावैद्युतांक K)",
                    nameEnglish = "Coulomb's Law in Dielectric Medium",
                    mathExpression = "F_m = \\frac{F_{air}}{K} = \\frac{1}{4\\pi K \\varepsilon_0} \\frac{|q_1 q_2|}{r^2}",
                    symbolsMeaning = listOf(
                        "F_m = माध्यम में बल",
                        "K (या ε_r) = माध्यम का परावैद्युतांक (Dielectric Constant)",
                        "धातुओं के लिए K = ∞ (अनंत), जल के लिए K ≈ 81, वायु के लिए K ≈ 1"
                    ),
                    siUnit = "न्यूटन (N)",
                    conditions = "जब आवेश K परावैद्युतांक वाले समरूप माध्यम में रखे हों। माध्यम में बल 1/K गुना घट जाता है।",
                    topicId = "top_4",
                    topicName = "कूलॉम का नियम"
                )
            ),
            siUnitsAndDimensions = "• बल F का SI मात्रक: न्यूटन (N)\n• विमीय सूत्र: [M¹ L¹ T⁻²]\n• निर्वात की विद्युतशीलता ε₀ का मात्रक: C² / (N·m²) या F/m (फैराड/मीटर)\n• ε₀ का विमीय सूत्र: [M⁻¹ L⁻³ T⁴ A²]",
            diagramId = "diagram_coulomb_force",
            diagramCaption = "कूलॉम बल: सजातीय आवेशों में प्रतिकर्षण (F बाहर की ओर), विजातीय आवेशों में आकर्षण (F अंदर की ओर)।",
            solvedNumerical = NumericalExample(
                problemStatementHindi = "वायु में एक-दूसरे से 30 cm की दूरी पर रखे दो छोटे आवेशित गोलों पर क्रमशः 2 × 10⁻⁷ C तथा 3 × 10⁻⁷ C आवेश हैं। उनके बीच कितना बल कार्य करता है? बल की प्रकृति क्या होगी?",
                problemStatementEnglish = "What is the force between two small charged spheres having charges of 2 × 10⁻⁷ C and 3 × 10⁻⁷ C placed 30 cm apart in air?",
                givenValues = listOf(
                    "q₁ = 2 × 10⁻⁷ C",
                    "q₂ = 3 × 10⁻⁷ C",
                    "r = 30 cm = 0.3 m",
                    "k = 1/(4πε₀) = 9 × 10⁹ N·m²/C²"
                ),
                formulaUsed = "F = k · (q₁ q₂ / r²)",
                stepByStepSolution = listOf(
                    "चरण 1: दूरी को मीटर में बदलें: r = 30 × 10⁻² m = 0.3 m।",
                    "चरण 2: मान सूत्र में रखें: F = (9 × 10⁹) × (2 × 10⁻⁷ × 3 × 10⁻⁷) / (0.3)²",
                    "चरण 3: अंश का गुणनफल = 9 × 2 × 3 × 10⁹⁻¹⁴ = 54 × 10⁻⁵ N·m²",
                    "चरण 4: हर = 0.09 = 9 × 10⁻² m²",
                    "चरण 5: F = (54 × 10⁻⁵) / (9 × 10⁻²) = 6 × 10⁻³ N।"
                ),
                finalAnswer = "बल F = 6 × 10⁻³ N (प्रतिकर्षण बल, क्योंकि दोनों आवेश सजातीय/धनात्मक हैं)।"
            ),
            commonMistakes = listOf(
                "गलती 1: दूरी r को सेंटीमीटर (cm) से मीटर (m) में बदलना भूल जाना।",
                "गलती 2: माध्यम में रखने पर बल को K से गुणा कर देना (सही: बल K से विभाजित होता है, घटता है)।",
                "गलती 3: सदिश रूप में दिशा को ध्यान में न रखना।"
            ),
            mpBoardExamFormat = "एमपी बोर्ड परीक्षा प्रारूप (अंक 3 अथवा 5):\n• प्रश्न: 'कूलॉम का व्युत्क्रम वर्ग नियम लिखिए तथा इसकी सीमाएं बताइए।'\n• अंक योजना:\n  1. नियम का कथन (1 अंक)\n  2. गणितीय व्यंजक तथा स्थिरांक का मान व मात्रक (1 अंक)\n  3. नियम की सीमाएं (1 अंक): बिंदु आवेश होना चाहिए, आवेश स्थिर होने चाहिए, अत्यंत सूक्ष्म नाभिकीय दूरियों (< 10⁻¹⁵ m) पर लागू नहीं।",
            quickRevisionPoints = listOf(
                "कूलॉम बल एक केंद्रीय (Central) और संरक्षी (Conservative) बल है।",
                "यह न्यूटन के तीसरे गति नियम (क्रिया-प्रतिक्रिया) का पालन करता है: F₁₂ = -F₂₁।",
                "परावैद्युत माध्यम में बल घट जाता है: F_medium = F_air / K।",
                "धातुओं (चालकों) के लिए परावैद्युतांक K = ∞ होता है, अतः उनके अंदर आवेशों पर बल शून्य हो जाता है।"
            ),
            practiceQuestions = listOf(
                "प्रश्न 1: यदि दो आवेशों के बीच की दूरी आधी कर दी जाए, तो उनके बीच बल कितना गुना हो जाएगा? (उत्तर: 4 गुना)",
                "प्रश्न 2: निर्वात की विद्युतशीलता ε₀ का मान एवं विमीय सूत्र लिखिए।",
                "प्रश्न 3: जल का परावैद्युतांक 80 है। यदि दो आवेशों को वायु से जल में समान दूरी पर रख दिया जाए तो बल पर क्या प्रभाव पड़ेगा? (उत्तर: बल 80 गुना घट जाएगा)"
            ),
            jeeEnrichment = "[JEE संवर्धन] सदिश रूप: F⃗₁₂ = \\frac{1}{4\\pi\\varepsilon_0} \\frac{q_1 q_2}{r^3} \\vec{r}_{12}। आवेशों के चिह्न (+ अथवा -) रखने पर दिशा स्वतः सही प्राप्त होती है।"
        ),

        "top_6" to TopicNote(
            topicId = "top_6",
            titleHindi = "विद्युत क्षेत्र एवं तीव्रता (Electric Field)",
            titleEnglish = "Electric Field & Intensity",
            conceptSimpleHindi = "विद्युत क्षेत्र किसी आवेश के चारों ओर का वह 'प्रभाव क्षेत्र' है जिसमें कोई अन्य परीक्षण आवेश लाने पर वह स्थिर वैद्युत बल का अनुभव करता है। जैसे किसी राजा का एक राज्य क्षेत्र होता है जहाँ उसकी सत्ता चलती है, वैसे ही किसी आवेश के चारों ओर उसका विद्युत क्षेत्र होता है।",
            realLifeAnalogy = "मोबाइल नेटवर्क टॉवर के चारों ओर एक अदृश्य नेटवर्क क्षेत्र होता है। जैसे ही आपका फोन उस क्षेत्र में आता है, सिग्नल पकड़ लेता है। दूर जाने पर सिग्नल कमजोर हो जाता है। विद्युत क्षेत्र भी स्रोत आवेश के पास प्रबल और दूर जाने पर दुर्बल होता जाता है।",
            stepByStepExplanation = listOf(
                "1. माइकल फैराडे ने सबसे पहले विद्युत क्षेत्र की अवधारणा प्रस्तुत की थी।",
                "2. स्रोत आवेश (Source Charge, Q): वह आवेश जो विद्युत क्षेत्र उत्पन्न करता है।",
                "3. परीक्षण आवेश (Test Charge, q₀): एक अत्यंत सूक्ष्म धनात्मक आवेश जिसकी अपनी कोई उपस्थिति क्षेत्र को विचलित नहीं करती।",
                "4. विद्युत क्षेत्र की तीव्रता (E): विद्युत क्षेत्र के किसी बिंदु पर रखे एकांक धन परीक्षण आवेश पर लगने वाले स्थिर वैद्युत बल को उस बिंदु पर विद्युत क्षेत्र की तीव्रता कहते हैं।",
                "5. E = F / q₀ (सदिश राशि, दिशा धनावेश पर लगने वाले बल की दिशा में)।",
                "6. बिंदु आवेश Q से r दूरी पर विद्युत क्षेत्र: E = (1 / 4πε₀) · (Q / r²)।"
            ),
            importantDefinition = "विद्युत क्षेत्र की तीव्रता: विद्युत क्षेत्र में किसी बिंदु पर स्थित एकांक धन परीक्षण आवेश जितने बल का अनुभव करता है, उसे उस बिंदु पर विद्युत क्षेत्र की तीव्रता कहते हैं। इसे सदिश E⃗ द्वारा व्यक्त किया जाता है।",
            formulas = listOf(
                FormulaItem(
                    id = "f_efield_def",
                    nameHindi = "विद्युत क्षेत्र की तीव्रता की परिभाषा",
                    nameEnglish = "Electric Field Definition",
                    mathExpression = "\\vec{E} = \\lim_{q_0 \\to 0} \\frac{\\vec{F}}{q_0}",
                    symbolsMeaning = listOf(
                        "E = विद्युत क्षेत्र की तीव्रता (N/C अथवा V/m)",
                        "F = परीक्षण आवेश पर बल (Newton)",
                        "q₀ = धन परीक्षण आवेश (Coulomb)"
                    ),
                    siUnit = "न्यूटन/कूलॉम (N/C) अथवा वोल्ट/मीटर (V/m)",
                    conditions = "परीक्षण आवेश q₀ अत्यंत अल्प होना चाहिए ताकि वह स्रोत आवेश के मूल क्षेत्र को प्रभावित न करे।",
                    topicId = "top_6",
                    topicName = "विद्युत क्षेत्र"
                ),
                FormulaItem(
                    id = "f_efield_point",
                    nameHindi = "बिंदु आवेश के कारण विद्युत क्षेत्र",
                    nameEnglish = "Electric Field due to Point Charge",
                    mathExpression = "E = \\frac{1}{4\\pi\\varepsilon_0} \\frac{Q}{r^2}",
                    symbolsMeaning = listOf(
                        "Q = स्रोत आवेश (Coulomb)",
                        "r = आवेश से प्रेक्षण बिंदु की दूरी (Meter)"
                    ),
                    siUnit = "N/C",
                    conditions = "बिंदु आवेश के लिए लागू। E दूरी के वर्ग के व्युत्क्रमानुपाती (E ∝ 1/r²) होता है।",
                    topicId = "top_6",
                    topicName = "विद्युत क्षेत्र"
                )
            ),
            siUnitsAndDimensions = "• SI मात्रक: न्यूटन/कूलॉम (N/C) अथवा वोल्ट/मीटर (V/m)\n• विमीय सूत्र: [M¹ L¹ T⁻³ A⁻¹]",
            diagramId = "diagram_field_lines",
            diagramCaption = "विद्युत क्षेत्र तीव्रता: धनावेश से त्रिज्यीय रूप से बाहर की ओर, ऋणावेश की ओर अंदर की ओर।",
            solvedNumerical = NumericalExample(
                problemStatementHindi = "5.0 × 10⁻⁸ C के बिंदु आवेश से कितनी दूरी पर विद्युत क्षेत्र की तीव्रता 450 N/C होगी?",
                problemStatementEnglish = "At what distance from a point charge of 5.0 × 10⁻⁸ C will the electric field intensity be 450 N/C?",
                givenValues = listOf(
                    "Q = 5.0 × 10⁻⁸ C",
                    "E = 450 N/C",
                    "k = 9 × 10⁹ N·m²/C²"
                ),
                formulaUsed = "E = k · (Q / r²) ⇒ r = √(k · Q / E)",
                stepByStepSolution = listOf(
                    "चरण 1: सूत्र को r के लिए पुनर्व्यवस्थित करें: r² = (k · Q) / E।",
                    "चरण 2: मान रखें: r² = (9 × 10⁹ × 5.0 × 10⁻⁸) / 450",
                    "चरण 3: अंश = 450; अतः r² = 450 / 450 = 1",
                    "चरण 4: वर्गमूल लेने पर: r = √1 = 1 मीटर।"
                ),
                finalAnswer = "दूरी r = 1.0 मीटर (1 m)।"
            ),
            commonMistakes = listOf(
                "गलती 1: यह भूल जाना कि विद्युत क्षेत्र एक सदिश राशि है। दिशा का ध्यान न रखना।",
                "गलती 2: ऋणावेश पर बल की दिशा विद्युत क्षेत्र के विपरीत होती है (F⃗ = qE⃗, q ऋणात्मक होने पर विपरीत)।",
                "गलती 3: मात्रक में वोल्ट/मीटर और न्यूटन/कूलॉम को अलग समझना, दोनों सर्वसम (Equally Valid) हैं।"
            ),
            mpBoardExamFormat = "एमपी बोर्ड परीक्षा प्रारूप (अंक 2):\n• प्रश्न: 'विद्युत क्षेत्र की तीव्रता की परिभाषा एवं इसका मात्रक व विमीय सूत्र लिखिए।'\n• आदर्श उत्तर: परिभाषा (1 अंक) + मात्रक N/C (0.5 अंक) + विमीय सूत्र [MLT⁻³A⁻¹] (0.5 अंक)।",
            quickRevisionPoints = listOf(
                "विद्युत क्षेत्र सदिश राशि है।",
                "धनावेश पर बल विद्युत क्षेत्र की दिशा में तथा ऋणावेश पर विपरीत दिशा में लगता है।",
                "एकसमान विद्युत क्षेत्र में समांतर एवं समदूरस्थ रेखाएं होती हैं।",
                "किसी चालक के भीतर स्थिर विद्युत क्षेत्र सदैव शून्य होता है (E = 0)।"
            ),
            practiceQuestions = listOf(
                "प्रश्न 1: एक इलेक्ट्रॉन 10⁴ N/C के एकसमान विद्युत क्षेत्र में रखा है। उस पर कार्यरत विद्युत बल का मान ज्ञात कीजिए। (उत्तर: 1.6 × 10⁻¹⁵ N)",
                "प्रश्न 2: सिद्ध कीजिए कि N/C और V/m एक ही भौतिक राशि के मात्रक हैं।",
                "प्रश्न 3: क्या दो विद्युत क्षेत्र रेखाएँ एक-दूसरे को काट सकती हैं? क्यों नहीं?"
            ),
            jeeEnrichment = "[JEE संवर्धन] आवेश वितरण (Continuous Charge Distribution): रैखिक आवेश घनत्व λ = dq/dL, पृष्ठीय आवेश घनत्व σ = dq/dA, आयतन आवेश घनत्व ρ = dq/dV। समाकलन रूप E⃗ = ∫ (dq / 4πε₀r²) r̂।"
        ),

        "top_8" to TopicNote(
            topicId = "top_8",
            titleHindi = "विद्युत द्विध्रुव एवं द्विध्रुव आघूर्ण",
            titleEnglish = "Electric Dipole & Dipole Moment",
            conceptSimpleHindi = "विद्युत द्विध्रुव दो बराबर परिमाण के परंतु विपरीत प्रकृति के आवेशों (+q और -q) का एक ऐसा जोड़ा है जो एक-दूसरे से बहुत अल्प दूरी (2a) पर स्थित होते हैं। जैसे जल का अणु (H₂O) अथवा नमक का अणु (NaCl) प्राकृतिक रूप से विद्युत द्विध्रुव की तरह व्यवहार करते हैं।",
            realLifeAnalogy = "डंबल (Dumbbell) की कल्पना कीजिए जिसके दोनों सिरों पर एक जैसे वजन लगे हैं लेकिन एक धनात्मक है और दूसरा ऋणात्मक। माइक्रोवेव ओवन में भोजन गर्म होना जल के अणुओं के द्विध्रुव आघूर्ण पर ही आधारित है—विद्युत क्षेत्र उन्हें घुमाता है जिससे घर्षण से ऊष्मा उत्पन्न होती है।",
            stepByStepExplanation = listOf(
                "1. परिभाषा: जब दो समान परिमाण किंतु विजातीय बिंदु आवेश परस्पर अत्यंत अल्प दूरी पर स्थित हों, तो इस निकाय को 'विद्युत द्विध्रुव' कहते हैं।",
                "2. द्विध्रुव की लंबाई: दोनों आवेशों के बीच की दूरी को सुविधा के लिए '2a' (या 2l) माना जाता है ताकि केंद्र से गणना सरल रहे।",
                "3. विद्युत द्विध्रुव आघूर्ण (p⃗): किसी एक आवेश के परिमाण (q) तथा दोनों आवेशों के बीच की दूरी (2a) के गुणनफल को विद्युत द्विध्रुव आघूर्ण कहते हैं।",
                "4. सूत्र: p = q × 2a।",
                "5. यह एक सदिश राशि है। भौतिकी में इसकी दिशा सदैव ऋणावेश (-q) से धनावेश (+q) की ओर मानी जाती है।"
            ),
            importantDefinition = "विद्युत द्विध्रुव आघूर्ण: विद्युत द्विध्रुव के किसी एक आवेश के परिमाण तथा दोनों आवेशों के बीच की दूरी के गुणनफल को विद्युत द्विध्रुव आघूर्ण कहते हैं। यह सदिश राशि है जिसकी दिशा -q से +q की ओर होती है।",
            formulas = listOf(
                FormulaItem(
                    id = "f_dipole_moment",
                    nameHindi = "विद्युत द्विध्रुव आघूर्ण",
                    nameEnglish = "Electric Dipole Moment",
                    mathExpression = "\\vec{p} = q \\cdot (2\\vec{a})",
                    symbolsMeaning = listOf(
                        "p = विद्युत द्विध्रुव आघूर्ण (Coulomb·meter)",
                        "q = किसी एक आवेश का परिमाण (Coulomb)",
                        "2a = द्विध्रुव की प्रभावकारी लंबाई (Meter)"
                    ),
                    siUnit = "कूलॉम-मीटर (C·m)",
                    conditions = "लागू जब दोनों आवेश परिमाण में सर्वसम तथा प्रकृति में विपरीत हों।",
                    topicId = "top_8",
                    topicName = "विद्युत द्विध्रुव"
                )
            ),
            siUnitsAndDimensions = "• SI मात्रक: कूलॉम-मीटर (C·m)\n• अन्य व्यावहारिक मात्रक: डिबाई (Debye, 1 Debye = 3.33 × 10⁻³⁰ C·m)\n• विमीय सूत्र: [M⁰ L¹ T¹ A¹]",
            diagramId = "diagram_dipole",
            diagramCaption = "विद्युत द्विध्रुव: -q तथा +q आवेश, दूरी 2a, द्विध्रुव आघूर्ण सदिश p की दिशा -q से +q की ओर।",
            solvedNumerical = NumericalExample(
                problemStatementHindi = "दो बिंदु आवेश +4 μC तथा -4 μC एक-दूसरे से 2 mm की दूरी पर स्थित हैं। इस विद्युत द्विध्रुव का द्विध्रुव आघूर्ण ज्ञात कीजिए।",
                problemStatementEnglish = "Two point charges +4 μC and -4 μC are placed 2 mm apart. Calculate the electric dipole moment of this dipole.",
                givenValues = listOf(
                    "q = 4 μC = 4 × 10⁻⁶ C",
                    "2a = 2 mm = 2 × 10⁻³ m"
                ),
                formulaUsed = "p = q × (2a)",
                stepByStepSolution = listOf(
                    "चरण 1: आवेश को कूलॉम में तथा दूरी 2a को मीटर में रखें।",
                    "ध्यान दें: 2a पूरी दूरी है, इसे 2 से गुणा न करें!",
                    "चरण 2: p = (4 × 10⁻⁶ C) × (2 × 10⁻³ m)",
                    "चरण 3: p = 8 × 10⁻⁹ C·m।"
                ),
                finalAnswer = "द्विध्रुव आघूर्ण p = 8.0 × 10⁻⁹ C·m (ऋणावेश से धनावेश की ओर)।"
            ),
            commonMistakes = listOf(
                "अति सामान्य गलती: दूरी '2a' दी होने पर छात्र अक्सर उसे पुनः 2 से गुणा कर देते हैं। 2a स्वयं कुल दूरी है।",
                "दिशा की गलती: रसायन विज्ञान में द्विध्रुव की दिशा + से - मानी जाती है, परंतु भौतिक विज्ञान (MP Board) में दिशा सदैव ऋणावेश (-q) से धनावेश (+q) की ओर होती है।"
            ),
            mpBoardExamFormat = "एमपी बोर्ड परीक्षा प्रारूप (अंक 2):\n• प्रश्न: 'विद्युत द्विध्रुव आघूर्ण को परिभाषित कीजिए तथा इसका मात्रक व दिशा लिखिए।'\n• अंक: परिभाषा (1 अंक) + मात्रक C·m (0.5 अंक) + दिशा -q से +q (0.5 अंक)।",
            quickRevisionPoints = listOf(
                "द्विध्रुव पर कुल आवेश सदैव शून्य होता है (q_total = +q - q = 0)।",
                "परंतु द्विध्रुव का विद्युत क्षेत्र शून्य नहीं होता, क्योंकि दोनों आवेश अलग-अलग बिंदुओं पर स्थित हैं।",
                "दिशा: ऋणावेश से धनावेश की ओर।",
                "उदाहरण: HCl, H₂O, NH₃ के अणु।"
            ),
            practiceQuestions = listOf(
                "प्रश्न 1: किसी विद्युत द्विध्रुव पर कुल नेट आवेश कितना होता है?",
                "प्रश्न 2: जल का अणु एक ध्रुवीय अणु क्यों है?",
                "प्रश्न 3: आदर्श विद्युत द्विध्रुव (Point Dipole) की क्या शर्त है? (q → ∞ तथा 2a → 0, p = नियत)"
            ),
            jeeEnrichment = "[JEE संवर्धन] बिंदु द्विध्रुव की शर्त: जब 2a ≪ r हो। उच्च दूरी पर अक्षीय क्षेत्र E_axial = 2kp/r³ तथा निरक्षीय क्षेत्र E_equatorial = kp/r³। E ∝ 1/r³ (बिंदु आवेश 1/r² से तीव्र ह्रास)।"
        ),

        "top_11" to TopicNote(
            topicId = "top_11",
            titleHindi = "विद्युत फ्लक्स (Electric Flux)",
            titleEnglish = "Electric Flux",
            conceptSimpleHindi = "विद्युत फ्लक्स किसी काल्पनिक या वास्तविक पृष्ठ से लंबवत गुजरने वाली विद्युत क्षेत्र रेखाओं की कुल संख्या की माप है। जितनी अधिक बल रेखाएँ किसी सतह को पार करेंगी, उस सतह से बद्ध विद्युत फ्लक्स उतना ही अधिक होगा।",
            realLifeAnalogy = "बारिश में खिड़की की कल्पना कीजिए। यदि खिड़की हवा और बारिश की दिशा के बिल्कुल लंबवत (सामने) खुली है, तो अधिकतम पानी अंदर आएगा। यदि खिड़की को तिरछा कर दिया जाए, तो कम पानी आएगा; और यदि खिड़की बारिश के समांतर हो जाए, तो एक भी बूंद अंदर नहीं आएगी। यही विद्युत फ्लक्स की संकल्पना है!",
            stepByStepExplanation = listOf(
                "1. क्षेत्रफल सदिश (Area Vector, dS⃗): किसी समतल क्षेत्रफल का परिमाण उसके क्षेत्रफल के बराबर होता है तथा उसकी दिशा तल पर खींचे गए बाह्य अभिलंब (Outward Normal) की ओर होती है।",
                "2. यदि एकसमान विद्युत क्षेत्र E⃗ में कोई समतल क्षेत्रफल A रखा हो और क्षेत्र तथा तल के अभिलंब के बीच कोण θ हो, तो फ्लक्स Φ = E · A · cos θ = E⃗ · A⃗।",
                "3. यदि θ = 0° (क्षेत्र तल के लंबवत हो): Φ_max = E · A (अधिकतम फ्लक्स)।",
                "4. यदि θ = 90° (क्षेत्र तल के समांतर हो): Φ = 0 (शून्य फ्लक्स)।",
                "5. बंद पृष्ठ के लिए: Φ = ∮ E⃗ · dS⃗।"
            ),
            importantDefinition = "विद्युत फ्लक्स: विद्युत क्षेत्र में स्थित किसी पृष्ठ से लंबवत गुजरने वाली कुल विद्युत क्षेत्र रेखाओं की संख्या को उस पृष्ठ से बद्ध विद्युत फ्लक्स कहते हैं। यह एक अदिश राशि है।",
            formulas = listOf(
                FormulaItem(
                    id = "f_flux_dot",
                    nameHindi = "विद्युत फ्लक्स का अदिश गुणनफल सूत्र",
                    nameEnglish = "Electric Flux Dot Product Formula",
                    mathExpression = "\\Phi_E = \\vec{E} \\cdot \\vec{A} = E \\cdot A \\cdot \\cos\\theta",
                    symbolsMeaning = listOf(
                        "Φ_E = विद्युत फ्लक्स (N·m²/C अथवा Volt·meter)",
                        "E = विद्युत क्षेत्र की तीव्रता (N/C)",
                        "A = पृष्ठ का क्षेत्रफल (m²)",
                        "θ = विद्युत क्षेत्र सदिश E⃗ तथा क्षेत्रफल सदिश A⃗ (अभिलंब) के बीच का कोण"
                    ),
                    siUnit = "N·m²/C अथवा V·m (वोल्ट-मीटर)",
                    conditions = "एकसमान विद्युत क्षेत्र तथा समतल पृष्ठ के लिए।",
                    topicId = "top_11",
                    topicName = "विद्युत फ्लक्स"
                )
            ),
            siUnitsAndDimensions = "• SI मात्रक: N·m²/C अथवा V·m (वोल्ट-मीटर)\n• विमीय सूत्र: [M¹ L³ T⁻³ A⁻¹]",
            diagramId = "diagram_flux",
            diagramCaption = "विद्युत फ्लक्स: क्षेत्रफल सदिश A⃗ (लंबवत) तथा क्षेत्र E⃗ के बीच कोण θ। Φ = EA cos θ।",
            solvedNumerical = NumericalExample(
                problemStatementHindi = "एक समान विद्युत क्षेत्र E⃗ = 3 × 10³ î N/C में 10 cm भुजा का एक वर्गाकार पृष्ठ Y-Z तल के समांतर रखा गया है। इस पृष्ठ से गुजरने वाला विद्युत फ्लक्स ज्ञात कीजिए।",
                problemStatementEnglish = "A square of side 10 cm is placed in Y-Z plane in a uniform electric field E = 3 × 10³ î N/C. Find the electric flux through it.",
                givenValues = listOf(
                    "E⃗ = 3 × 10³ î N/C",
                    "भुजा a = 10 cm = 0.1 m",
                    "क्षेत्रफल A = (0.1)² = 0.01 m²",
                    "Y-Z तल का अभिलंब X-अक्ष के अनुदिश होता है: A⃗ = 0.01 î m²"
                ),
                formulaUsed = "Φ = E⃗ · A⃗ = E · A · cos(0°)",
                stepByStepSolution = listOf(
                    "चरण 1: Y-Z तल के लंबवत सदिश X-अक्ष (î दिशा) में होता है, अतः क्षेत्रफल सदिश A⃗ = 0.01 î m²।",
                    "चरण 2: विद्युत क्षेत्र E⃗ और A⃗ दोनों एक ही दिशा में हैं, अतः θ = 0°।",
                    "चरण 3: Φ = (3 × 10³ î) · (0.01 î) = 3 × 10³ × 0.01 = 30 N·m²/C।"
                ),
                finalAnswer = "विद्युत फ्लक्स Φ = 30 N·m²/C (अथवा 30 V·m)।"
            ),
            commonMistakes = listOf(
                "कोण की गलती: जब प्रश्न कहे 'पृष्ठ क्षेत्र के समांतर है', तो क्षेत्रफल सदिश अभिलंब होने के कारण θ = 90° होता है (Φ = 0)। छात्र θ = 0° मान बैठते हैं।",
                "प्रवेशी फ्लक्स ऋणात्मक तथा निर्गत फ्लक्स धनात्मक होता है।"
            ),
            mpBoardExamFormat = "एमपी बोर्ड परीक्षा प्रारूप (अंक 2):\n• प्रश्न: 'विद्युत फ्लक्स किसे कहते हैं? इसका SI मात्रक लिखिए।'\n• आदर्श उत्तर: परिभाषा (1 अंक) + SI मात्रक N·m²/C अथवा V·m (1 अंक)।",
            quickRevisionPoints = listOf(
                "विद्युत फ्लक्स एक अदिश (Scalar) राशि है।",
                "धनात्मक फ्लक्स: जब बल रेखाएं पृष्ठ से बाहर निकलती हैं।",
                "ऋणात्मक फ्लक्स: जब बल रेखाएं पृष्ठ में प्रवेश करती हैं।",
                "शून्य फ्लक्स: जब जितनी रेखाएं अंदर आती हैं, उतनी ही बाहर निकल जाती हैं।"
            ),
            practiceQuestions = listOf(
                "प्रश्न 1: किस स्थिति में किसी समतल पृष्ठ से बद्ध विद्युत फ्लक्स शून्य होता है?",
                "प्रश्न 2: यदि किसी बंद पृष्ठ के अंदर नेट आवेश शून्य हो, तो उससे गुजरने वाला कुल फ्लक्स कितना होगा?",
                "प्रश्न 3: विद्युत फ्लक्स का विमीय सूत्र व्युत्पन्न कीजिए।"
            ),
            jeeEnrichment = "[JEE संवर्धन] घन (Cube) के कोने पर रखे आवेश q के कारण घन से कुल फ्लक्स: घन केवल 1/8 भाग को आच्छादित करता है, अतः Φ_total = q / (8ε₀)।"
        ),

        "top_12" to TopicNote(
            topicId = "top_12",
            titleHindi = "गाउस का नियम और उसके अनुप्रयोग",
            titleEnglish = "Gauss's Law & Applications",
            conceptSimpleHindi = "गाउस का नियम स्थिर वैद्युतिकी के सबसे शक्तिशाली नियमों में से एक है। यह बताता है कि किसी भी बंद काल्पनिक पृष्ठ (गाउसीय पृष्ठ) से गुजरने वाला कुल विद्युत फ्लक्स केवल उस पृष्ठ के भीतर बंद कुल आवेश पर निर्भर करता है, पृष्ठ के आकार या आकृति पर नहीं!",
            realLifeAnalogy = "एक बंद गुब्बारे या डिब्बे की कल्पना कीजिए जिसके अंदर एक जलती हुई मोमबत्ती (आवेश) रखी है। डिब्बे की सतह से बाहर निकलने वाली कुल रोशनी की किरणें केवल इस बात पर निर्भर करेंगी कि अंदर कितनी मोमबत्तियाँ रखी हैं, न कि इस बात पर कि डिब्बा गोल है, चौकोर है या टेढ़ा-मेढ़ा।",
            stepByStepExplanation = listOf(
                "1. जर्मन गणितज्ञ व भौतिकशास्त्री कार्ल फ्रेडरिक गाउस ने 1835 में यह नियम प्रस्तुत किया।",
                "2. कथन: किसी बंद पृष्ठ (Closed Surface) से गुजरने वाला कुल विद्युत फ्लक्स उस पृष्ठ द्वारा परिबद्ध कुल नेट आवेश (Σq) का 1/ε₀ गुना होता है।",
                "3. समाकलन रूप: Φ = ∮ E⃗ · dS⃗ = (q_enclosed) / ε₀।",
                "4. गाउसीय पृष्ठ (Gaussian Surface): आवेश वितरण के चारों ओर खींचा गया एक काल्पनिक बंद पृष्ठ जो सममित (Symmetric) हो।",
                "5. मुख्य अनुप्रयोग 1: अनंत लंबाई के एकसमान आवेशित सीधे तार के कारण विद्युत क्षेत्र: E = λ / (2πε₀r)।",
                "6. मुख्य अनुप्रयोग 2: अनंत विस्तार की समतल आवेशित अचालक चादर के कारण विद्युत क्षेत्र: E = σ / (2ε₀)।",
                "7. मुख्य अनुप्रयोग 3: एकसमान आवेशित पतले गोलीय खोल (Shell) के कारण विद्युत क्षेत्र: बाहर E = q / (4πε₀r²), सतह पर E = q / (4πε₀R²), तथा भीतर E = 0।"
            ),
            importantDefinition = "गाउस की प्रमेय (Gauss's Law): निर्वात अथवा वायु में किसी बंद पृष्ठ से गुजरने वाला संपूर्ण विद्युत फ्लक्स उस बंद पृष्ठ के अंदर उपस्थित कुल आवेश का 1/ε₀ गुना होता है। गणितीय रूप में: Φ = ∮ E⃗ · dS⃗ = q / ε₀।",
            formulas = listOf(
                FormulaItem(
                    id = "f_gauss_law",
                    nameHindi = "गाउस की प्रमेय का मूलभूत सूत्र",
                    nameEnglish = "Gauss's Law Fundamental Formula",
                    mathExpression = "\\oint \\vec{E} \\cdot d\\vec{S} = \\frac{q_{\\text{enclosed}}}{\\varepsilon_0}",
                    symbolsMeaning = listOf(
                        "Φ = बंद पृष्ठ से कुल विद्युत फ्लक्स",
                        "E⃗ = पृष्ठ पर विद्युत क्षेत्र",
                        "dS⃗ = अल्पांश क्षेत्रफल सदिश",
                        "q_enclosed = पृष्ठ के भीतर परिबद्ध कुल नेट आवेश",
                        "ε₀ = निर्वात की विद्युतशीलता = 8.854 × 10⁻¹² C²/N·m²"
                    ),
                    siUnit = "N·m²/C अथवा V·m",
                    conditions = "पृष्ठ पूर्णतः बंद (Closed) होना चाहिए। पृष्ठ के बाहर स्थित आवेशों का कुल फ्लक्स में कोई योगदान नहीं होता।",
                    topicId = "top_12",
                    topicName = "गाउस का नियम"
                ),
                FormulaItem(
                    id = "f_wire_field",
                    nameHindi = "आवेशित लंबे सीधे तार के कारण विद्युत क्षेत्र",
                    nameEnglish = "Electric Field due to Infinitely Long Charged Wire",
                    mathExpression = "E = \\frac{\\lambda}{2\\pi\\varepsilon_0 r}",
                    symbolsMeaning = listOf(
                        "E = विद्युत क्षेत्र की तीव्रता (N/C)",
                        "λ = रैखिक आवेश घनत्व (Linear Charge Density, C/m)",
                        "r = तार से लंबवत दूरी (Meter)"
                    ),
                    siUnit = "N/C",
                    conditions = "अनंत लंबाई का सीधा समरूप आवेशित चालक तार। बेलनाकार गाउसीय पृष्ठ।",
                    topicId = "top_12",
                    topicName = "गाउस का नियम"
                ),
                FormulaItem(
                    id = "f_sheet_field",
                    nameHindi = "आवेशित अचालक चादर के कारण विद्युत क्षेत्र",
                    nameEnglish = "Electric Field due to Infinite Non-conducting Sheet",
                    mathExpression = "E = \\frac{\\sigma}{2\\varepsilon_0}",
                    symbolsMeaning = listOf(
                        "σ = पृष्ठीय आवेश घनत्व (Surface Charge Density, C/m²)",
                        "ε₀ = निर्वात की विद्युतशीलता"
                    ),
                    siUnit = "N/C",
                    conditions = "अनंत विस्तार की पतली समतल अचालक चादर। ध्यान दें: यह दूरी r पर निर्भर नहीं करता!",
                    topicId = "top_12",
                    topicName = "गाउस का नियम"
                )
            ),
            siUnitsAndDimensions = "• फ्लक्स का मात्रक: N·m²/C\n• रैखिक आवेश घनत्व λ का मात्रक: कूलॉम/मीटर (C/m), विमा: [L⁻¹ T¹ A¹]\n• पृष्ठीय आवेश घनत्व σ का मात्रक: कूलॉम/मीटर² (C/m²), विमा: [L⁻² T¹ A¹]",
            diagramId = "diagram_gauss_law",
            diagramCaption = "गाउसीय पृष्ठ: परिबद्ध आवेश q, फ्लक्स रेखाएं Φ = ∮ E·dA = q / ε₀।",
            solvedNumerical = NumericalExample(
                problemStatementHindi = "8.854 μC का एक बिंदु आवेश किसी 10 cm किनारे वाले घन के केंद्र पर स्थित है। घन के किसी एक फलक (One Face) से गुजरने वाला विद्युत फ्लक्स कितना होगा?",
                problemStatementEnglish = "A point charge of 8.854 μC is at the center of a cube of edge 10 cm. Find the electric flux through any one face of the cube.",
                givenValues = listOf(
                    "q = 8.854 μC = 8.854 × 10⁻⁶ C",
                    "ε₀ = 8.854 × 10⁻¹² C²/N·m²",
                    "घन में कुल 6 फलक (Faces) होते हैं।"
                ),
                formulaUsed = "गाउस के नियम से कुल फ्लक्स Φ_total = q / ε₀; एक फलक से Φ_face = Φ_total / 6",
                stepByStepSolution = listOf(
                    "चरण 1: गाउस के नियम से घन से कुल निकलने वाला फ्लक्स:",
                    "Φ_total = (8.854 × 10⁻⁶ C) / (8.854 × 10⁻¹² C²/N·m²) = 1.0 × 10⁶ N·m²/C।",
                    "चरण 2: सममिति (Symmetry) के कारण फ्लक्स सभी 6 फलकों में समान रूप से विभाजित होगा।",
                    "चरण 3: एक फलक से फ्लक्स Φ_face = (1.0 × 10⁶) / 6 ≈ 1.67 × 10⁵ N·m²/C।"
                ),
                finalAnswer = "एक फलक से फ्लक्स = 1.67 × 10⁵ N·m²/C (घन के आकार/भुजा पर निर्भर नहीं करता)।"
            ),
            commonMistakes = listOf(
                "गलती 1: चादर वाले सूत्र में चादर से दूरी r का उपयोग करना। चादर का क्षेत्र E = σ/(2ε₀) दूरी पर निर्भर नहीं करता।",
                "गलती 2: पृष्ठ के बाहर स्थित आवेशों को भी q_enclosed में जोड़ देना। केवल अंदर का आवेश लिया जाता है।",
                "गलती 3: आवेशित गोलीय कोश के भीतर विद्युत क्षेत्र को अशून्य मानना। कोश के भीतर E = 0 होता है।"
            ),
            mpBoardExamFormat = "एमपी बोर्ड परीक्षा प्रारूप (अंक 5 - दीर्घ उत्तरीय प्रश्न):\n• प्रश्न: 'गाउस की प्रमेय लिखिए एवं सिद्ध कीजिए' अथवा 'गाउस की प्रमेय की सहायता से सीधे लंबे आवेशित तार के कारण विद्युत क्षेत्र की तीव्रता का व्यंजक व्युत्पन्न कीजिए।'\n• अंक विभाजन:\n  1. प्रमेय का कथन (1 अंक)\n  2. स्वच्छ नामांकित रेखाचित्र (1 अंक)\n  3. गणितीय निगमन/व्युत्पत्ति (2.5 अंक)\n  4. अंतिम सूत्र एवं विशेष स्थितियाँ (0.5 अंक)।",
            quickRevisionPoints = listOf(
                "गाउस की प्रमेय केवल बंद पृष्ठों के लिए ही लागू होती है।",
                "कुल फ्लक्स पृष्ठ के आकार, आकृति अथवा आवेश के स्थान पर निर्भर नहीं करता।",
                "यदि पृष्ठ के अंदर नेट आवेश शून्य है, तो कुल फ्लक्स भी शून्य होगा।",
                "गाउस का नियम कूलॉम के नियम के समतुल्य है और व्युत्क्रम वर्ग नियम पर आधारित है।"
            ),
            practiceQuestions = listOf(
                "प्रश्न 1: गाउस के नियम से कूलॉम के नियम का निगमन कीजिए। (एमपी बोर्ड महत्वपूर्ण प्रश्न)",
                "प्रश्न 2: आवेशित खोखले चालक गोले के भीतर विद्युत क्षेत्र कितना होता है? (उत्तर: शून्य)",
                "प्रश्न 3: किसी बंद पृष्ठ में प्रवेश करने वाला फ्लक्स 400 N·m²/C तथा निकलने वाला फ्लक्स 800 N·m²/C है। पृष्ठ के भीतर नेट आवेश ज्ञात कीजिए।"
            ),
            jeeEnrichment = "[JEE संवर्धन] ठोस अचालक गोले (Uniformly Charged Solid Sphere) के अंदर विद्युत क्षेत्र: E = (ρ · r) / (3ε₀) = (Q · r) / (4πε₀R³)। गोले के केंद्र पर E = 0 तथा केंद्र से सतह तक E ∝ r (रैखिक वृद्धि)!"
        )
    )

    fun getTopicNote(topicId: String): TopicNote? {
        // Return existing note or generate a rich standard note structure for other syllabus topics
        topicNotes[topicId]?.let { return it }

        val topic = topics.find { it.id == topicId } ?: return null
        return TopicNote(
            topicId = topic.id,
            titleHindi = topic.titleHindi,
            titleEnglish = topic.titleEnglish,
            conceptSimpleHindi = "${topic.titleHindi} स्थिर वैद्युतिकी (Electrostatics) का एक अनिवार्य संप्रत्यय है। एमपी बोर्ड पाठ्यक्रम के अनुसार यह विषय आवेशों की अंतःक्रिया और उनके व्यवहार को समझने के लिए आधारशिला है।",
            realLifeAnalogy = "दैनिक जीवन में जब दो वस्तुएं संपर्क में आती हैं और अलग होती हैं, तो इस विषय के सिद्धांत सूक्ष्म कणों के स्तर पर कार्य करते हैं।",
            stepByStepExplanation = listOf(
                "1. संप्रत्यय की मूलभूत व्याख्या: पदार्थ में आवेशों का वितरण और उनका प्रभाव।",
                "2. गणितीय एवं भौतिक विश्लेषण: भौतिक राशियों के पारस्परिक संबंध।",
                "3. बोर्ड परीक्षा में पूछे जाने वाले मुख्य बिंदु एवं अवधारणात्मक अनुप्रयोग।"
            ),
            importantDefinition = "${topic.titleHindi}: स्थिर वैद्युतिकी का वह संप्रत्यय जो निकाय के विद्युत व्यवहार को निर्धारित करता है।",
            formulas = listOf(
                FormulaItem(
                    id = "f_gen_${topic.id}",
                    nameHindi = "${topic.titleHindi} से संबंधित सूत्र",
                    nameEnglish = "${topic.titleEnglish} Formula",
                    mathExpression = "E = f(q, r)",
                    symbolsMeaning = listOf("q = आवेश", "r = दूरी"),
                    siUnit = "SI मानक मात्रक",
                    conditions = "स्थिर विद्युत परिस्थितियों के लिए।",
                    topicId = topic.id,
                    topicName = topic.titleHindi
                )
            ),
            siUnitsAndDimensions = "• SI मात्रक: मानक अंतरराष्ट्रीय मात्रक\n• विमीय सूत्र: विमीय समीकरणों पर आधारित",
            diagramId = "diagram_point_charges",
            diagramCaption = "संबंधित संकल्पना का योजनाबद्ध आरेख।",
            solvedNumerical = NumericalExample(
                problemStatementHindi = "${topic.titleHindi} पर आधारित आदर्श बोर्ड स्तर का आंकिक प्रश्न।",
                givenValues = listOf("मानक मान"),
                formulaUsed = "मानक सूत्र",
                stepByStepSolution = listOf("चरण 1: दत्त मान लिखें", "चरण 2: सूत्र में प्रतिस्थापन", "चरण 3: गणना"),
                finalAnswer = "हल सहित उत्तर।"
            ),
            commonMistakes = listOf(
                "सामान्यतः छात्र मात्रक लिखना भूल जाते हैं।",
                "सदिश राशियों के प्रश्नों में दिशा का उल्लेख न करना।"
            ),
            mpBoardExamFormat = "एमपी बोर्ड परीक्षा प्रारूप (अंक 2/3):\n• स्पष्ट परिभाषा, सूत्र एवं मात्रक लिखने पर पूर्ण अंक प्राप्त होते हैं।",
            quickRevisionPoints = listOf(
                "${topic.titleHindi} का सारांश बिंदु।",
                "मुख्य सूत्र और उसके अनुप्रयोग।"
            ),
            practiceQuestions = listOf(
                "प्रश्न 1: ${topic.titleHindi} की परिभाषा लिखिए।",
                "प्रश्न 2: इसका एक व्यावहारिक उदाहरण दीजिए।"
            ),
            jeeEnrichment = "[JEE संवर्धन] प्रतियोगी परीक्षाओं के लिए उन्नत अनुप्रयोग एवं विशेष स्थितियाँ।"
        )
    }
}
