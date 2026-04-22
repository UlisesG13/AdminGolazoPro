package com.ulisesg.admingolazopro.features.products.data.datasource.local

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ulisesg.admingolazopro.core.database.backup.DatabaseBackupWorker
import com.ulisesg.admingolazopro.features.products.data.datasource.local.dao.ProductDao
import com.ulisesg.admingolazopro.features.products.data.datasource.local.entity.ProductEntity
import javax.inject.Inject
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext

class ProductsLocalDataSource @Inject constructor(
    private val dao: ProductDao,
    @ApplicationContext private val context: Context
) {

    private fun triggerBackup() {
        val backupRequest = OneTimeWorkRequestBuilder<DatabaseBackupWorker>().build()
        WorkManager.getInstance(context).enqueue(backupRequest)
    }

    suspend fun getProducts(): List<ProductEntity> {
        return dao.getProducts()
    }

    suspend fun getProductById(id: String): ProductEntity? {
        return dao.getProductById(id)
    }

    suspend fun insertProducts(products: List<ProductEntity>) {
        dao.insertProducts(products)
        triggerBackup()
    }

    suspend fun insertProduct(product: ProductEntity) {
        dao.insertProduct(product)
        triggerBackup()
    }

    suspend fun deleteProduct(id: String) {
        dao.deleteProduct(id)
    }

    suspend fun clearProducts() {
        dao.clear()
    }
}

