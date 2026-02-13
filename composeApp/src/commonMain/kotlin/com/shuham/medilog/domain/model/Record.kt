package com.shuham.medilog.domain.model

import kotlin.time.Clock
import kotlin.time.Instant
import kotlinx.serialization.Serializable

/**
 * AI Analysis Result
 * Contains the result of image analysis
 */
@Serializable
data class AIResult(
    val riskLevel: RiskLevel,
    val confidence: Double,
    val explanation: String
)

/**
 * Medical Record Domain Model
 * Represents a scan/analysis record for a patient
 */
data class Record(
    val id: String,
    val patientId: String,
    val localImagePath: String,
    val remoteImageUrl: String? = null,
    val aiResult: AIResult? = null,
    val timestamp: Instant = Clock.System.now()
)
