package com.shuham.medilog.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shuham.medilog.data.local.entity.RecordEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Record operations
 */
@Dao
interface RecordDao {
    
    /**
     * Insert a new record
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: RecordEntity)
    
    /**
     * Update an existing record
     */
    @Update
    suspend fun update(record: RecordEntity)
    
    /**
     * Delete a record
     */
    @Delete
    suspend fun delete(record: RecordEntity)
    
    /**
     * Get all records for a patient as Flow (reactive)
     */
    @Query("SELECT * FROM records WHERE patientId = :patientId ORDER BY timestamp DESC")
    fun getRecordsForPatient(patientId: String): Flow<List<RecordEntity>>
    
    /**
     * Get record by ID
     */
    @Query("SELECT * FROM records WHERE id = :recordId")
    suspend fun getRecordById(recordId: String): RecordEntity?
    
    /**
     * Get all records as Flow (reactive)
     */
    @Query("SELECT * FROM records ORDER BY timestamp DESC")
    fun getAllRecords(): Flow<List<RecordEntity>>
    
    /**
     * Get recent records with limit
     */
    @Query("SELECT * FROM records ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentRecords(limit: Int = 10): Flow<List<RecordEntity>>
    
    /**
     * Get count of records for a patient
     */
    @Query("SELECT COUNT(*) FROM records WHERE patientId = :patientId")
    fun getRecordCountForPatient(patientId: String): Flow<Int>
    
    /**
     * Get total count of records
     */
    @Query("SELECT COUNT(*) FROM records")
    fun getTotalRecordCount(): Flow<Int>
    
    /**
     * Delete all records for a patient
     */
    @Query("DELETE FROM records WHERE patientId = :patientId")
    suspend fun deleteRecordsForPatient(patientId: String)
    
    /**
     * Delete record by ID
     */
    @Query("DELETE FROM records WHERE id = :recordId")
    suspend fun deleteById(recordId: String)
}
