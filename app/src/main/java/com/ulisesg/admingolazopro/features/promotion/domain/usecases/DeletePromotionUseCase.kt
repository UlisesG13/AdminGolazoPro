package com.ulisesg.admingolazopro.features.promotion.domain.usecases

import com.ulisesg.admingolazopro.features.promotion.domain.repositories.PromotionRepository
import javax.inject.Inject

class DeletePromotionUseCase @Inject constructor(
    private val repository: PromotionRepository
) {
    suspend operator fun invoke(id: Int): Result<Unit> {
        return repository.deletePromotion(id)
    }
}

