package com.ulisesg.admingolazopro.features.products.data.repositories

import com.ulisesg.admingolazopro.features.products.data.datasource.local.ProductsLocalDataSource
import com.ulisesg.admingolazopro.features.products.data.datasource.local.mapper.toDomain
import com.ulisesg.admingolazopro.features.products.data.datasource.local.mapper.toEntity
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.api.ProductsApi
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.mapper.ProductoMapper
import com.ulisesg.admingolazopro.features.products.domain.entities.Product
import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val api: ProductsApi,
    private val local: ProductsLocalDataSource
) : ProductsRepository {

    override suspend fun getProducts(): List<Product> {
        return try {
            val response = api.getProducts()
            val products = response.map { ProductoMapper.toDomain(it) }

            local.clearProducts()
            // Convertimos la lista de Product a List<ProductEntity> antes de insertar
            local.insertProducts(products.map { it.toEntity() })

            products
        } catch (e: Exception) {
            local.getProducts().map { it.toDomain() }
        }
    }

    override suspend fun getProductById(id: String): Product? {
        return try {
            val response = api.getProductById(id)
            ProductoMapper.toDomain(response)
        } catch (e: Exception) {
            local.getProductById(id)?.toDomain()
        }
    }

    override suspend fun createProduct(product: Product): Result<Product> {
        return try {
            val request = ProductoMapper.toCreateRequest(product)
            val response = api.createProduct(request)
            val createdProduct = ProductoMapper.toDomain(response)
            
            local.insertProducts(listOf(createdProduct.toEntity()))
            
            Result.success(createdProduct)
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
            val updatedProduct = ProductoMapper.toDomain(response)
            
            local.insertProducts(listOf(updatedProduct.toEntity()))

            Result.success(updatedProduct)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteProduct(id: String): Result<Unit> {
        return try {
            api.deleteProduct(id)
            local.deleteProduct(id)
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
            val updatedProduct = ProductoMapper.toDomain(response)
            local.insertProducts(listOf(updatedProduct.toEntity()))
            
            Result.success(updatedProduct)
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
            val updatedProduct = ProductoMapper.toDomain(response)
            local.insertProducts(listOf(updatedProduct.toEntity()))

            Result.success(updatedProduct)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductsByCategoria(categoriaId: Int): List<Product> {
        return try {
            val response = api.getProductsByCategoria(categoriaId)
            response.map { ProductoMapper.toDomain(it) }
        } catch (e: Exception) {
            local.getProducts().filter { it.categoriaId == categoriaId }.map { it.toDomain() }
        }
    }
}
