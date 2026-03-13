package com.ulisesg.admingolazopro.features.home.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ulisesg.admingolazopro.features.home.components.HomeOptionCard

@Composable
fun HomeScreen(
    onProduct: () -> Unit,
    onPromotion: () -> Unit,
    onEmployee: () -> Unit,
    onOrder: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Panel de administración",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        HomeOptionCard(
            title = "Gestionar productos",
            onClick = { onProduct() }
        )

        HomeOptionCard(
            title = "Gestionar promociones",
            onClick = { onPromotion() }
        )

        HomeOptionCard(
            title = "Gestionar empleados",
            onClick = { onEmployee() }
        )

        HomeOptionCard(
            title = "Ver pedidos",
            onClick = { onOrder() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onProduct = {},
        onPromotion = {},
        onEmployee = {},
        onOrder = {}
    )
}
