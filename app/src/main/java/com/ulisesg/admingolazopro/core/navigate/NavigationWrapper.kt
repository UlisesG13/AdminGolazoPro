package com.ulisesg.admingolazopro.core.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ulisesg.admingolazopro.features.auth.presentation.screens.LoginScreen
import com.ulisesg.admingolazopro.features.auth.presentation.screens.RegisterScreen
import com.ulisesg.admingolazopro.features.employee.presentation.screens.EmployeesScreen
import com.ulisesg.admingolazopro.features.home.screens.HomeScreen
import com.ulisesg.admingolazopro.features.products.presentation.screens.ProductsScreen

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
                onAddProduct = { /* aqui va la ruta tilapia manu */ },
                onEditProduct = { /* aqui va la ruta bagre manu*/ }
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
                    navController.navigate(Register)
                },
                onEditEmployee = { id -> 
                    navController.navigate(Register)
                }

            )
        }
    }
}
