package com.shuham.medilog.domain.model

import kotlin.time.Clock
import kotlin.time.Instant

/**
 * Gender options for Patient
 */
enum class Gender {
    MALE,
    FEMALE,
    OTHER;
    
    companion object {
        fun fromString(value: String): Gender {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: OTHER
        }
    }
}

/**
 * Patient Domain Model
 * Represents a patient in the healthcare system
 */
data class Patient(
    val id: String,
    val name: String,
    val age: Int,
    val gender: Gender,
    val contact: String,
    val notes: String? = null,
    val riskLevel: RiskLevel = RiskLevel.NORMAL,
    val isSynced: Boolean = false,
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now()
) {
    /**
     * Returns initials for avatar display
     * e.g., "John Doe" -> "JD"
     */
    val initials: String
        get() = name.split(" ")
            .take(2)
            .map { it.firstOrNull()?.uppercaseChar() ?: "" }
            .joinToString("")
    
    /**
     * Formatted display string for age and gender
     * e.g., "25 - Male"
     */
    val ageGenderDisplay: String
        get() = "$age - ${gender.name.lowercase().replaceFirstChar { it.uppercase() }}"
}
