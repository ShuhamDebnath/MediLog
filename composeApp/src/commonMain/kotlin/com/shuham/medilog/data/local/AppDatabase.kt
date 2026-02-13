package com.shuham.medilog.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.shuham.medilog.data.local.dao.PatientDao
import com.shuham.medilog.data.local.dao.RecordDao
import com.shuham.medilog.data.local.entity.PatientEntity
import com.shuham.medilog.data.local.entity.RecordEntity

/**
 * Room Database for MediLog
 * Contains Patient and Record tables
 */
@Database(
    entities = [
        PatientEntity::class,
        RecordEntity::class
    ],
    version = 1,
    exportSchema = true
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun recordDao(): RecordDao
}

/**
 * Room Database Constructor for KMP
 * Required for Room KMP
 */
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
