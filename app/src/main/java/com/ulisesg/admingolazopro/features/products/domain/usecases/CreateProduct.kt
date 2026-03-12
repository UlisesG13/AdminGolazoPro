package com.ulisesg.admingolazopro.features.products.domain.usecases

import com.ulisesg.admingolazopro.features.products.domain.entities.Product
import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import javax.inject.Inject

class CreateProduct @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(product: Product): Result<Product> {
        return repository.createProduct(product)
    }
}
