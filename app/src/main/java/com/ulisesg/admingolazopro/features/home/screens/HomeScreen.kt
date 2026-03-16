package com.ulisesg.admingolazopro.features.home.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ulisesg.admingolazopro.features.home.components.HomeOptionCard

@Composable
fun HomeScreen(
    onProduct: () -> Unit,
    onPromotion: () -> Unit,
    onEmployee: () -> Unit,
    onOrder: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "AdminGolazo Pro",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Panel de control empresarial",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            HomeOptionCard(
                title = "Productos",
                description = "Administra el inventario y catálogo",
                icon = Icons.Default.Inventory,
                onClick = onProduct
            )

            HomeOptionCard(
                title = "Promociones",
                description = "Gestiona descuentos y ofertas",
                icon = Icons.Default.LocalOffer,
                onClick = onPromotion
            )

            HomeOptionCard(
                title = "Empleados",
                description = "Control de personal y roles",
                icon = Icons.Default.Badge,
                onClick = onEmployee
            )

            HomeOptionCard(
                title = "Órdenes",
                description = "Seguimiento de pedidos en tiempo real",
                icon = Icons.AutoMirrored.Filled.ListAlt,
                onClick = onOrder
            )
        }
    }
}
