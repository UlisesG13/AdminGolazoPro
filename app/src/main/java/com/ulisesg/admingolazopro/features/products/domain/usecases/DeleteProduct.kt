package com.ulisesg.admingolazopro.features.products.domain.usecases

import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import javax.inject.Inject

class DeleteProduct @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return repository.deleteProduct(id)
    }
}
