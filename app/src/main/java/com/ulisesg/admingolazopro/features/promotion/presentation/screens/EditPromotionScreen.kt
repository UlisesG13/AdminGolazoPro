package com.ulisesg.admingolazopro.features.promotion.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ulisesg.admingolazopro.features.promotion.presentation.components.PromotionDatePicker
import com.ulisesg.admingolazopro.features.promotion.presentation.components.formatDateDisplay
import com.ulisesg.admingolazopro.features.promotion.presentation.components.formatToISO
import com.ulisesg.admingolazopro.features.promotion.presentation.components.toMillis
import com.ulisesg.admingolazopro.features.promotion.presentation.viewmodels.PromotionViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPromotionScreen(
    promotionId: Int,
    onNavigateBack: () -> Unit,
    viewModel: PromotionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val promotion = state.promotions.find { it.id == promotionId }

    var codigo by remember { mutableStateOf("") }
    var descuento by remember { mutableStateOf("") }
    var tipoDescuento by remember { mutableStateOf("porcentaje") }
    var usosMaximos by remember { mutableStateOf("") }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaExpiracion by remember { mutableStateOf("") }

    var showInicioPicker by remember { mutableStateOf(false) }
    var showExpiracionPicker by remember { mutableStateOf(false) }

    val datePickerStateInicio = rememberDatePickerState(
        initialSelectedDateMillis = fechaInicio.toMillis() ?: Calendar.getInstance().timeInMillis
    )
    val datePickerStateExpiracion = rememberDatePickerState(
        initialSelectedDateMillis = fechaExpiracion.toMillis() ?: Calendar.getInstance().timeInMillis
    )

    LaunchedEffect(promotion) {
        promotion?.let {
            codigo = it.codigo
            descuento = it.descuento.toString()
            tipoDescuento = it.tipoDescuento
            usosMaximos = it.usosMaximos.toString()
            fechaInicio = it.fechaInicio
            fechaExpiracion = it.fechaExpiracion
        }
    }

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
                title = { Text("Editar Promoción", fontWeight = FontWeight.Bold) },
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
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it },
                label = { Text("Código de Promoción") },
                leadingIcon = { Icon(Icons.Default.LocalOffer, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = descuento,
                    onValueChange = { descuento = it },
                    label = { Text("Descuento") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(1f)
                )
                
                Column(modifier = Modifier.weight(1f)) {
                    Text("Tipo", style = MaterialTheme.typography.labelMedium)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = tipoDescuento == "porcentaje", onClick = { tipoDescuento = "porcentaje" })
                        Text("%", style = MaterialTheme.typography.bodySmall)
                        RadioButton(selected = tipoDescuento == "monto_fijo", onClick = { tipoDescuento = "monto_fijo" })
                        Text("$", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            OutlinedTextField(
                value = usosMaximos,
                onValueChange = { usosMaximos = it },
                label = { Text("Usos Máximos") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = { Icon(Icons.Default.Numbers, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fechaInicio.formatDateDisplay(),
                onValueChange = { },
                label = { Text("Fecha Inicio") },
                leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showInicioPicker = true },
                enabled = false,
                readOnly = true,
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
                label = { Text("Fecha Expiración") },
                leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showExpiracionPicker = true },
                enabled = false,
                readOnly = true,
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
                Text(text = state.error!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Button(
                onClick = {
                    promotion?.let {
                        viewModel.updatePromotion(
                            promotionId,
                            it.copy(
                                codigo = codigo,
                                descuento = descuento.toFloatOrNull() ?: 0f,
                                tipoDescuento = tipoDescuento,
                                usosMaximos = usosMaximos.toIntOrNull() ?: 0,
                                fechaInicio = fechaInicio,
                                fechaExpiracion = fechaExpiracion
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                enabled = !state.isLoading && codigo.isNotBlank() && promotion != null
            ) {
                if (state.isLoading) CircularProgressIndicator(modifier = Modifier.size(24.dp))
                else Text("Guardar Cambios")
            }
        }
    }
}

