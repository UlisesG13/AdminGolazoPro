package com.ulisesg.admingolazopro.features.employee.data.datasource.remote.api

import com.ulisesg.admingolazopro.features.employee.data.datasource.remote.models.EmployeeResponse
import com.ulisesg.admingolazopro.features.employee.data.datasource.remote.models.RegisterEmployeeRequest
import com.ulisesg.admingolazopro.features.employee.data.datasource.remote.models.UpdateEmployeeRequest
import retrofit2.http.*

interface EmployeeApi {

    @GET("users/")
    suspend fun getEmployees(@Query("page") page: Int = 1): List<EmployeeResponse>

    @GET("users/by-email/{email}/")
    suspend fun getEmployeeByEmail(@Path("email") email: String): EmployeeResponse

    @GET("users/{id}/")
    suspend fun getEmployeeById(@Path("id") id: String): EmployeeResponse

    @POST("users/admin/")
    suspend fun registerEmployee(@Body request: RegisterEmployeeRequest): EmployeeResponse

    @PUT("users/{id}/")
    suspend fun updateEmployee(
        @Path("id") id: String,
        @Body request: UpdateEmployeeRequest
    ): EmployeeResponse

    @DELETE("users/{id}")
    suspend fun deleteEmployeeById(@Path("id") id: String)

    @GET("users/profile")
    suspend fun getEmployeeProfile(): EmployeeResponse
}
