package com.ulisesg.admingolazopro.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulisesg.admingolazopro.core.auth.BiometricStorage
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
    private val registerFCMTokenUseCase: RegisterFCMTokenUseCase,
    private val biometricStorage: BiometricStorage
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

    fun loginWithBiometrics(onNoCredentials: () -> Unit) {
        val credentials = biometricStorage.getCredentials()
        if (credentials != null) {
            // Si hay credenciales, las ponemos en el estado e intentamos login
            updateEmail(credentials.first)
            updatePassword(credentials.second)
            login(saveForBiometrics = false) // No necesitamos volver a guardar
        } else {
            // Si no hay, avisamos a la UI para que pida los datos
            onNoCredentials()
        }
    }

    fun login(saveForBiometrics: Boolean = true) {
        val user = _state.value.user ?: return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val result = loginUseCase(user)

                // Si el login es exitoso y el flag está activo, guardamos para la próxima vez
                if (saveForBiometrics) {
                    biometricStorage.saveCredentials(user.email, user.password)
                }

                registerFCMTokenUseCase()
                _state.update { it.copy(isLoading = false, user = result, isAuthenticated = true) }
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
