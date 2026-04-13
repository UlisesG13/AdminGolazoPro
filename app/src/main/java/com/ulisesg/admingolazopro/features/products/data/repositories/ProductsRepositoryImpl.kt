package com.ulisesg.admingolazopro.features.products.data.repositories

import android.util.Log
import com.ulisesg.admingolazopro.features.products.data.datasource.local.ProductsLocalDataSource
import com.ulisesg.admingolazopro.features.products.data.datasource.local.mapper.toDomain
import com.ulisesg.admingolazopro.features.products.data.datasource.local.mapper.toEntity
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.api.ProductsApi
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.mapper.CategoriaMapper
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.mapper.ProductMapper
import com.ulisesg.admingolazopro.features.products.domain.entities.Category
import com.ulisesg.admingolazopro.features.products.domain.entities.Product
import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import javax.inject.Inject

private const val TAG = "ProductsRepositoryImpl"

class ProductsRepositoryImpl @Inject constructor(
    private val api: ProductsApi,
    private val local: ProductsLocalDataSource
) : ProductsRepository {

    override suspend fun getProducts(): List<Product> {
        val localProducts = local.getProducts().map { it.toDomain() }
        return try {
            val response = api.getProducts()
            val remoteProducts = response.map { ProductMapper.toDomain(it) }
            local.clearProducts()
            local.insertProducts(remoteProducts.map { it.toEntity() })
            remoteProducts
        } catch (e: Exception) {
            localProducts
        }
    }

    override suspend fun getProductById(id: String): Result<Product> {
        return try {
            val response = api.getProductById(id)
            val product = ProductMapper.toDomain(response)
            Result.success(product)
        } catch (e: Exception) {
            local.getProductById(id)?.toDomain()?.let { Result.success(it) } ?: Result.failure(e)
        }
    }

    override suspend fun createProduct(product: Product): Result<Product> {
        return try {
            val request = ProductMapper.toCreateRequest(product)
            val response = api.createProduct(request)
            val createdProduct = ProductMapper.toDomain(response)
            local.insertProducts(listOf(createdProduct.toEntity()))
            Result.success(createdProduct)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProduct(product: Product): Result<Product> {
        return try {
            val request = ProductMapper.toUpdateRequest(product)
            val response = api.updateProduct(id = product.id, request = request)
            val updatedProduct = ProductMapper.toDomain(response)
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

    override suspend fun changeDestacado(id: String, destacado: Boolean): Result<Product> {
        return try {
            val response = api.changeDestacado(id = id, destacado = destacado)
            val updatedProduct = ProductMapper.toDomain(response)
            local.insertProducts(listOf(updatedProduct.toEntity()))
            Result.success(updatedProduct)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changeStatus(id: String, status: Boolean): Result<Product> {
        return try {
            val response = api.changeStatus(id = id, status = status)
            val updatedProduct = ProductMapper.toDomain(response)
            local.insertProducts(listOf(updatedProduct.toEntity()))
            Result.success(updatedProduct)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductsByCategoria(categoriaId: Int): List<Product> {
        return try {
            val response = api.getProductsByCategoria(categoriaId)
            Log.d(TAG, "getProductsByCategoria: $response")
            response.map { ProductMapper.toDomain(it) }
        } catch (e: Exception) {
            local.getProducts().filter { it.categoriaId == categoriaId }.map { it.toDomain() }
        }
    }

    override suspend fun getCategorias(): List<Category> {
        return try {
            // api.getCategorias().map { Category(it.categoria_id, it.nombre, it.categoria_id) }
            val response = api.getCategorias()
            Log.d(TAG, "getCategorias: $response")
            response.map {
                CategoriaMapper.toDomain(it)
            }
        } catch (e: Exception) {
            Log.e(TAG, "getCategorias: Error", e)
            emptyList()
        }
    }
}

