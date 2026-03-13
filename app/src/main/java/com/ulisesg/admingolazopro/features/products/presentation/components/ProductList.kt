package com.ulisesg.admingolazopro.features.products.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.ulisesg.admingolazopro.features.products.domain.entities.Product

@Composable
fun ProductsList(
    products: List<Product>,
    onDelete: (String) -> Unit,
    onProductClick: (String) -> Unit
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {

        items(products.size) { index ->

            val product = products[index]

            ProductCard(
                product = product,
                onDelete = onDelete,
                onClick = onProductClick
            )
        }
    }
}