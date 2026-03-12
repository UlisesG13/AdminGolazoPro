package com.ulisesg.admingolazopro.features.products.data.repositories

import com.ulisesg.admingolazopro.features.products.data.datasource.remote.api.ProductsApi
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.mapper.toDomain
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.mapper.toResponse
import com.ulisesg.admingolazopro.features.products.domain.entities.Product
import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val api: ProductsApi
) : ProductsRepository {

    override suspend fun getProducts(): List<Product> {
        return try {
            val response = api.getProducts()
            response.toDomain()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun createProduct(product: Product): Result<Product> = runCatching {
        val response = api.createProduct(product.toResponse())
        response.toDomain()
    }

    override suspend fun updateProduct(product: Product): Result<Product> = runCatching {
        val response = api.updateProduct(product.id, product.toResponse())
        response.toDomain()
    }

    override suspend fun deleteProduct(id: String): Result<Unit> = runCatching {
        api.deleteProduct(id)
    }
}
