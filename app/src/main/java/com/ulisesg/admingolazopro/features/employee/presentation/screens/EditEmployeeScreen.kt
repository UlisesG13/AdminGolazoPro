package com.ulisesg.admingolazopro.features.employee.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ulisesg.admingolazopro.features.employee.presentation.viewmodels.EmployeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEmployeeScreen(
    employeeId: String,
    onNavigateBack: () -> Unit,
    viewModel: EmployeeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    
    // Buscar al empleado actual en la lista del estado
    val employee = state.employees.find { it.usuario_id == employeeId }
    
    var nombre by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("") }
    var estaActivo by remember { mutableStateOf(true) }

    // Sincronizar el estado local con el empleado encontrado
    LaunchedEffect(employee) {
        employee?.let {
            nombre = it.nombre
            rol = it.rol
            estaActivo = it.is_authenticated ?: true
        }
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            viewModel.resetSuccess()
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Empleado") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = rol,
                onValueChange = { rol = it },
                label = { Text("Rol (administrador/vendedor)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = estaActivo,
                    onCheckedChange = { estaActivo = it }
                )
                Text("Usuario Activo")
            }

            Spacer(modifier = Modifier.weight(1f))

            if (state.error != null) {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = {
                    val updatedEmployee = employee?.copy(
                        nombre = nombre,
                        rol = rol,
                        is_authenticated = estaActivo
                    )
                    if (updatedEmployee != null) {
                        viewModel.updateEmployee(employeeId, updatedEmployee)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading && employee != null
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Guardar Cambios")
                }
            }
        }
    }
}
