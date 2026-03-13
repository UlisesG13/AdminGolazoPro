package com.ulisesg.admingolazopro.features.employee.domain.usecases

import com.ulisesg.admingolazopro.features.employee.domain.entities.Employee
import com.ulisesg.admingolazopro.features.employee.domain.repositories.EmployeeRepository
import javax.inject.Inject

class GetEmployeeProfileUseCase @Inject constructor(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(): Result<Employee> {
        return repository.getEmployeeProfile()
    }
}
