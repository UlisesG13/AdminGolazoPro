package com.ulisesg.admingolazopro.features.promotion.data.datasource.remote.mapper

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomBoxWithText(text: String) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomBoxWithTextPreview() {
    CustomBoxWithText(text = "¡Hola LICHI de pijijiapan!")
}
