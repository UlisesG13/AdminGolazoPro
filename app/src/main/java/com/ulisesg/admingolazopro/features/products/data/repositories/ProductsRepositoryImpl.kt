package com.ulisesg.admingolazopro.features.products.data.repositories

import android.util.Log
import com.ulisesg.admingolazopro.features.products.data.datasource.local.ProductsLocalDataSource
import com.ulisesg.admingolazopro.features.products.data.datasource.local.mapper.toDomain
import com.ulisesg.admingolazopro.features.products.data.datasource.local.mapper.toEntity
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.api.ProductsApi
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.mapper.ProductoMapper
import com.ulisesg.admingolazopro.features.products.domain.entities.Product
import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import javax.inject.Inject

private const val TAG = "ProductsRepositoryImpl"

class ProductsRepositoryImpl @Inject constructor(
    private val api: ProductsApi,
    private val local: ProductsLocalDataSource
) : ProductsRepository {

    override suspend fun getProducts(): List<Product> {
        // 1. Intentamos obtener de la base de datos local primero
        val localProducts = local.getProducts().map { it.toDomain() }
        Log.d(TAG, "getProducts: Local products found: ${localProducts.size}")

        return try {
            // Intentamos refrescar desde la API
            Log.d(TAG, "getProducts: Fetching from API...")
            val response = api.getProducts()
            Log.d(TAG, "getProducts: API response received. Count: ${response.size}")
            
            val remoteProducts = response.map { ProductoMapper.toDomain(it) }
            Log.d(TAG, "getProducts: Mapped ${remoteProducts.size} products from API")

            // Actualizamos la caché local
            Log.d(TAG, "getProducts: Clearing local products and inserting new ones")
            local.clearProducts()
            local.insertProducts(remoteProducts.map { it.toEntity() })

            remoteProducts
        } catch (e: Exception) {
            Log.e(TAG, "getProducts: Error fetching from API", e)
            // Si la API falla, devolvemos lo que tengamos en local (aunque sea una lista vacía)
            localProducts
        }
    }

    override suspend fun getProductById(id: String): Product? {
        Log.d(TAG, "getProductById: id=$id")
        return try {
            val response = api.getProductById(id)
            Log.d(TAG, "getProductById: API success")
            ProductoMapper.toDomain(response)
        } catch (e: Exception) {
            Log.e(TAG, "getProductById: API error, trying local", e)
            local.getProductById(id)?.toDomain()
        }
    }

    override suspend fun createProduct(product: Product): Result<Product> {
        Log.d(TAG, "createProduct: $product")
        return try {
            val request = ProductoMapper.toCreateRequest(product)
            val response = api.createProduct(request)
            val createdProduct = ProductoMapper.toDomain(response)
            
            Log.d(TAG, "createProduct: Success, inserting in local")
            local.insertProducts(listOf(createdProduct.toEntity()))
            
            Result.success(createdProduct)
        } catch (e: Exception) {
            Log.e(TAG, "createProduct: Error", e)
            Result.failure(e)
        }
    }

    override suspend fun updateProduct(product: Product): Result<Product> {
        Log.d(TAG, "updateProduct: id=${product.id}")
        return try {
            val request = ProductoMapper.toUpdateRequest(product)
            val response = api.updateProduct(
                id = product.id,
                request = request
            )
            val updatedProduct = ProductoMapper.toDomain(response)
            
            Log.d(TAG, "updateProduct: Success, updating local")
            local.insertProducts(listOf(updatedProduct.toEntity()))

            Result.success(updatedProduct)
        } catch (e: Exception) {
            Log.e(TAG, "updateProduct: Error", e)
            Result.failure(e)
        }
    }

    override suspend fun deleteProduct(id: String): Result<Unit> {
        Log.d(TAG, "deleteProduct: id=$id")
        return try {
            api.deleteProduct(id)
            local.deleteProduct(id)
            Log.d(TAG, "deleteProduct: Success")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "deleteProduct: Error", e)
            Result.failure(e)
        }
    }

    override suspend fun changeDestacado(
        id: String,
        destacado: Boolean
    ): Result<Product> {
        Log.d(TAG, "changeDestacado: id=$id, destacado=$destacado")
        return try {
            val response = api.changeDestacado(
                id = id,
                destacado = destacado
            )
            val updatedProduct = ProductoMapper.toDomain(response)
            local.insertProducts(listOf(updatedProduct.toEntity()))
            Log.d(TAG, "changeDestacado: Success")
            
            Result.success(updatedProduct)
        } catch (e: Exception) {
            Log.e(TAG, "changeDestacado: Error", e)
            Result.failure(e)
        }
    }

    override suspend fun changeStatus(
        id: String,
        status: Boolean
    ): Result<Product> {
        Log.d(TAG, "changeStatus: id=$id, status=$status")
        return try {
            val response = api.changeStatus(
                id = id,
                status = status
            )
            val updatedProduct = ProductoMapper.toDomain(response)
            local.insertProducts(listOf(updatedProduct.toEntity()))
            Log.d(TAG, "changeStatus: Success")

            Result.success(updatedProduct)
        } catch (e: Exception) {
            Log.e(TAG, "changeStatus: Error", e)
            Result.failure(e)
        }
    }

    override suspend fun getProductsByCategoria(categoriaId: Int): List<Product> {
        Log.d(TAG, "getProductsByCategoria: categoriaId=$categoriaId")
        return try {
            val response = api.getProductsByCategoria(categoriaId)
            Log.d(TAG, "getProductsByCategoria: API response Count: ${response.size}")
            response.map { ProductoMapper.toDomain(it) }
        } catch (e: Exception) {
            Log.e(TAG, "getProductsByCategoria: Error", e)
            local.getProducts().filter { it.categoriaId == categoriaId }.map { it.toDomain() }
        }
    }
}