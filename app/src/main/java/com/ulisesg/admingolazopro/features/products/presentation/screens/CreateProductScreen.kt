package com.ulisesg.admingolazopro.features.products.presentation.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ulisesg.admingolazopro.core.hardware.createImageUri
import com.ulisesg.admingolazopro.features.products.presentation.viewmodels.CreateProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProductScreen(
    viewModel: CreateProductViewModel = hiltViewModel(),
    onProductCreated: () -> Unit
) {

    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    if (state.success) {
        onProductCreated()
    }

    var cameraUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->

            uri?.let {
                viewModel.addImage(it.toString())
            }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->

            if (success) {
                cameraUri?.let {
                    viewModel.addImage(it.toString())
                }
            }
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Producto") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = state.nombre,
                onValueChange = viewModel::updateNombre,
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.precio,
                onValueChange = viewModel::updatePrecio,
                label = { Text("Precio") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.descripcion,
                onValueChange = viewModel::updateDescripcion,
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.categoriaId,
                onValueChange = viewModel::updateCategoria,
                label = { Text("Categoría ID") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = state.estaActivo,
                    onCheckedChange = { viewModel.toggleActivo() }
                )

                Text("Producto activo")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = state.estaDestacado,
                    onCheckedChange = { viewModel.toggleDestacado() }
                )

                Text("Producto destacado")
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { viewModel.createProduct() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {

                if (state.isLoading) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp)
                    )

                } else {

                    Text("Crear producto")
                }
            }

            state.error?.let {

                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Button(
                    onClick = {
                        galleryLauncher.launch("image/*")
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Galería")
                }

                Button(
                    onClick = {

                        val uri = createImageUri(context)

                        cameraUri = uri

                        cameraLauncher.launch(uri)

                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cámara")
                }
            }
        }
    }
}