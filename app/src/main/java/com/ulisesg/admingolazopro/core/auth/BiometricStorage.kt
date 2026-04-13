package com.ulisesg.admingolazopro.core.auth

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("DEPRECATION")
@Singleton
class BiometricStorage @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    // MasterKey y EncryptedSharedPreferences aparecen como deprecated en la versión 1.1.0
    // pero siguen siendo el estándar oficial para almacenamiento cifrado simple.
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "biometric_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveCredentials(email: String, password: String) {
        sharedPreferences.edit {
            putString("saved_email", email)
            putString("saved_password", password)
        }
    }

    fun getCredentials(): Pair<String, String>? {
        val email = sharedPreferences.getString("saved_email", null)
        val password = sharedPreferences.getString("saved_password", null)
        return if (email != null && password != null) {
            Pair(email, password)
        } else {
            null
        }
    }

    fun clearCredentials() {
        sharedPreferences.edit { clear() }
    }
}
