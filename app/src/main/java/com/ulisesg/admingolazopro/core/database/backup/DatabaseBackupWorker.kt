package com.ulisesg.admingolazopro.core.database.backup

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class DatabaseBackupWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        return try {
            val dbName = "app_database"
            val dbFile = applicationContext.getDatabasePath(dbName)
            
            if (dbFile.exists()) {
                val backupFile = File(applicationContext.getExternalFilesDir(null), "$dbName-backup.db")
                
                FileInputStream(dbFile).use { input ->
                    FileOutputStream(backupFile).use { output ->
                        input.copyTo(output)
                    }
                }
                Result.success()
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}

