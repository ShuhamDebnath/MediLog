package com.shuham.medilog

import androidx.compose.ui.window.ComposeUIViewController
import com.shuham.medilog.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }