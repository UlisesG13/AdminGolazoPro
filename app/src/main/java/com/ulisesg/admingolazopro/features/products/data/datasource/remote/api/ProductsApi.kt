package com.ulisesg.admingolazopro.features.products.data.datasource.remote.api

import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.CategoriaResponse
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.ProductoCreateRequest
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.ProductoResponse
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.ProductoUpdateRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsApi {

    @GET("productos")
    suspend fun getProducts(): List<ProductoResponse>

    @GET("productos/{id}")
    suspend fun getProductById(
        @Path("id") id: String
    ): ProductoResponse

    @GET("productos/by-categoria/{categoriaId}")
    suspend fun getProductsByCategoria(
        @Path("categoriaId") categoriaId: Int
    ): List<ProductoResponse>

    @POST("productos")
    suspend fun createProduct(
        @Body request: ProductoCreateRequest
    ): ProductoResponse

    @PUT("productos/{id}")
    suspend fun updateProduct(
        @Path("id") id: String,
        @Body request: ProductoUpdateRequest
    ): ProductoResponse

    @PUT("productos/{id}/alter-destacado")
    suspend fun changeDestacado(
        @Path("id") id: String,
        @Query("destacado") destacado: Boolean
    ): ProductoResponse

    @PUT("productos/{id}/alter-status")
    suspend fun changeStatus(
        @Path("id") id: String,
        @Query("status") status: Boolean
    ): ProductoResponse

    @DELETE("productos/{id}")
    suspend fun deleteProduct(
        @Path("id") id: String
    )

    @GET("categorias")
    suspend fun getCategorias(): List<CategoriaResponse>
}