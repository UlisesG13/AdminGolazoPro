package com.ulisesg.admingolazopro.features.auth.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NameField(
    name: String,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Nombre Completo") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
        modifier = modifier.fillMaxWidth(),
        singleLine = true
    )
}
