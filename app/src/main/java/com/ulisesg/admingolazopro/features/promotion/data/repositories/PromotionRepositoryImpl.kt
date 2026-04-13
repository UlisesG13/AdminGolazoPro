package com.ulisesg.admingolazopro.features.promotion.data.repositories

import com.ulisesg.admingolazopro.features.promotion.data.datasource.remote.api.PromotionApi
import com.ulisesg.admingolazopro.features.promotion.data.datasource.remote.mapper.toCreateDto
import com.ulisesg.admingolazopro.features.promotion.data.datasource.remote.mapper.toDomain
import com.ulisesg.admingolazopro.features.promotion.data.datasource.remote.mapper.toUpdateDto
import com.ulisesg.admingolazopro.features.promotion.domain.entities.Promotion
import com.ulisesg.admingolazopro.features.promotion.domain.repositories.PromotionRepository
import javax.inject.Inject

class PromotionRepositoryImpl @Inject constructor(
    private val api: PromotionApi
) : PromotionRepository {

    override suspend fun getPromotions(): Result<List<Promotion>> = runCatching {
        api.getPromotions().toDomain()
    }

    override suspend fun getPromotionById(id: Int): Result<Promotion> = runCatching {
        api.getPromotionById(id).toDomain()
    }

    override suspend fun createPromotion(promotion: Promotion): Result<Promotion> = runCatching {
        api.createPromotion(promotion.toCreateDto()).toDomain()
    }

    override suspend fun updatePromotion(id: Int, promotion: Promotion): Result<Promotion> = runCatching {
        api.updatePromotion(id, promotion.toUpdateDto()).toDomain()
    }

    override suspend fun deletePromotion(id: Int): Result<Unit> = runCatching {
        api.deletePromotion(id)
    }

    override suspend fun activatePromotion(id: Int): Result<Promotion> = runCatching {
        api.activatePromotion(id).toDomain()
    }

    override suspend fun deactivatePromotion(id: Int): Result<Promotion> = runCatching {
        api.deactivatePromotion(id).toDomain()
    }
}

