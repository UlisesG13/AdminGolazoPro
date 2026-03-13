package com.ulisesg.admingolazopro.features.employee.data.di

import com.ulisesg.admingolazopro.features.employee.data.datasource.remote.api.EmployeeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EmployeeNetworkModule {

    @Provides
    @Singleton
    fun provideEmployeeApi(retrofit: Retrofit): EmployeeApi {
        return retrofit.create(EmployeeApi::class.java)
    }
}
