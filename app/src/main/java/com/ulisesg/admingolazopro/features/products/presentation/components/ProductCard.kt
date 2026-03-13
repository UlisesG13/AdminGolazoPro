package com.ulisesg.admingolazopro.features.products.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ulisesg.admingolazopro.features.products.domain.entities.Product

data class ProductsUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String? = null,
    val isSuccess: Boolean = false
)

@Composable
fun ProductCard(
    product: Product,
    onDelete: (String) -> Unit,
    onEdit: (String) -> Unit,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val image = product.imagenes.firstOrNull()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(product.id) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column {

            AsyncImage(
                model = image?.path,
                contentDescription = product.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(12.dp)
            ) {

                Text(
                    text = product.nombre,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$${product.precio}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {

                    Button(
                        onClick = { onDelete(product.id) }
                    ) {
                        Text("Eliminar")
                    }
                    Button(
                        onClick = { onEdit(product.id) }
                    ) {
                        Text("Editar")
                    }
                }
            }
        }
    }
}

