package com.ulisesg.admingolazopro.features.products.data.repositories

import com.ulisesg.admingolazopro.features.products.data.datasource.remote.api.ProductsApi
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.mapper.ProductoMapper
import com.ulisesg.admingolazopro.features.products.domain.entities.Product
import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import jakarta.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val api: ProductsApi
) : ProductsRepository {

    override suspend fun getProducts(): List<Product> {
        val response = api.getProducts()
        return response.map { ProductoMapper.toDomain(it) }
    }

    override suspend fun getProductById(id: String): Product? {
        return try {
            val response = api.getProductById(id)
            ProductoMapper.toDomain(response)
        } catch (e: Exception) {
            print(e)
            null
        }
    }

    override suspend fun createProduct(product: Product): Result<Product> {
        return try {

            val request = ProductoMapper.toCreateRequest(product)

            val response = api.createProduct(request)

            Result.success(
                ProductoMapper.toDomain(response)
            )

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProduct(product: Product): Result<Product> {
        return try {

            val request = ProductoMapper.toUpdateRequest(product)

            val response = api.updateProduct(
                id = product.id,
                request = request
            )

            Result.success(
                ProductoMapper.toDomain(response)
            )

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteProduct(id: String): Result<Unit> {
        return try {

            api.deleteProduct(id)

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changeDestacado(
        id: String,
        destacado: Boolean
    ): Result<Product> {

        return try {

            val response = api.changeDestacado(
                id = id,
                destacado = destacado
            )

            Result.success(
                ProductoMapper.toDomain(response)
            )

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    override suspend fun changeStatus(
        id: String,
        status: Boolean
    ): Result<Product> {

        return try {

            val response = api.changeStatus(
                id = id,
                status = status
            )

            Result.success(
                ProductoMapper.toDomain(response)
            )

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    override suspend fun getProductsByCategoria(categoriaId: Int): List<Product> {
        val response = api.getProductsByCategoria(categoriaId)
        return response.map { ProductoMapper.toDomain(it) }
    }
}