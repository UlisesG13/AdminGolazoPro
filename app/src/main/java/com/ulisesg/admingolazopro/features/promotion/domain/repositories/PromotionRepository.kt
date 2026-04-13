package com.ulisesg.admingolazopro.features.promotion.domain.repositories

import com.ulisesg.admingolazopro.features.promotion.domain.entities.Promotion

interface PromotionRepository {
    suspend fun getPromotions(): Result<List<Promotion>>
    suspend fun getPromotionById(id: Int): Result<Promotion>
    suspend fun createPromotion(promotion: Promotion): Result<Promotion>
    suspend fun updatePromotion(id: Int, promotion: Promotion): Result<Promotion>
    suspend fun deletePromotion(id: Int): Result<Unit>
    suspend fun activatePromotion(id: Int): Result<Promotion>
    suspend fun deactivatePromotion(id: Int): Result<Promotion>
}

