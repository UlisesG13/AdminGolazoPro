package com.ulisesg.admingolazopro.features.products.data.datasource.local

import com.ulisesg.admingolazopro.features.products.data.datasource.local.dao.ProductDao
import com.ulisesg.admingolazopro.features.products.data.datasource.local.entity.ProductEntity
import javax.inject.Inject

class ProductsLocalDataSource @Inject constructor(
    private val dao: ProductDao
) {

    suspend fun getProducts(): List<ProductEntity> {
        return dao.getProducts()
    }

    suspend fun getProductById(id: String): ProductEntity? {
        return dao.getProductById(id)
    }

    suspend fun insertProducts(products: List<ProductEntity>) {
        dao.insertProducts(products)
    }

    suspend fun insertProduct(product: ProductEntity) {
        dao.insertProduct(product)
    }

    suspend fun deleteProduct(id: String) {
        dao.deleteProduct(id)
    }

    suspend fun clearProducts() {
        dao.clear()
    }
}

