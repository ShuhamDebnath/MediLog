package com.shuham.medilog.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.module.Module

/**
 * DataStore Module for Koin DI
 * Provides DataStore instance for preferences
 */
expect val dataStoreModule: Module
