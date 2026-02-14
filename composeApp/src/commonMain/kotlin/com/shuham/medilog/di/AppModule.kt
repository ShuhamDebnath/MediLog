package com.shuham.medilog.di

import com.shuham.medilog.data.local.AppDatabase
import org.koin.dsl.module

/**
 * App Module for Koin DI
 * Provides application-wide dependencies
 */
val appModule = module {

    // Patient DAO
    single { get<AppDatabase>().patientDao() }

    // Record DAO
    single { get<AppDatabase>().recordDao() }
}

/**
 * All Koin modules combined
 */
val allModules = listOf(
    appModule,
    databaseModule,
    dataStoreModule,
    repositoryModule
)
