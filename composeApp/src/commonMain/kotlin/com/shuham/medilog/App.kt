package com.shuham.medilog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shuham.medilog.di.allModules
import com.shuham.medilog.ui.theme.MediLogTheme
import org.koin.compose.KoinApplication

/**
 * Main Application Composable
 * Entry point for the MediLog app
 */
@Composable
fun App() {

    MediLogTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
        }
    }

}
