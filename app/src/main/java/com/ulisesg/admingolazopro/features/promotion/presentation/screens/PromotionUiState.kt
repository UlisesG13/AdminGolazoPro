package com.ulisesg.admingolazopro.features.promotion.presentation.screens

import com.ulisesg.admingolazopro.features.promotion.domain.entities.Promotion

data class PromotionUiState(
    val isLoading: Boolean = false,
    val promotions: List<Promotion> = emptyList(),
    val error: String? = null,
    val isSuccess: Boolean = false
)
