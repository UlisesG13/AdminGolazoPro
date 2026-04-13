package com.ulisesg.admingolazopro.features.promotion.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ulisesg.admingolazopro.features.promotion.domain.entities.Promotion
import com.ulisesg.admingolazopro.features.promotion.presentation.components.PromotionDatePicker
import com.ulisesg.admingolazopro.features.promotion.presentation.components.formatDateDisplay
import com.ulisesg.admingolazopro.features.promotion.presentation.components.formatToISO
import com.ulisesg.admingolazopro.features.promotion.presentation.viewmodels.PromotionViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePromotionScreen(
    onNavigateBack: () -> Unit,
    viewModel: PromotionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    var codigo by remember { mutableStateOf("") }
    var descuento by remember { mutableStateOf("") }
    var tipoDescuento by remember { mutableStateOf("porcentaje") }
    var usosMaximos by remember { mutableStateOf("") }
    
    var fechaInicio by remember { mutableStateOf(Calendar.getInstance().timeInMillis.formatToISO()) }
    var fechaExpiracion by remember { 
        mutableStateOf(Calendar.getInstance().apply { add(Calendar.MONTH, 1) }.timeInMillis.formatToISO()) 
    }

    var showInicioPicker by remember { mutableStateOf(false) }
    var showExpiracionPicker by remember { mutableStateOf(false) }
    
    val datePickerStateInicio = rememberDatePickerState(
        initialSelectedDateMillis = Calendar.getInstance().timeInMillis
    )
    val datePickerStateExpiracion = rememberDatePickerState(
        initialSelectedDateMillis = Calendar.getInstance().apply { add(Calendar.MONTH, 1) }.timeInMillis
    )

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            viewModel.resetSuccess()
            onNavigateBack()
        }
    }

    PromotionDatePicker(
        state = datePickerStateInicio,
        isOpen = showInicioPicker,
        onDismiss = { showInicioPicker = false },
        onConfirm = { it?.let { fechaInicio = it.formatToISO() } }
    )

    PromotionDatePicker(
        state = datePickerStateExpiracion,
        isOpen = showExpiracionPicker,
        onDismiss = { showExpiracionPicker = false },
        onConfirm = { it?.let { fechaExpiracion = it.formatToISO() } }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Promoción", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.LocalOffer, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.primary)
                    Text("Configura el cupón", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            }

            OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it.uppercase() },
                label = { Text("Código (Ej: GOLAZO20)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Default.ConfirmationNumber, contentDescription = null) }
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = descuento,
                    onValueChange = { if (it.all { char -> char.isDigit() || char == '.' }) descuento = it },
                    label = { Text("Valor") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text("Tipo", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = tipoDescuento == "porcentaje", onClick = { tipoDescuento = "porcentaje" })
                        Text("%", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.width(8.dp))
                        RadioButton(selected = tipoDescuento == "monto_fijo", onClick = { tipoDescuento = "monto_fijo" })
                        Text("$", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            OutlinedTextField(
                value = usosMaximos,
                onValueChange = { if (it.all { char -> char.isDigit() }) usosMaximos = it },
                label = { Text("Límite de usos") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Default.Group, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = fechaInicio.formatDateDisplay(),
                onValueChange = { },
                label = { Text("Fecha de Inicio") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showInicioPicker = true },
                enabled = false,
                readOnly = true,
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null) },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            )

            OutlinedTextField(
                value = fechaExpiracion.formatDateDisplay(),
                onValueChange = { },
                label = { Text("Fecha de Expiración") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showExpiracionPicker = true },
                enabled = false,
                readOnly = true,
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Default.Event, contentDescription = null) },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            if (state.error != null) {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                    Text(text = "Error: ${state.error}", modifier = Modifier.padding(8.dp), color = MaterialTheme.colorScheme.onErrorContainer, style = MaterialTheme.typography.bodySmall)
                }
            }

            Button(
                onClick = {
                    viewModel.createPromotion(
                        Promotion(
                            codigo = codigo,
                            descuento = descuento.toFloatOrNull() ?: 0f,
                            tipoDescuento = tipoDescuento,
                            usosMaximos = usosMaximos.toIntOrNull() ?: 0,
                            fechaInicio = fechaInicio,
                            fechaExpiracion = fechaExpiracion,
                            estaActiva = false
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = !state.isLoading && codigo.isNotBlank() && descuento.isNotBlank()
            ) {
                if (state.isLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                else Text("Crear Cupón de Descuento", fontWeight = FontWeight.Bold)
            }
        }
    }
}

