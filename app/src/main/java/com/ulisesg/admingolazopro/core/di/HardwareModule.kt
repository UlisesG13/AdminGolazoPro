package com.ulisesg.admingolazopro.core.di

import com.ulisesg.admingolazopro.core.hardware.data.AndroidFlashManager
import com.ulisesg.admingolazopro.core.hardware.domain.FlashManager
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
    abstract fun bindFlashManager(
        impl: AndroidFlashManager
    ): FlashManager
}
