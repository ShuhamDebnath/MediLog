package com.shuham.medilog.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * MediLog Color Palette
 * Based on UIUX.md specifications - Ultra-Clean, Minimalist Design
 */

// Primary Colors
val Primary = Color(0xFF008080) // Teal - Active Buttons, Icons, Highlights
val OnPrimary = Color(0xFFFFFFFF) // White - Text on Primary backgrounds
val Secondary = Color(0xFFE0F2F1) // Light Teal - Button backgrounds (Inactive), Accents

// Background & Surface
val Background = Color(0xFFFFFFFF) // Pure White - Main Screen Backgrounds
val Surface = Color(0xFFFFFFFF) // Pure White - Cards, Sheets
val SurfaceVariant = Color(0xFFF9FAFB) // Light Grey - Form fields

// Text Colors
val TextPrimary = Color(0xFF111827) // Almost Black - Headlines, Body Text
val TextSecondary = Color(0xFF6B7280) // Cool Grey - Captions, Subtitles
val TextTertiary = Color(0xFF9CA3AF) // Light Grey - Disabled text

// Status Colors
val Success = Color(0xFF10B981) // Emerald - Normal Risk, Success
val Warning = Color(0xFFF59E0B) // Amber - Needs Review
val Error = Color(0xFFEF4444) // Soft Red - High Risk, Errors

// Status Background Colors (Light versions for badges/cards)
val SuccessBackground = Color(0xFFD1FAE5) // Light Green
val WarningBackground = Color(0xFFFEF3C7) // Light Amber
val ErrorBackground = Color(0xFFFEE2E2) // Light Red

// Input Colors
val InputBackground = Color(0xFFF3F4F6) // Light Grey - Input field backgrounds
val InputBorder = Color(0xFFE5E7EB) // Grey - Input borders
val InputBorderFocused = Color(0xFF008080) // Teal - Focused input border

// Divider & Border
val Divider = Color(0xFFE5E7EB)
val Border = Color(0xFFE5E7EB)

// Shadow Color (for reference - used in modifiers)
val ShadowColor = Color(0x14000000) // rgba(0,0,0,0.08)

// Dark Theme Colors (for future implementation)
val PrimaryDark = Color(0xFF4DD0C1)
val BackgroundDark = Color(0xFF121212)
val SurfaceDark = Color(0xFF1E1E1E)
val TextPrimaryDark = Color(0xFFF9FAFB)
val TextSecondaryDark = Color(0xFF9CA3AF)
