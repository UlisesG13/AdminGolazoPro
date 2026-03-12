package com.ulisesg.admingolazopro.features.products.domain.repositories

import java.io.File

interface ImageRepository {
    suspend fun uploadImage(file: File): Result<String>
}
