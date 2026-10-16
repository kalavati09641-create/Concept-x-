package com.example.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// ConceptX Warm White, Saffron & Deep Kesari Design System Tokens
val MainBackground = Color(0xFFFFFDF8)
val MainSurface = Color(0xFFFFFFFF)
val PrimarySaffron = Color(0xFFF59E0B)
val DeepKesari = Color(0xFFC65D12)
val SaffronLight = Color(0xFFFFB454)
val MainText = Color(0xFF172033)
val SecondaryText = Color(0xFF596273)
val SoftPeach = Color(0xFFFFF0D9)
val SoftPeachLight = Color(0xFFFFF8EC)
val SuccessGreen = Color(0xFF166534)
val SubtleBorder = Color(0xFFE8E3DA)

// Dark Mode Tokens (Deep Ink, Warm Saffron Accents)
val DarkInkBackground = Color(0xFF0D121D)
val DarkInkSurface = Color(0xFF151C2C)
val DarkInkSurfaceVariant = Color(0xFF1E283D)
val DarkInkBorder = Color(0xFF2B374E)
val DarkTextPrimary = Color(0xFFF8FAFC)
val DarkTextSecondary = Color(0xFF94A3B8)
val DarkSoftPeach = Color(0xFF261E14)

// Subject specific accent tags
val PhysicsColor = Color(0xFFC65D12)
val ChemistryColor = Color(0xFF0D9488)
val MathColor = Color(0xFF2563EB)
val HindiColor = Color(0xFFD946EF)
val EnglishColor = Color(0xFF7C3AED)

// Badges & Status
val WarningAmber = Color(0xFFD97706)
val ErrorRed = Color(0xFFDC2626)
val BoardBadgeBg = Color(0xFFFFF0D9)
val BoardBadgeText = Color(0xFFC65D12)

// Controlled Elegant Gradients
val HeroWarmGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFFFFF0D9),
        Color(0xFFFFB454)
    ),
    start = Offset(0f, 0f),
    end = Offset(1000f, 500f)
)

val HeroWarmGradientDark = Brush.linearGradient(
    colors = listOf(
        Color(0xFF151C2C),
        Color(0xFF241D17),
        Color(0xFF381F0D)
    ),
    start = Offset(0f, 0f),
    end = Offset(1000f, 500f)
)

val SaffronButtonGradient = Brush.horizontalGradient(
    colors = listOf(DeepKesari, PrimarySaffron)
)
