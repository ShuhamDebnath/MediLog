package com.shuham.medilog.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesSerializer
import com.shuham.medilog.data.repository.PreferencesRepository
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import okio.FileSystem
import okio.Path.Companion.toPath
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

/**
 * iOS DataStore Module
 * Uses Okio file system for iOS
 */

@OptIn(ExperimentalForeignApi::class)
actual val dataStoreModule = module {
    single<DataStore<Preferences>> {
        // 1. Get the Document Directory path correctly for iOS
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )

        // 2. Ensure path is valid and append filename
        val requirePath = requireNotNull(documentDirectory?.path) { "Document directory not found" }
        val path = "$requirePath/medilog_preferences.preferences_pb"

        // 3. Create explicit OkioStorage to satisfy the DataStoreFactory.create signature
        val storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = PreferencesSerializer,
            producePath = { path.toPath() }
        )

        // 4. Create DataStore passing the storage object
        DataStoreFactory.create(
            storage = storage,
            corruptionHandler = null,
            migrations = emptyList(),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }

     //Assuming you have a repository that consumes this DataStore
     single { PreferencesRepository(get()) }
}
