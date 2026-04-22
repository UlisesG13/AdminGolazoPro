package com.ulisesg.admingolazopro.core.database.backup

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ulisesg.admingolazopro.features.products.data.datasource.local.dao.ProductDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.File

@HiltWorker
class DatabaseBackupWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val productDao: ProductDao
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            // 1. Obtener productos de la DB
            val products = productDao.getAllProductsSync()
            println("DEBUG: Cantidad de productos encontrados: ${products.size}") // Ver en Logcat
            // 2. Crear el contenido del backup
            val backupContent = StringBuilder()
            backupContent.append("RESPALDO DE PRODUCTOS\n")
            backupContent.append("=====================\n\n")

            products.forEach { product ->
                backupContent.append("ID: ${product.id}\n")
                backupContent.append("Nombre: ${product.nombre}\n")
                backupContent.append("Precio: ${product.precio}\n")
                backupContent.append("Descripción: ${product.descripcion}\n")
                backupContent.append("Categoría ID: ${product.categoriaId}\n")
                backupContent.append("Estado: ${if (product.estaActivo) "Activo" else "Inactivo"}\n")
                
                // Lógica solicitada para la imagen
                backupContent.append("Imagen: [Imagen de ${product.nombre}]\n")
                
                backupContent.append("---------------------\n")
            }

            backupContent.append("\nRESPALDO FINALIZADO - FECHA: ${java.util.Date()}\n")

            // 3. Guardar en un archivo .txt en el almacenamiento externo de la app
            val backupFile = File(applicationContext.getExternalFilesDir(null), "productos_backup.txt")
            backupFile.writeText(backupContent.toString())

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}
