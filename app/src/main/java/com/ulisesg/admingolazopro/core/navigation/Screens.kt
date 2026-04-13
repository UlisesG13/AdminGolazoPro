package com.ulisesg.admingolazopro.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Register

@Serializable
object Products

@Serializable
data class ProductDetail(val productId: String)

@Serializable
object ProductCreate

@Serializable
data class ProductEdit(val productId: String)

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

