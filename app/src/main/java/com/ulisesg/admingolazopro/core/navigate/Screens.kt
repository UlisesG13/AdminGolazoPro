package com.ulisesg.admingolazopro.core.navigate

import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Register

@Serializable
object Products

@Serializable
object Home

@Serializable
object Employees

@Serializable
object CreateEmployee

@Serializable
data class EditEmployee(val employeeId: String)

@Serializable
object Promotions

@Serializable
object CreatePromotion

@Serializable
data class EditPromotion(val promotionId: Int)

@Serializable
object Orders