package com.shuham.medilog.data.repository

import com.shuham.medilog.data.local.dao.RecordDao
import com.shuham.medilog.data.local.entity.RecordEntity
import com.shuham.medilog.domain.model.AIResult
import com.shuham.medilog.domain.model.Record
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock

/**
 * Repository for Record data operations
 * Handles medical scan records for patients
 */
class RecordRepository(
    private val recordDao: RecordDao
) {
    /**
     * Get all records for a patient
     */
    fun getRecordsForPatient(patientId: String): Flow<List<Record>> {
        return recordDao.getRecordsForPatient(patientId)
            .map { entities -> entities.map { it.toDomain() } }
    }
    
    /**
     * Get record by ID
     */
    suspend fun getRecordById(id: String): Record? {
        return recordDao.getRecordById(id)?.toDomain()
    }
    
    /**
     * Get all records
     */
    fun getAllRecords(): Flow<List<Record>> {
        return recordDao.getAllRecords()
            .map { entities -> entities.map { it.toDomain() } }
    }
    
    /**
     * Get recent records
     */
    fun getRecentRecords(limit: Int = 10): Flow<List<Record>> {
        return recordDao.getRecentRecords(limit)
            .map { entities -> entities.map { it.toDomain() } }
    }
    
    /**
     * Add a new record
     */
    suspend fun addRecord(
        patientId: String,
        localImagePath: String,
        aiResult: AIResult? = null
    ): Record {
        val record = Record(
            id = generateId(),
            patientId = patientId,
            localImagePath = localImagePath,
            remoteImageUrl = null,
            aiResult = aiResult,
            timestamp = Clock.System.now()
        )
        
        recordDao.insert(RecordEntity.fromDomain(record))
        return record
    }
    
    /**
     * Update record with AI result
     */
    suspend fun updateRecordWithAIResult(recordId: String, aiResult: AIResult) {
        val record = getRecordById(recordId) ?: return
        val updatedRecord = record.copy(aiResult = aiResult)
        recordDao.update(RecordEntity.fromDomain(updatedRecord))
    }
    
    /**
     * Delete a record
     */
    suspend fun deleteRecord(record: Record) {
        recordDao.delete(RecordEntity.fromDomain(record))
    }
    
    /**
     * Delete record by ID
     */
    suspend fun deleteRecordById(id: String) {
        recordDao.deleteById(id)
    }
    
    /**
     * Delete all records for a patient
     */
    suspend fun deleteRecordsForPatient(patientId: String) {
        recordDao.deleteRecordsForPatient(patientId)
    }
    
    /**
     * Get record count for a patient
     */
    fun getRecordCountForPatient(patientId: String): Flow<Int> {
        return recordDao.getRecordCountForPatient(patientId)
    }
    
    /**
     * Get total record count
     */
    fun getTotalRecordCount(): Flow<Int> {
        return recordDao.getTotalRecordCount()
    }
    
    /**
     * Generate unique ID for record
     */
    private fun generateId(): String {
        return "record_${Clock.System.now().toEpochMilliseconds()}_${(1000..9999).random()}"
    }
}
