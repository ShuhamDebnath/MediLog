package com.shuham.medilog.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.shuham.medilog.data.repository.PreferencesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Android DataStore Module
 * Uses Android-specific preferencesDataStore delegate
 */
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "medilog_preferences")

actual val dataStoreModule: Module = module {
    single<DataStore<Preferences>> {
        androidContext().dataStore
    }
    
    single { PreferencesRepository(get()) }
}
