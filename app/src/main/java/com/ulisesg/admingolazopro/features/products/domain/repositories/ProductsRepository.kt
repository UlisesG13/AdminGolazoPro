package com.ulisesg.admingolazopro.features.products.domain.repositories

import com.ulisesg.admingolazopro.features.products.domain.entities.Product

interface ProductsRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProductById(id: String): Product?
    suspend fun createProduct(product: Product): Result<Product>
    suspend fun updateProduct(product: Product): Result<Product>
    suspend fun deleteProduct(id: String): Result<Unit>
    suspend fun changeDestacado(id: String, destacado: Boolean): Result<Product>
    suspend fun changeStatus(id: String, status: Boolean): Result<Product>
    suspend fun getProductsByCategoria(categoriaId: Int): List<Product>
}
