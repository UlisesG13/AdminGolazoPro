package com.ulisesg.admingolazopro.features.employee.domain.repositories

import com.ulisesg.admingolazopro.features.employee.domain.entities.Employee

interface EmployeeRepository {
    suspend fun getEmployees(page: Int = 1): Result<List<Employee>>
    suspend fun getEmployeeById(id: String): Result<Employee>
    suspend fun getEmployeeByEmail(email: String): Result<Employee>
    suspend fun createEmployee(employee: Employee, password: String): Result<Employee>
    suspend fun updateEmployee(id: String, employee: Employee): Result<Employee>
    suspend fun deleteEmployee(id: String): Result<Unit>
    suspend fun getEmployeeProfile(): Result<Employee>
}
