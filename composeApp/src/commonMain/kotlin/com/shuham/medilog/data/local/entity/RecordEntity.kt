package com.shuham.medilog.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.shuham.medilog.domain.model.AIResult
import com.shuham.medilog.domain.model.Record
import com.shuham.medilog.domain.model.RiskLevel
import kotlinx.datetime.Instant
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Room Entity for Medical Record
 * Maps to 'records' table in SQLite
 * Has foreign key relationship to PatientEntity
 */
@Entity(
    tableName = "records",
    foreignKeys = [
        ForeignKey(
            entity = PatientEntity::class,
            parentColumns = ["id"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["patientId"])]
)
data class RecordEntity(
    @PrimaryKey
    val id: String,
    val patientId: String,
    val localImagePath: String,
    val remoteImageUrl: String?,
    val aiResultJson: String?, // Serialized AIResult
    val timestamp: Long
) {
    /**
     * Convert Entity to Domain Model
     */
    fun toDomain(): Record {
        val aiResult = aiResultJson?.let {
            try {
                Json.decodeFromString<AIResult>(it)
            } catch (e: Exception) {
                null
            }
        }
        
        return Record(
            id = id,
            patientId = patientId,
            localImagePath = localImagePath,
            remoteImageUrl = remoteImageUrl,
            aiResult = aiResult,
            timestamp = Instant.fromEpochMilliseconds(timestamp)
        )
    }
    
    companion object {
        /**
         * Convert Domain Model to Entity
         */
        fun fromDomain(record: Record): RecordEntity {
            val aiResultJson = record.aiResult?.let {
                Json.encodeToString(it)
            }
            
            return RecordEntity(
                id = record.id,
                patientId = record.patientId,
                localImagePath = record.localImagePath,
                remoteImageUrl = record.remoteImageUrl,
                aiResultJson = aiResultJson,
                timestamp = record.timestamp.toEpochMilliseconds()
            )
        }
    }
}
