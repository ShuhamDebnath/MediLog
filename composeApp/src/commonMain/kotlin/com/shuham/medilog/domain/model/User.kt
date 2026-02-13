package com.shuham.medilog.domain.model

/**
 * User Domain Model
 * Represents an authenticated user
 */
data class User(
    val id: String,
    val email: String,
    val name: String,
    val isGuest: Boolean = false
)
