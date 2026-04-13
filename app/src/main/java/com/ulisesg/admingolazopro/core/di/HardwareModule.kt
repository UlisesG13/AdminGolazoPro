package com.ulisesg.admingolazopro.core.di

import com.ulisesg.admingolazopro.core.device.data.AndroidImagePickerRepository
import com.ulisesg.admingolazopro.core.device.data.AndroidVibratorManager
import com.ulisesg.admingolazopro.core.device.domain.ImagePickerRepository
import com.ulisesg.admingolazopro.core.device.domain.VibratorRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HardwareModule {

    @Binds
    @Singleton
    abstract fun bindImagePickerRepository(
        impl: AndroidImagePickerRepository
    ): ImagePickerRepository

    @Binds
    @Singleton
    abstract fun bindVibratorManager(
        impl: AndroidVibratorManager
    ): VibratorRepository
}

