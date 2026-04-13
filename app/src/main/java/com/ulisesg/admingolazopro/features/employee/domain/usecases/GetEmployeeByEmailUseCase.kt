package com.ulisesg.admingolazopro.features.employee.domain.usecases

import com.ulisesg.admingolazopro.features.employee.domain.entities.Employee
import com.ulisesg.admingolazopro.features.employee.domain.repositories.EmployeeRepository
import javax.inject.Inject

class GetEmployeeByEmailUseCase @Inject constructor(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(email: String): Result<Employee> {
        return repository.getEmployeeByEmail(email)
    }
}

