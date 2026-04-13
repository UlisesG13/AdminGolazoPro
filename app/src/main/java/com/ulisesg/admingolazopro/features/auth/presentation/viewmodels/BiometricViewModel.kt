package com.ulisesg.admingolazopro.features.auth.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class BiometricViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    fun canAuthenticate(): Boolean {
        val biometricManager = BiometricManager.from(context)
        val status = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )
        Log.d("BIOMETRIC_DEBUG", "CanAuthenticate Status: $status")
        
        when (status) {
            BiometricManager.BIOMETRIC_SUCCESS -> Log.d("BIOMETRIC_DEBUG", "La app puede autenticar usando biometría.")
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> Log.e("BIOMETRIC_DEBUG", "No hay hardware biométrico disponible en este dispositivo.")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> Log.e("BIOMETRIC_DEBUG", "Las funciones biométricas no están disponibles actualmente.")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> Log.e("BIOMETRIC_DEBUG", "El usuario no tiene credenciales biométricas asociadas.")
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> Log.e("BIOMETRIC_DEBUG", "Se requiere una actualización de seguridad.")
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> Log.e("BIOMETRIC_DEBUG", "Biometría no soportada.")
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> Log.e("BIOMETRIC_DEBUG", "Estado biométrico desconocido.")
        }
        
        return status == BiometricManager.BIOMETRIC_SUCCESS
    }

    fun authenticate(
        activity: FragmentActivity,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.e("BIOMETRIC_DEBUG", "Error de autenticación: ($errorCode) $errString")
                    onError(errString.toString())
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Log.d("BIOMETRIC_DEBUG", "Autenticación exitosa")
                    onSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Log.w("BIOMETRIC_DEBUG", "Autenticación fallida (huella no reconocida, etc.)")
                    onError("Autenticación fallida")
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación biométrica")
            .setSubtitle("Inicia sesión usando tu huella o rostro")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}
