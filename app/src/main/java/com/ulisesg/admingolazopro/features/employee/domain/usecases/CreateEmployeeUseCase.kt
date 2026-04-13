package com.ulisesg.admingolazopro.features.employee.domain.usecases

import com.ulisesg.admingolazopro.features.employee.domain.entities.Employee
import com.ulisesg.admingolazopro.features.employee.domain.repositories.EmployeeRepository
import javax.inject.Inject

class CreateEmployeeUseCase @Inject constructor(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(employee: Employee, password: String): Result<Employee> {
        // Validaciones
        require(employee.nombre.isNotEmpty()) { "Nombre es requerido" }
        require(employee.email.isNotEmpty()) { "Email es requerido" }
        require(isValidEmail(employee.email)) { "Email inválido" }
        require(password.length >= 6) { "Contraseña debe tener mínimo 6 caracteres" }

        return repository.createEmployee(employee, password)
    }

    private fun isValidEmail(email: String): Boolean {
        return email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))
    }
}

