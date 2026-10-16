package com.example.util

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import com.example.model.TopicNote
import java.io.File
import java.io.FileOutputStream

object PdfExporter {

    /**
     * Generates a clean, beautifully formatted multi-page PDF document for the given physics note
     * and triggers a share / view intent so the student can save or view it offline.
     */
    fun exportNoteToPdf(context: Context, note: TopicNote): File? {
        val pdfDocument = PdfDocument()

        val pageWidth = 595 // Standard A4 width in PostScript points (72 dpi approx)
        val pageHeight = 842 // Standard A4 height
        var pageNumber = 1

        val textPaint = Paint().apply {
            color = Color.rgb(23, 32, 51) // Ink Black (#172033)
            textSize = 11f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            isAntiAlias = true
        }

        val headerPaint = Paint().apply {
            color = Color.rgb(198, 93, 18) // Deep Kesari (#C65D12)
            textSize = 17f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val subHeaderPaint = Paint().apply {
            color = Color.rgb(198, 93, 18)
            textSize = 13f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val metaPaint = Paint().apply {
            color = Color.rgb(89, 98, 115) // Secondary text (#596273)
            textSize = 10f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            isAntiAlias = true
        }

        val bannerBgPaint = Paint().apply {
            color = Color.rgb(255, 240, 217) // Soft peach (#FFF0D9)
            style = Paint.Style.FILL
        }

        val cardBgPaint = Paint().apply {
            color = Color.rgb(255, 253, 248) // Warm white (#FFFDF8)
            style = Paint.Style.FILL
        }

        val borderPaint = Paint().apply {
            color = Color.rgb(232, 227, 218) // Subtle border (#E8E3DA)
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }

        var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
        var page = pdfDocument.startPage(pageInfo)
        var canvas = page.canvas

        val margin = 36f
        val contentWidth = pageWidth - (margin * 2)
        var yPos = margin + 10f

        fun checkPageBreak(requiredHeight: Float) {
            if (yPos + requiredHeight > pageHeight - margin) {
                // Draw footer on current page
                metaPaint.textAlign = Paint.Align.RIGHT
                canvas.drawText("पृष्ठ $pageNumber • ConceptX भौतिकी नोट्स", pageWidth - margin, pageHeight - 20f, metaPaint)
                metaPaint.textAlign = Paint.Align.LEFT

                pdfDocument.finishPage(page)
                pageNumber++
                pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                page = pdfDocument.startPage(pageInfo)
                canvas = page.canvas
                yPos = margin + 15f
            }
        }

        fun drawWrappedText(text: String, paint: Paint, maxWidth: Float, lineSpacing: Float = 14f) {
            val words = text.split(" ")
            var currentLine = StringBuilder()

            for (word in words) {
                val candidate = if (currentLine.isEmpty()) word else "$currentLine $word"
                val width = paint.measureText(candidate)
                if (width <= maxWidth) {
                    currentLine = StringBuilder(candidate)
                } else {
                    checkPageBreak(lineSpacing)
                    canvas.drawText(currentLine.toString(), margin + 10f, yPos, paint)
                    yPos += lineSpacing
                    currentLine = StringBuilder(word)
                }
            }
            if (currentLine.isNotEmpty()) {
                checkPageBreak(lineSpacing)
                canvas.drawText(currentLine.toString(), margin + 10f, yPos, paint)
                yPos += lineSpacing
            }
        }

        fun drawSection(title: String, content: String) {
            checkPageBreak(50f)
            // Section Card Header
            canvas.drawRect(margin, yPos, margin + contentWidth, yPos + 26f, bannerBgPaint)
            canvas.drawRect(margin, yPos, margin + contentWidth, yPos + 26f, borderPaint)
            canvas.drawText(title, margin + 10f, yPos + 18f, subHeaderPaint)
            yPos += 34f

            drawWrappedText(content, textPaint, contentWidth - 20f, 15f)
            yPos += 14f
        }

        // Top Header Banner
        canvas.drawRect(margin, yPos, margin + contentWidth, yPos + 55f, bannerBgPaint)
        canvas.drawRect(margin, yPos, margin + contentWidth, yPos + 55f, borderPaint)

        canvas.drawText("ConceptX • 12वीं भौतिक विज्ञान (MP Board / NCERT)", margin + 12f, yPos + 20f, headerPaint)
        canvas.drawText("${note.titleHindi} (${note.titleEnglish}) • सत्र 2026-27", margin + 12f, yPos + 40f, metaPaint)
        yPos += 72f

        // 1. Important Definition
        if (note.importantDefinition.isNotBlank()) {
            drawSection("महत्वपूर्ण परिभाषा (Formal Definition):", note.importantDefinition)
        }

        // 2. Simple Hindi Explanation
        if (note.conceptSimpleHindi.isNotBlank()) {
            drawSection("सरल व्याख्या (Simple Hindi):", note.conceptSimpleHindi)
        }

        // 3. Real-life Analogy
        if (note.realLifeAnalogy.isNotBlank()) {
            drawSection("दैनिक जीवन का उदाहरण (Real-Life Analogy):", note.realLifeAnalogy)
        }

        // 4. SI Units and Dimensions
        if (note.siUnitsAndDimensions.isNotBlank()) {
            drawSection("SI मात्रक एवं विमीय सूत्र (Units & Dimensions):", note.siUnitsAndDimensions)
        }

        // 5. Step by step explanation
        if (note.stepByStepExplanation.isNotEmpty()) {
            checkPageBreak(40f)
            canvas.drawRect(margin, yPos, margin + contentWidth, yPos + 26f, bannerBgPaint)
            canvas.drawRect(margin, yPos, margin + contentWidth, yPos + 26f, borderPaint)
            canvas.drawText("चरणबद्ध सिद्धांत (Step-by-Step Principles):", margin + 10f, yPos + 18f, subHeaderPaint)
            yPos += 34f

            for (step in note.stepByStepExplanation) {
                drawWrappedText("• $step", textPaint, contentWidth - 20f, 15f)
                yPos += 4f
            }
            yPos += 10f
        }

        // 6. Solved Numerical
        val num = note.solvedNumerical
        if (num != null) {
            checkPageBreak(50f)
            canvas.drawRect(margin, yPos, margin + contentWidth, yPos + 26f, bannerBgPaint)
            canvas.drawRect(margin, yPos, margin + contentWidth, yPos + 26f, borderPaint)
            canvas.drawText("हल किया गया आंकिक प्रश्न (Solved Numerical):", margin + 10f, yPos + 18f, subHeaderPaint)
            yPos += 34f

            drawWrappedText("प्रश्न: ${num.problemStatementHindi}", textPaint, contentWidth - 20f, 15f)
            yPos += 6f

            if (num.formulaUsed.isNotBlank()) {
                drawWrappedText("प्रयुक्त सूत्र: ${num.formulaUsed}", subHeaderPaint, contentWidth - 20f, 15f)
                yPos += 6f
            }

            for (step in num.stepByStepSolution) {
                drawWrappedText("  $step", textPaint, contentWidth - 20f, 14f)
            }
            yPos += 6f

            drawWrappedText("अंतिम उत्तर: ${num.finalAnswer}", subHeaderPaint, contentWidth - 20f, 15f)
            yPos += 14f
        }

        // 7. Common Mistakes
        if (note.commonMistakes.isNotEmpty()) {
            checkPageBreak(40f)
            canvas.drawRect(margin, yPos, margin + contentWidth, yPos + 26f, bannerBgPaint)
            canvas.drawRect(margin, yPos, margin + contentWidth, yPos + 26f, borderPaint)
            canvas.drawText("परीक्षा में अक्सर होने वाली गलतियां (Common Mistakes):", margin + 10f, yPos + 18f, subHeaderPaint)
            yPos += 34f

            for (mistake in note.commonMistakes) {
                drawWrappedText("⚠ $mistake", textPaint, contentWidth - 20f, 15f)
                yPos += 4f
            }
            yPos += 10f
        }

        // 8. Quick Revision Points
        if (note.quickRevisionPoints.isNotEmpty()) {
            checkPageBreak(40f)
            canvas.drawRect(margin, yPos, margin + contentWidth, yPos + 26f, bannerBgPaint)
            canvas.drawRect(margin, yPos, margin + contentWidth, yPos + 26f, borderPaint)
            canvas.drawText("त्वरित दोहराव बिंदु (Quick Revision):", margin + 10f, yPos + 18f, subHeaderPaint)
            yPos += 34f

            for (pt in note.quickRevisionPoints) {
                drawWrappedText("✓ $pt", textPaint, contentWidth - 20f, 15f)
                yPos += 4f
            }
            yPos += 10f
        }

        // Draw final page footer
        metaPaint.textAlign = Paint.Align.RIGHT
        canvas.drawText("पृष्ठ $pageNumber • ConceptX भौतिकी नोट्स", pageWidth - margin, pageHeight - 20f, metaPaint)
        metaPaint.textAlign = Paint.Align.LEFT

        pdfDocument.finishPage(page)

        // Save PDF to cache directory
        return try {
            val pdfDir = File(context.cacheDir, "exported_notes")
            if (!pdfDir.exists()) pdfDir.mkdirs()

            val sanitizedTitle = note.titleEnglish.replace(Regex("[^a-zA-Z0-9]"), "_")
            val pdfFile = File(pdfDir, "ConceptX_${note.topicId}_$sanitizedTitle.pdf")

            val outStream = FileOutputStream(pdfFile)
            pdfDocument.writeTo(outStream)
            outStream.flush()
            outStream.close()
            pdfDocument.close()
            pdfFile
        } catch (e: Exception) {
            e.printStackTrace()
            pdfDocument.close()
            null
        }
    }

    /**
     * Shares or opens the exported PDF file using an Android chooser intent with FileProvider
     */
    fun sharePdf(context: Context, pdfFile: File, title: String) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            pdfFile
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "$title - ConceptX भौतिकी नोट्स")
            putExtra(Intent.EXTRA_TEXT, "$title के संपूर्ण अवधारणात्मक भौतिकी नोट्स (PDF रूप में)।")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val chooser = Intent.createChooser(intent, "नोट्स PDF सहेजें या साझा करें")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }
}
