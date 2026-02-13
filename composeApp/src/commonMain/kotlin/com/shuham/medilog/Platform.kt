package com.shuham.medilog

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform