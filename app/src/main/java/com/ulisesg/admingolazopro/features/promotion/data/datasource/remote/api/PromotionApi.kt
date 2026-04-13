package com.ulisesg.admingolazopro.features.promotion.data.datasource.remote.api

import com.ulisesg.admingolazopro.features.promotion.data.datasource.remote.models.PromocionCreateDTO
import com.ulisesg.admingolazopro.features.promotion.data.datasource.remote.models.PromocionDTO
import com.ulisesg.admingolazopro.features.promotion.data.datasource.remote.models.PromocionUpdateDTO
import retrofit2.http.*

interface PromotionApi {
    
    @GET("promociones/")
    suspend fun getPromotions(): List<PromocionDTO>
    
    @GET("promociones/{promocion_id}")
    suspend fun getPromotionById(
        @Path("promocion_id") promocionId: Int
    ): PromocionDTO
    
    @POST("promociones/")
    suspend fun createPromotion(
        @Body request: PromocionCreateDTO
    ): PromocionDTO
    
    @PUT("promociones/{promocion_id}")
    suspend fun updatePromotion(
        @Path("promocion_id") promocionId: Int,
        @Body request: PromocionUpdateDTO
    ): PromocionDTO
    
    @DELETE("promociones/{promocion_id}")
    suspend fun deletePromotion(
        @Path("promocion_id") promocionId: Int
    )
    
    @POST("promociones/{promocion_id}/activar")
    suspend fun activatePromotion(
        @Path("promocion_id") promocionId: Int
    ): PromocionDTO
    
    @POST("promociones/{promocion_id}/desactivar")
    suspend fun deactivatePromotion(
        @Path("promocion_id") promocionId: Int
    ): PromocionDTO
}

