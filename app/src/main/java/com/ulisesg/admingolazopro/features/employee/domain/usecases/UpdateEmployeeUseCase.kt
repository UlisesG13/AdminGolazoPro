package com.ulisesg.admingolazopro.features.employee.domain.usecases

import com.ulisesg.admingolazopro.features.employee.domain.entities.Employee
import com.ulisesg.admingolazopro.features.employee.domain.repositories.EmployeeRepository
import javax.inject.Inject

class UpdateEmployeeUseCase @Inject constructor(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(id: String, employee: Employee): Result<Employee> {
        return repository.updateEmployee(id, employee)
    }
}
