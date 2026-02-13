package com.shuham.medilog.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        // Allow platforms to pass in config (like androidContext)
        config?.invoke(this)

        // Load the modules
        modules(allModules)
    }
}