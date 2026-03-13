package com.ulisesg.admingolazopro.features.employee.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulisesg.admingolazopro.features.employee.domain.entities.Employee
import com.ulisesg.admingolazopro.features.employee.domain.usecases.*
import com.ulisesg.admingolazopro.features.employee.presentation.screens.EmployeeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val getEmployeesUseCase: GetEmployeesUseCase,
    private val createEmployeeUseCase: CreateEmployeeUseCase,
    private val updateEmployeeUseCase: UpdateEmployeeUseCase,
    private val deleteEmployeeUseCase: DeleteEmployeeUseCase,
    private val getEmployeeByEmailUseCase: GetEmployeeByEmailUseCase,
    private val getEmployeeProfileUseCase: GetEmployeeProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EmployeeUiState())
    val state: StateFlow<EmployeeUiState> = _state.asStateFlow()

    init {
        loadEmployees()
    }

    fun loadEmployees() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getEmployeesUseCase()
                .onSuccess { employees ->
                    _state.update { it.copy(isLoading = false, employees = employees) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message ?: "Error desconocido") }
                }
        }
    }

    fun createEmployee(employee: Employee, password: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, isSuccess = false) }
            createEmployeeUseCase(employee, password)
                .onSuccess {
                    _state.update { it.copy(isLoading = false, isSuccess = true) }
                    loadEmployees()
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }

    fun updateEmployee(id: String, employee: Employee) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, isSuccess = false) }
            updateEmployeeUseCase(id, employee)
                .onSuccess {
                    _state.update { it.copy(isLoading = false, isSuccess = true) }
                    loadEmployees()
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }

    fun deleteEmployee(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            deleteEmployeeUseCase(id)
                .onSuccess {
                    _state.update { it.copy(isLoading = false) }
                    loadEmployees()
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }

    fun getEmployeeByEmail(email: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getEmployeeByEmailUseCase(email)
                .onSuccess { employee ->
                    _state.update { it.copy(isLoading = false, employees = listOf(employee)) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }

    fun getProfile() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getEmployeeProfileUseCase()
                .onSuccess { employee ->
                    _state.update { it.copy(isLoading = false, employees = listOf(employee)) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }

    fun resetSuccess() {
        _state.update { it.copy(isSuccess = false) }
    }
}
