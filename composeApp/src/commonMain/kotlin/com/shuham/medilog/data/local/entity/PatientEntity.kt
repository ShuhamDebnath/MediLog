package com.shuham.medilog.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shuham.medilog.domain.model.Gender
import com.shuham.medilog.domain.model.Patient
import com.shuham.medilog.domain.model.RiskLevel
import kotlin.time.Instant

/**
 * Room Entity for Patient
 * Maps to 'patients' table in SQLite
 */
@Entity(tableName = "patients")
data class PatientEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val age: Int,
    val gender: String, // "MALE", "FEMALE", "OTHER"
    val contact: String,
    val notes: String?,
    val riskLevel: String, // "NORMAL", "REVIEW", "HIGH"
    val isSynced: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long
) {
    /**
     * Convert Entity to Domain Model
     */
    fun toDomain(): Patient = Patient(
        id = id,
        name = name,
        age = age,
        gender = Gender.fromString(gender),
        contact = contact,
        notes = notes,
        riskLevel = RiskLevel.fromString(riskLevel),
        isSynced = isSynced,
        createdAt = Instant.fromEpochMilliseconds(createdAt),
        updatedAt = Instant.fromEpochMilliseconds(updatedAt)
    )
    
    companion object {
        /**
         * Convert Domain Model to Entity
         */
        fun fromDomain(patient: Patient): PatientEntity = PatientEntity(
            id = patient.id,
            name = patient.name,
            age = patient.age,
            gender = patient.gender.name,
            contact = patient.contact,
            notes = patient.notes,
            riskLevel = patient.riskLevel.name,
            isSynced = patient.isSynced,
            createdAt = patient.createdAt.toEpochMilliseconds(),
            updatedAt = patient.updatedAt.toEpochMilliseconds()
        )
    }
}
