package com.ulisesg.admingolazopro.core.di.features

import com.ulisesg.admingolazopro.features.employee.data.repositories.EmployeeRepositoryImpl
import com.ulisesg.admingolazopro.features.employee.domain.repositories.EmployeeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EmployeeRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindEmployeeRepository(
        employeeRepositoryImpl: EmployeeRepositoryImpl
    ): EmployeeRepository
}


