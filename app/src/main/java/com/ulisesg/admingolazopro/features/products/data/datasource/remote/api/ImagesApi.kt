package com.ulisesg.admingolazopro.features.products.data.datasource.remote.api

import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.ImagenResponse
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.ProductoImagenCreateRequest
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.ProductoImagenResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ImagesApi {

    @Multipart
    @POST("imagenes")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("orden") orden: RequestBody
    ): ImagenResponse

    @POST("imagenes/producto")
    suspend fun asociarImagen(
        @Body request: ProductoImagenCreateRequest
    ): ProductoImagenResponse

    @GET("imagenes/producto/{productoId}")
    suspend fun getImagenesProducto(
        @Path("productoId") productoId: String
    ): List<ImagenResponse>

    @DELETE("imagenes/producto/{productoId}/{imagenId}")
    suspend fun desasociarImagen(
        @Path("productoId") productoId: String,
        @Path("imagenId") imagenId: Int
    )

    @DELETE("imagenes/producto/{productoId}")
    suspend fun eliminarImagenesProducto(
        @Path("productoId") productoId: String
    )

    @DELETE("imagenes/{imagenId}")
    suspend fun eliminarImagen(
        @Path("imagenId") imagenId: Int
    )
}