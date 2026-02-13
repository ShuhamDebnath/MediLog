package com.shuham.medilog.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.shuham.medilog.data.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val databaseModule: Module = module {

    single<AppDatabase> {
        // Android specific database builder
        val dbFile = androidContext().getDatabasePath("medilog.db")

        Room.databaseBuilder<AppDatabase>(
            context = androidContext(),
            name = dbFile.absolutePath
        )
            .setDriver(BundledSQLiteDriver()) // The KMP SQLite Driver
            .fallbackToDestructiveMigration(true) // Clears DB if schema changes (good for dev)
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

}