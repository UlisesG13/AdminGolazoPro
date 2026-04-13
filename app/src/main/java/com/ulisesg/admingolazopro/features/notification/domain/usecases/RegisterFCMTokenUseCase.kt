package com.ulisesg.admingolazopro.features.notification.domain.usecases

import com.ulisesg.admingolazopro.features.notification.domain.repositories.FCMRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterFCMTokenUseCase @Inject constructor(
    private val repository: FCMRepository
) {
    operator fun invoke() {
        repository.getToken { token ->
            token?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    repository.registerToken(it)
                }
            }
        }
    }
}
