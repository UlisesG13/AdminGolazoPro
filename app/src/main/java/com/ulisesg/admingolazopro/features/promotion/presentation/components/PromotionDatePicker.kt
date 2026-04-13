package com.ulisesg.admingolazopro.features.promotion.presentation.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromotionDatePicker(
    state: DatePickerState,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (Long?) -> Unit
) {
    if (isOpen) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    onConfirm(state.selectedDateMillis)
                    onDismiss()
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = state)
        }
    }
}

fun Long.formatToISO(): String {
    val date = Date(this)
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return format.format(date)
}

fun String.formatDateDisplay(): String {
    if (this.isBlank()) return ""
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = inputFormat.parse(this)
        if (date != null) outputFormat.format(date) else this
    } catch (e: Exception) {
        this
    }
}

fun String.toMillis(): Long? {
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        format.parse(this)?.time
    } catch (e: Exception) {
        null
    }
}

