package com.ulisesg.admingolazopro.features.products.domain.repositories

import com.ulisesg.admingolazopro.features.products.domain.entities.Product

interface ProductsRepository {
    suspend fun getProducts(): List<Product>
    suspend fun createProduct(product: Product): Result<Product>
    suspend fun updateProduct(product: Product): Result<Product>
    suspend fun deleteProduct(id: String): Result<Unit>
}
