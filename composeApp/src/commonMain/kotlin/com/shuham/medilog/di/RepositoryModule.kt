package com.shuham.medilog.di

import com.shuham.medilog.data.repository.PatientRepository
import com.shuham.medilog.data.repository.RecordRepository
import org.koin.dsl.module

/**
 * Repository Module for Koin DI
 * Provides repository instances
 */
val repositoryModule = module {
    // Patient Repository
    single { PatientRepository(get()) }
    
    // Record Repository
    single { RecordRepository(get()) }
}
