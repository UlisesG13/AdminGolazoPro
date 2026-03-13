package com.ulisesg.admingolazopro.core.navigate

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
object Home

@Serializable
object Employees

@Serializable
object Promotions

@Serializable
object Orders
