package com.ulisesg.admingolazopro.features.order.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ulisesg.admingolazopro.features.order.domain.entities.Order

@Composable
fun OrderCard(order: Order, onStatusChange: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Pedido #${order.id}", fontWeight = FontWeight.Bold)
                StatusBadge(order.estado)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Cliente ID: ${order.usuarioId}")
            Text("Total: $${order.total ?: 0.0f}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.ExtraBold)
            
            if (!order.notas.isNullOrBlank()) {
                Text("Notas: ${order.notas}", style = MaterialTheme.typography.bodySmall)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { onStatusChange("procesando") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Procesar", style = MaterialTheme.typography.labelSmall)
                }
                Button(
                    onClick = { onStatusChange("completado") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Completar", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val color = when(status.lowercase()) {
        "pendiente" -> Color.Gray
        "procesando" -> Color(0xFFFF9800)
        "en_camino" -> Color(0xFF2196F3)
        "completado" -> Color(0xFF4CAF50)
        "cancelado" -> Color.Red
        else -> Color.DarkGray
    }
    Surface(color = color, shape = MaterialTheme.shapes.small) {
        Text(
            text = status.replace("_", " ").uppercase(),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
