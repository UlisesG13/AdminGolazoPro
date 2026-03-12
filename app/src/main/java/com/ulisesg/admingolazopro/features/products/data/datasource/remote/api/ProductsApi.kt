package com.ulisesg.admingolazopro.features.products.data.datasource.remote.api

import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.ProductResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductsApi {

    @GET("products")
    suspend fun getProducts(): List<ProductResponse>

    @POST("products")
    suspend fun createProduct(
        @Body request: ProductResponse
    ): ProductResponse

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: String,
        @Body request: ProductResponse
    ): ProductResponse

    @DELETE("products/{id}")
    suspend fun deleteProduct(
        @Path("id") id: String
    )
}

