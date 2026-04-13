package com.ulisesg.admingolazopro.features.employee.presentation.screens

import com.ulisesg.admingolazopro.features.employee.domain.entities.Employee

data class EmployeeUiState(
    val isLoading: Boolean = false,
    val employees: List<Employee> = emptyList(),
    val error: String? = null,
    val isSuccess: Boolean = false
)
