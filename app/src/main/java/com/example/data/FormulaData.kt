package com.example.data

import com.example.model.FormulaItem

object FormulaData {

    val allFormulas: List<FormulaItem> = listOf(
        FormulaItem(
            id = "f_quantization",
            nameHindi = "आवेश का क्वांटीकरण (Quantization of Charge)",
            nameEnglish = "Quantization of Charge",
            mathExpression = "q = ± n · e",
            symbolsMeaning = listOf(
                "q = कुल आवेश (C)",
                "n = इलेक्ट्रॉनों की संख्या (पूर्ण संख्या: 1, 2, 3...)",
                "e = मूल आवेश (1.6 × 10⁻¹⁹ C)"
            ),
            siUnit = "कूलॉम (Coulomb - C)",
            conditions = "सूक्ष्म एवं स्थूल स्तर पर आवेश का आदान-प्रदान सदैव 'e' के पूर्ण गुणजों में होता है।",
            topicId = "top_1",
            topicName = "विद्युत आवेश",
            workedExample = "1 C ऋणावेश में इलेक्ट्रॉन संख्या: n = 1 / (1.6 × 10⁻¹⁹) = 6.25 × 10¹⁸।"
        ),
        FormulaItem(
            id = "f_current_charge",
            nameHindi = "धारा एवं आवेश संबंध (Electric Current)",
            nameEnglish = "Current and Charge Relation",
            mathExpression = "q = I · t",
            symbolsMeaning = listOf(
                "q = प्रवाहित कुल आवेश (C)",
                "I = विद्युत धारा (A)",
                "t = समय (s)"
            ),
            siUnit = "A·s अथवा C",
            conditions = "जब धारा का मान समय के साथ नियत रहे।",
            topicId = "top_1",
            topicName = "विद्युत आवेश"
        ),
        FormulaItem(
            id = "f_coulomb_vacuum",
            nameHindi = "कूलॉम का नियम - निर्वात में (Coulomb's Law in Air/Vacuum)",
            nameEnglish = "Coulomb's Law in Vacuum",
            mathExpression = "F = \\frac{1}{4\\pi\\varepsilon_0} \\frac{|q_1 q_2|}{r^2}",
            symbolsMeaning = listOf(
                "F = स्थिर वैद्युत बल (N)",
                "q₁, q₂ = बिंदु आवेशों के परिमाण (C)",
                "r = आवेशों के बीच की दूरी (m)",
                "ε₀ = 8.854 × 10⁻¹² C²/N·m²",
                "1/(4πε₀) = 9 × 10⁹ N·m²/C²"
            ),
            siUnit = "न्यूटन (N)",
            conditions = "स्थिर एवं बिंदु आवेशों के लिए। r > 10⁻¹⁵ m (नाभिकीय सीमा से अधिक)।",
            topicId = "top_4",
            topicName = "कूलॉम का नियम",
            workedExample = "दो 1 C आवेश 1 m दूरी पर हों, तो F = 9 × 10⁹ N।"
        ),
        FormulaItem(
            id = "f_coulomb_dielectric",
            nameHindi = "परावैद्युत माध्यम में कूलॉम बल (Dielectric Medium)",
            nameEnglish = "Coulomb's Law in Medium",
            mathExpression = "F_m = \\frac{F_{air}}{K} = \\frac{1}{4\\pi K \\varepsilon_0} \\frac{|q_1 q_2|}{r^2}",
            symbolsMeaning = listOf(
                "F_m = माध्यम में बल (N)",
                "F_{air} = वायु में बल (N)",
                "K = माध्यम का परावैद्युतांक (Dielectric Constant)"
            ),
            siUnit = "न्यूटन (N)",
            conditions = "माध्यम समांगी एवं समदैशिक होना चाहिए। धातुओं के लिए K = ∞ (F_m = 0)।",
            topicId = "top_4",
            topicName = "कूलॉम का नियम"
        ),
        FormulaItem(
            id = "f_electric_field_point",
            nameHindi = "बिंदु आवेश का विद्युत क्षेत्र (Electric Field Intensity)",
            nameEnglish = "Electric Field of Point Charge",
            mathExpression = "E = \\frac{1}{4\\pi\\varepsilon_0} \\frac{Q}{r^2}",
            symbolsMeaning = listOf(
                "E = विद्युत क्षेत्र तीव्रता (N/C)",
                "Q = स्रोत आवेश (C)",
                "r = प्रेक्षण बिंदु की दूरी (m)"
            ),
            siUnit = "N/C अथवा V/m",
            conditions = "बिंदु आवेश के लिए लागू। E व्युत्क्रमानुपाती r² (E ∝ 1/r²)।",
            topicId = "top_6",
            topicName = "विद्युत क्षेत्र"
        ),
        FormulaItem(
            id = "f_dipole_moment",
            nameHindi = "विद्युत द्विध्रुव आघूर्ण (Electric Dipole Moment)",
            nameEnglish = "Electric Dipole Moment",
            mathExpression = "\\vec{p} = q \\cdot (2\\vec{a})",
            symbolsMeaning = listOf(
                "p = द्विध्रुव आघूर्ण (C·m)",
                "q = किसी एक आवेश का परिमाण (C)",
                "2a = दोनों आवेशों के बीच की दूरी (m)"
            ),
            siUnit = "कूलॉम-मीटर (C·m)",
            conditions = "सदिश राशि। दिशा ऋणावेश (-q) से धनावेश (+q) की ओर।",
            topicId = "top_8",
            topicName = "विद्युत द्विध्रुव"
        ),
        FormulaItem(
            id = "f_dipole_axial",
            nameHindi = "द्विध्रुव की अक्षीय स्थिति में विद्युत क्षेत्र (End-on / Axial Position)",
            nameEnglish = "Axial Field of Dipole",
            mathExpression = "E_{axial} = \\frac{1}{4\\pi\\varepsilon_0} \\frac{2pr}{(r^2 - a^2)^2} \\approx \\frac{1}{4\\pi\\varepsilon_0} \\frac{2p}{r^3}",
            symbolsMeaning = listOf(
                "E_{axial} = अक्षीय स्थिति में क्षेत्र (N/C)",
                "p = द्विध्रुव आघूर्ण (C·m)",
                "r = केंद्र से प्रेक्षण बिंदु की दूरी (m)",
                "2a = द्विध्रुव की लंबाई"
            ),
            siUnit = "N/C अथवा V/m",
            conditions = "लघु द्विध्रुव के लिए r ≫ a मान्य। E दिशा p⃗ के अनुदिश। E ∝ 1/r³।",
            topicId = "top_9",
            topicName = "द्विध्रुव का विद्युत क्षेत्र"
        ),
        FormulaItem(
            id = "f_dipole_equatorial",
            nameHindi = "द्विध्रुव की निरक्षीय स्थिति में विद्युत क्षेत्र (Broadside / Equatorial Position)",
            nameEnglish = "Equatorial Field of Dipole",
            mathExpression = "E_{eq} = \\frac{1}{4\\pi\\varepsilon_0} \\frac{p}{(r^2 + a^2)^{3/2}} \\approx \\frac{1}{4\\pi\\varepsilon_0} \\frac{p}{r^3}",
            symbolsMeaning = listOf(
                "E_{eq} = निरक्षीय स्थिति में क्षेत्र (N/C)",
                "संबंध: E_{axial} = 2 · E_{equatorial} (समान दूरी r के लिए)"
            ),
            siUnit = "N/C",
            conditions = "दूरी r ≫ a के लिए। दिशा p⃗ के विपरीत (धनावेश से ऋणावेश की ओर)।",
            topicId = "top_9",
            topicName = "द्विध्रुव का विद्युत क्षेत्र"
        ),
        FormulaItem(
            id = "f_torque_dipole",
            nameHindi = "एकसमान विद्युत क्षेत्र में द्विध्रुव पर बल आघूर्ण (Torque on Dipole)",
            nameEnglish = "Torque on Dipole in Uniform Field",
            mathExpression = "\\vec{\\tau} = \\vec{p} \\times \\vec{E} \\implies \\tau = p \\cdot E \\cdot \\sin\\theta",
            symbolsMeaning = listOf(
                "τ = बल आघूर्ण (N·m)",
                "p = द्विध्रुव आघूर्ण (C·m)",
                "E = विद्युत क्षेत्र (N/C)",
                "θ = p⃗ तथा E⃗ के मध्य कोण"
            ),
            siUnit = "न्यूटन-मीटर (N·m)",
            conditions = "θ = 90° पर अधिकतम τ_max = pE। θ = 0° (स्थाई साम्य) एवं 180° (अस्थाई साम्य) पर τ = 0।",
            topicId = "top_10",
            topicName = "द्विध्रुव पर बल आघूर्ण"
        ),
        FormulaItem(
            id = "f_dipole_potential_energy",
            nameHindi = "विद्युत द्विध्रुव की स्थितिज ऊर्जा (Potential Energy of Dipole)",
            nameEnglish = "Potential Energy of Dipole",
            mathExpression = "U = -\\vec{p} \\cdot \\vec{E} = -p \\cdot E \\cdot \\cos\\theta",
            symbolsMeaning = listOf(
                "U = स्थितिज ऊर्जा (J)",
                "θ = 0° पर न्यूनतम ऊर्जा U = -pE (स्थाई संतुलन)",
                "θ = 180° पर अधिकतम ऊर्जा U = +pE (अस्थाई संतुलन)"
            ),
            siUnit = "जूल (J)",
            conditions = "मानक स्थिति θ = 90° पर स्थितिज ऊर्जा शून्य मानी जाती है।",
            topicId = "top_10",
            topicName = "द्विध्रुव पर बल आघूर्ण"
        ),
        FormulaItem(
            id = "f_electric_flux",
            nameHindi = "विद्युत फ्लक्स (Electric Flux)",
            nameEnglish = "Electric Flux",
            mathExpression = "\\Phi_E = \\vec{E} \\cdot \\vec{A} = E \\cdot A \\cdot \\cos\\theta",
            symbolsMeaning = listOf(
                "Φ_E = विद्युत फ्लक्स (N·m²/C या V·m)",
                "E = विद्युत क्षेत्र (N/C)",
                "A = पृष्ठ क्षेत्रफल (m²)",
                "θ = E⃗ तथा क्षेत्रफल सदिश A⃗ (अभिलंब) के बीच का कोण"
            ),
            siUnit = "N·m²/C अथवा V·m",
            conditions = "एकसमान क्षेत्र तथा समतल पृष्ठ के लिए। बंद पृष्ठ हेतु Φ = ∮ E⃗ · dS⃗।",
            topicId = "top_11",
            topicName = "विद्युत फ्लक्स"
        ),
        FormulaItem(
            id = "f_gauss_law",
            nameHindi = "गाउस का नियम (Gauss's Law)",
            nameEnglish = "Gauss's Law",
            mathExpression = "\\oint \\vec{E} \\cdot d\\vec{S} = \\frac{q_{\\text{enclosed}}}{\\varepsilon_0}",
            symbolsMeaning = listOf(
                "Φ = कुल फ्लक्स (N·m²/C)",
                "q_enclosed = पृष्ठ द्वारा परिबद्ध कुल आवेश (C)",
                "ε₀ = 8.854 × 10⁻¹² C²/N·m²"
            ),
            siUnit = "N·m²/C",
            conditions = "पृष्ठ पूर्णतः बंद होना चाहिए। बाह्य आवेशों का कुल फ्लक्स में शून्य योगदान होता है।",
            topicId = "top_12",
            topicName = "गाउस का नियम"
        ),
        FormulaItem(
            id = "f_gauss_wire",
            nameHindi = "अनंत सीधे आवेशित तार के कारण विद्युत क्षेत्र (Linear Charge)",
            nameEnglish = "Electric Field of Infinitely Long Wire",
            mathExpression = "E = \\frac{\\lambda}{2\\pi\\varepsilon_0 r}",
            symbolsMeaning = listOf(
                "E = विद्युत क्षेत्र (N/C)",
                "λ = रैखिक आवेश घनत्व (C/m)",
                "r = तार से लंबवत दूरी (m)"
            ),
            siUnit = "N/C अथवा V/m",
            conditions = "अनंत लंबाई का सीधा समरूप आवेशित तार। E ∝ 1/r (व्युत्क्रमानुपाती r)।",
            topicId = "top_12",
            topicName = "गाउस का नियम"
        ),
        FormulaItem(
            id = "f_gauss_sheet",
            nameHindi = "अनंत समतल आवेशित चादर का विद्युत क्षेत्र (Infinite Non-conducting Sheet)",
            nameEnglish = "Electric Field of Infinite Sheet",
            mathExpression = "E = \\frac{\\sigma}{2\\varepsilon_0}",
            symbolsMeaning = listOf(
                "E = विद्युत क्षेत्र (N/C)",
                "σ = पृष्ठीय आवेश घनत्व (C/m²)",
                "ε₀ = निर्वात की विद्युतशीलता"
            ),
            siUnit = "N/C अथवा V/m",
            conditions = "अनंत विस्तार की समतल पतली अचालक चादर। यह दूरी r से स्वतंत्र (Independent) होता है!",
            topicId = "top_12",
            topicName = "गाउस का नियम"
        ),
        FormulaItem(
            id = "f_spherical_shell",
            nameHindi = "एकसमान आवेशित पतले गोलीय खोल का क्षेत्र (Spherical Shell)",
            nameEnglish = "Electric Field of Spherical Shell",
            mathExpression = "\\begin{cases} E_{out} = \\frac{1}{4\\pi\\varepsilon_0}\\frac{q}{r^2} & (r > R) \\\\ E_{surf} = \\frac{1}{4\\pi\\varepsilon_0}\\frac{q}{R^2} & (r = R) \\\\ E_{in} = 0 & (r < R) \\end{cases}",
            symbolsMeaning = listOf(
                "R = गोलीय खोल की त्रिज्या (m)",
                "r = केंद्र से प्रेक्षण बिंदु की दूरी (m)",
                "q = खोल पर कुल आवेश (C)"
            ),
            siUnit = "N/C",
            conditions = "गोलीय खोल के भीतर विद्युत क्षेत्र सदैव शून्य (E = 0) होता है (स्थिर विद्युत परिरक्षण / Electrostatic Shielding)।",
            topicId = "top_12",
            topicName = "गाउस का नियम"
        )
    )
}
