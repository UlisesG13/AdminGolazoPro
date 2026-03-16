package com.ulisesg.admingolazopro.features.products.presentation.viewmodels

import com.ulisesg.admingolazopro.features.products.domain.entities.Product

data class ProductsUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String? = null,
    val isSuccess: Boolean = false
)
