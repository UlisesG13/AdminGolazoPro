package com.ulisesg.admingolazopro.features.auth.domain.usecases

import com.ulisesg.admingolazopro.features.auth.domain.entities.User
import com.ulisesg.admingolazopro.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class Register @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        data: User
    ): User {
        return repository.register(data)
    }
}
