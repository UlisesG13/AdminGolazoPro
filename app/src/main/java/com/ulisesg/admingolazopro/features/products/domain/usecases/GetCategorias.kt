package com.ulisesg.admingolazopro.features.products.domain.usecases

import com.ulisesg.admingolazopro.features.products.domain.entities.Category
import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import javax.inject.Inject

class GetCategorias @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(): List<Category> {
        return repository.getCategorias()
    }
}
