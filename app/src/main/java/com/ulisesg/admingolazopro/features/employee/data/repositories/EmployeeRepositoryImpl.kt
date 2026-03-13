package com.ulisesg.admingolazopro.features.employee.data.repositories

import com.ulisesg.admingolazopro.features.employee.data.datasource.remote.api.EmployeeApi
import com.ulisesg.admingolazopro.features.employee.data.datasource.remote.mapper.toDomain
import com.ulisesg.admingolazopro.features.employee.data.datasource.remote.models.RegisterEmployeeRequest
import com.ulisesg.admingolazopro.features.employee.data.datasource.remote.models.UpdateEmployeeRequest
import com.ulisesg.admingolazopro.features.employee.domain.entities.Employee
import com.ulisesg.admingolazopro.features.employee.domain.repositories.EmployeeRepository
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    private val api: EmployeeApi
) : EmployeeRepository {

    override suspend fun getEmployees(page: Int): Result<List<Employee>> = runCatching {
        api.getEmployees().toDomain()
    }

    override suspend fun getEmployeeById(id: String): Result<Employee> = runCatching {
        api.getEmployeeById(id).toDomain()
    }

    override suspend fun getEmployeeByEmail(email: String): Result<Employee> = runCatching {
        api.getEmployeeByEmail(email).toDomain()
    }

    override suspend fun createEmployee(employee: Employee, password: String): Result<Employee> = runCatching {
        val request = RegisterEmployeeRequest(
            nombre = employee.nombre,
            email = employee.email,
            rol = employee.rol,
            password = password
        )
        api.registerEmployee(request).toDomain()
    }

    override suspend fun updateEmployee(id: String, employee: Employee): Result<Employee> = runCatching {
        val request = UpdateEmployeeRequest(
            nombre = employee.nombre,
            rol = employee.rol,
            estaActivo = employee.is_authenticated
        )
        api.updateEmployee(id, request).toDomain()
    }

    override suspend fun deleteEmployee(id: String): Result<Unit> = runCatching {
        api.deleteEmployeeById(id)
    }

    override suspend fun getEmployeeProfile(): Result<Employee> = runCatching {
        api.getEmployeeProfile().toDomain()
    }
}
