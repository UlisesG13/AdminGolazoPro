package com.ulisesg.admingolazopro.features.auth.domain.usecases

import com.ulisesg.admingolazopro.features.auth.domain.entities.User
import com.ulisesg.admingolazopro.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class Login @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        credentials: User
    ): User {
        return repository.login(credentials)
    }
}
