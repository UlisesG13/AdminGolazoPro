package com.ulisesg.admingolazopro.features.employee.domain.usecases

import com.ulisesg.admingolazopro.features.employee.domain.repositories.EmployeeRepository
import javax.inject.Inject

class DeleteEmployeeUseCase @Inject constructor(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return repository.deleteEmployee(id)
    }
}

