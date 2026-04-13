package com.ulisesg.admingolazopro.features.promotion.domain.usecases

import com.ulisesg.admingolazopro.features.promotion.domain.entities.Promotion
import com.ulisesg.admingolazopro.features.promotion.domain.repositories.PromotionRepository
import javax.inject.Inject

class ActivatePromotionUseCase @Inject constructor(
    private val repository: PromotionRepository
) {
    suspend operator fun invoke(id: Int): Result<Promotion> {
        return repository.activatePromotion(id)
    }
}

