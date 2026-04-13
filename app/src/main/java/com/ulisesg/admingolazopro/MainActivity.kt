package com.ulisesg.admingolazopro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ulisesg.admingolazopro.core.database.backup.DatabaseBackupWorker
import com.ulisesg.admingolazopro.core.ui.theme.AppTheme
import com.ulisesg.admingolazopro.core.navigation.NavigationWrapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Ejecutar Respaldo de Base de Datos (Req 9)
        val backupRequest = OneTimeWorkRequestBuilder<DatabaseBackupWorker>().build()
        WorkManager.getInstance(this).enqueue(backupRequest)

        setContent {
            AppTheme {
                NavigationWrapper()
            }
        }
    }
}

