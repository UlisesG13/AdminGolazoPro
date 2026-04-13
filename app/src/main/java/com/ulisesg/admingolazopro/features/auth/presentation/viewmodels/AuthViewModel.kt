package com.ulisesg.admingolazopro.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulisesg.admingolazopro.features.auth.domain.entities.Rol
import com.ulisesg.admingolazopro.features.auth.domain.entities.User
import com.ulisesg.admingolazopro.features.auth.domain.usecases.Login
import com.ulisesg.admingolazopro.features.auth.domain.usecases.Register
import com.ulisesg.admingolazopro.features.auth.presentation.components.AuthUiState
import com.ulisesg.admingolazopro.features.notification.domain.usecases.RegisterFCMTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: Login,
    private val registerUseCase: Register,
    private val registerFCMTokenUseCase: RegisterFCMTokenUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        AuthUiState(
            user = User(
                id = "",
                nombre = "",
                email = "",
                password = "",
                rol = Rol.ADMINISTRADOR
            )
        )
    )
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    fun updateEmail(email: String) {
        _state.update { it.copy(user = it.user?.copy(email = email)) }
    }

    fun updatePassword(password: String) {
        _state.update { it.copy(user = it.user?.copy(password = password)) }
    }

    fun updateName(nombre: String) {
        _state.update { it.copy(user = it.user?.copy(nombre = nombre)) }
    }

    fun login() {
        val user = _state.value.user ?: return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val result = loginUseCase(user)

                registerFCMTokenUseCase()

                _state.update {
                    it.copy(
                        isLoading = false,
                        user = result,
                        isAuthenticated = true
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message ?: "Error desconocido") }
            }
        }
    }

    fun register() {
        val user = _state.value.user ?: return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val result = registerUseCase(user)

                registerFCMTokenUseCase()

                _state.update {
                    it.copy(
                        isLoading = false,
                        user = result,
                        isAuthenticated = true
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message ?: "Error desconocido") }
            }
        }
    }
}

