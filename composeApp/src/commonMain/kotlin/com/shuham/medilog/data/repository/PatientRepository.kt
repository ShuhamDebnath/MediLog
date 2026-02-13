package com.shuham.medilog.data.repository

import com.shuham.medilog.data.local.dao.PatientDao
import com.shuham.medilog.data.local.entity.PatientEntity
import com.shuham.medilog.domain.model.Gender
import com.shuham.medilog.domain.model.Patient
import com.shuham.medilog.domain.model.RiskLevel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock
import kotlin.time.Instant

/**
 * Repository for Patient data operations
 * Acts as the single source of truth for patient data
 */
class PatientRepository(
    private val patientDao: PatientDao
) {
    /**
     * Get all patients as Flow of domain models
     */
    fun getAllPatients(): Flow<List<Patient>> {
        return patientDao.getAllPatients()
            .map { entities -> entities.map { it.toDomain() } }
    }
    
    /**
     * Get patient by ID
     */
    suspend fun getPatientById(id: String): Patient? {
        return patientDao.getPatientById(id)?.toDomain()
    }
    
    /**
     * Get patient by ID as Flow
     */
    fun getPatientByIdFlow(id: String): Flow<Patient?> {
        return patientDao.getPatientByIdFlow(id)
            .map { it?.toDomain() }
    }
    
    /**
     * Search patients by name
     */
    fun searchPatients(query: String): Flow<List<Patient>> {
        return patientDao.searchPatients(query)
            .map { entities -> entities.map { it.toDomain() } }
    }
    
    /**
     * Get patients by risk level
     */
    fun getPatientsByRiskLevel(riskLevel: RiskLevel): Flow<List<Patient>> {
        return patientDao.getPatientsByRiskLevel(riskLevel.name)
            .map { entities -> entities.map { it.toDomain() } }
    }
    
    /**
     * Get patients by sync status
     */
    fun getPatientsBySyncStatus(isSynced: Boolean): Flow<List<Patient>> {
        return patientDao.getPatientsBySyncStatus(isSynced)
            .map { entities -> entities.map { it.toDomain() } }
    }
    
    /**
     * Add a new patient
     */
    suspend fun addPatient(
        name: String,
        age: Int,
        gender: Gender,
        contact: String,
        notes: String? = null
    ): Patient {
        val now = Clock.System.now()
        val patient = Patient(
            id = generateId(),
            name = name,
            age = age,
            gender = gender,
            contact = contact,
            notes = notes,
            riskLevel = RiskLevel.NORMAL,
            isSynced = false,
            createdAt = now,
            updatedAt = now
        )
        
        patientDao.insert(PatientEntity.fromDomain(patient))
        return patient
    }
    
    /**
     * Update an existing patient
     */
    suspend fun updatePatient(patient: Patient) {
        val updatedPatient = patient.copy(
            updatedAt = Clock.System.now(),
            isSynced = false
        )
        patientDao.update(PatientEntity.fromDomain(updatedPatient))
    }
    
    /**
     * Update patient risk level
     */
    suspend fun updateRiskLevel(patientId: String, riskLevel: RiskLevel) {
        val patient = getPatientById(patientId) ?: return
        val updatedPatient = patient.copy(
            riskLevel = riskLevel,
            updatedAt = Clock.System.now(),
            isSynced = false
        )
        patientDao.update(PatientEntity.fromDomain(updatedPatient))
    }
    
    /**
     * Delete a patient
     */
    suspend fun deletePatient(patient: Patient) {
        patientDao.delete(PatientEntity.fromDomain(patient))
    }
    
    /**
     * Delete patient by ID
     */
    suspend fun deletePatientById(id: String) {
        patientDao.deleteById(id)
    }
    
    /**
     * Get patient count
     */
    fun getPatientCount(): Flow<Int> {
        return patientDao.getPatientCount()
    }
    
    /**
     * Get patient count by risk level
     */
    fun getPatientCountByRiskLevel(riskLevel: RiskLevel): Flow<Int> {
        return patientDao.getPatientCountByRiskLevel(riskLevel.name)
    }
    
    /**
     * Get unsynced patient count
     */
    fun getUnsyncedCount(): Flow<Int> {
        return patientDao.getUnsyncedCount()
    }
    
    /**
     * Get all unsynced patients
     */
    suspend fun getUnsyncedPatients(): List<Patient> {
        return patientDao.getUnsyncedPatients().map { it.toDomain() }
    }
    
    /**
     * Mark patient as synced
     */
    suspend fun markAsSynced(patientId: String) {
        patientDao.markAsSynced(patientId)
    }
    
    /**
     * Generate unique ID for patient
     */
    private fun generateId(): String {
        return "patient_${Clock.System.now().toEpochMilliseconds()}_${(1000..9999).random()}"
    }
}
