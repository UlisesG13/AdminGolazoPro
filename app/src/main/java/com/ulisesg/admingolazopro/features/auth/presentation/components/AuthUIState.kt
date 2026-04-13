package com.ulisesg.admingolazopro.features.auth.presentation.components

import com.ulisesg.admingolazopro.features.auth.domain.entities.User

data class AuthUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val isAuthenticated: Boolean = false
)
