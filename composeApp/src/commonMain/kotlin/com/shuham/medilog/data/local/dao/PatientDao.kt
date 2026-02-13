package com.shuham.medilog.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shuham.medilog.data.local.entity.PatientEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Patient operations
 */
@Dao
interface PatientDao {
    
    /**
     * Insert a new patient
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(patient: PatientEntity)
    
    /**
     * Update an existing patient
     */
    @Update
    suspend fun update(patient: PatientEntity)
    
    /**
     * Delete a patient
     */
    @Delete
    suspend fun delete(patient: PatientEntity)
    
    /**
     * Get all patients as Flow (reactive)
     */
    @Query("SELECT * FROM patients ORDER BY updatedAt DESC")
    fun getAllPatients(): Flow<List<PatientEntity>>
    
    /**
     * Get patient by ID
     */
    @Query("SELECT * FROM patients WHERE id = :patientId")
    suspend fun getPatientById(patientId: String): PatientEntity?
    
    /**
     * Get patient by ID as Flow (reactive)
     */
    @Query("SELECT * FROM patients WHERE id = :patientId")
    fun getPatientByIdFlow(patientId: String): Flow<PatientEntity?>
    
    /**
     * Search patients by name
     */
    @Query("SELECT * FROM patients WHERE name LIKE '%' || :query || '%' ORDER BY updatedAt DESC")
    fun searchPatients(query: String): Flow<List<PatientEntity>>
    
    /**
     * Get patients by risk level
     */
    @Query("SELECT * FROM patients WHERE riskLevel = :riskLevel ORDER BY updatedAt DESC")
    fun getPatientsByRiskLevel(riskLevel: String): Flow<List<PatientEntity>>
    
    /**
     * Get patients by sync status
     */
    @Query("SELECT * FROM patients WHERE isSynced = :isSynced ORDER BY updatedAt DESC")
    fun getPatientsBySyncStatus(isSynced: Boolean): Flow<List<PatientEntity>>
    
    /**
     * Get count of all patients
     */
    @Query("SELECT COUNT(*) FROM patients")
    fun getPatientCount(): Flow<Int>
    
    /**
     * Get count of patients by risk level
     */
    @Query("SELECT COUNT(*) FROM patients WHERE riskLevel = :riskLevel")
    fun getPatientCountByRiskLevel(riskLevel: String): Flow<Int>
    
    /**
     * Get count of unsynced patients
     */
    @Query("SELECT COUNT(*) FROM patients WHERE isSynced = 0")
    fun getUnsyncedCount(): Flow<Int>
    
    /**
     * Get all unsynced patients
     */
    @Query("SELECT * FROM patients WHERE isSynced = 0")
    suspend fun getUnsyncedPatients(): List<PatientEntity>
    
    /**
     * Mark patient as synced
     */
    @Query("UPDATE patients SET isSynced = 1 WHERE id = :patientId")
    suspend fun markAsSynced(patientId: String)
    
    /**
     * Soft delete - mark as deleted (for future sync)
     * For now, we'll use hard delete
     */
    @Query("DELETE FROM patients WHERE id = :patientId")
    suspend fun deleteById(patientId: String)
}
