package com.ulisesg.admingolazopro.features.products.domain.usecases

import com.ulisesg.admingolazopro.features.products.domain.entities.Product
import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import javax.inject.Inject

class GetProducts @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(): List<Product> {
        return repository.getProducts()
    }
}
