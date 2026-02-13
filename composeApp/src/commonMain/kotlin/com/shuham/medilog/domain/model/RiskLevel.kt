package com.shuham.medilog.domain.model

/**
 * Risk Level for AI Analysis Results
 * Used for color-coded health risk indicators
 */
enum class RiskLevel {
    NORMAL,   // Green - No concerns detected
    REVIEW,   // Orange - Needs further review
    HIGH;     // Red - High risk, immediate attention
    
    companion object {
        fun fromString(value: String): RiskLevel {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: NORMAL
        }
    }
}
