package com.ulisesg.admingolazopro.core.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ulisesg.admingolazopro.features.auth.presentation.screens.LoginScreen
import com.ulisesg.admingolazopro.features.auth.presentation.screens.RegisterScreen
import com.ulisesg.admingolazopro.features.employee.presentation.screens.CreateEmployeeScreen
import com.ulisesg.admingolazopro.features.employee.presentation.screens.EditEmployeeScreen
import com.ulisesg.admingolazopro.features.employee.presentation.screens.EmployeesScreen
import com.ulisesg.admingolazopro.features.home.screens.HomeScreen
import com.ulisesg.admingolazopro.features.products.presentation.screens.CreateProductScreen
import com.ulisesg.admingolazopro.features.order.presentation.screens.CustomBoxWithText as OrderPrueba
import com.ulisesg.admingolazopro.features.products.presentation.screens.ProductDetailScreen
import com.ulisesg.admingolazopro.features.products.presentation.screens.ProductsScreen
import com.ulisesg.admingolazopro.features.promotion.presentation.screens.CustomBoxWithText as PromotionPrueba
import com.ulisesg.admingolazopro.features.promotion.presentation.screens.CreatePromotionScreen
import com.ulisesg.admingolazopro.features.promotion.presentation.screens.EditPromotionScreen
import com.ulisesg.admingolazopro.features.promotion.presentation.screens.PromotionsScreen

@Composable
fun NavigationWrapper() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Login) {

        composable<Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Home)
                }
            )
        }

        composable<Register> {
            RegisterScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable<Products> {
            ProductsScreen(
                onProductClick = { id ->
                    navController.navigate(ProductDetail(productId = id))
                },
                onCreateProduct = { navController.navigate(ProductCreate) }
            )
        }

        composable<ProductDetail> { backStackEntry ->
            val detail: ProductDetail = backStackEntry.toRoute()
            ProductDetailScreen(
                productId = detail.productId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<ProductCreate> {
            CreateProductScreen(
                onProductCreated = {
                    navController.popBackStack()
                }
            )
        }

        composable<Home> {
            HomeScreen(
                onProduct = { navController.navigate(Products) },
                onPromotion = { navController.navigate(Promotions) },
                onEmployee = { navController.navigate(Employees) },
                onOrder = { navController.navigate(Orders) }
            )
        }

        composable<Employees> {
            EmployeesScreen(
                onAddEmployee = { 
                    navController.navigate(CreateEmployee)
                },
                onEditEmployee = { id -> 
                    navController.navigate(EditEmployee(employeeId = id))
                }
            )
        }
        composable<CreateEmployee> {
            CreateEmployeeScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable<EditEmployee> { backStackEntry ->
            val route: EditEmployee = backStackEntry.toRoute()
            EditEmployeeScreen(
                employeeId = route.employeeId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<Promotions> {
            PromotionsScreen(
                onAddPromotion = { navController.navigate(CreatePromotion) },
                onEditPromotion = { id -> navController.navigate(EditPromotion(promotionId = id)) }
            )
        }

        composable<CreatePromotion> {
            CreatePromotionScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<EditPromotion> { backStackEntry ->
            val route: EditPromotion = backStackEntry.toRoute()
            EditPromotionScreen(
                promotionId = route.promotionId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
