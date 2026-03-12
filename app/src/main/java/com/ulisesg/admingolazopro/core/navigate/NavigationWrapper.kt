package com.ulisesg.admingolazopro.core.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ulisesg.admingolazopro.features.auth.presentation.screens.LoginScreen
import com.ulisesg.admingolazopro.features.auth.presentation.screens.RegisterScreen

@Composable
fun NavigationWrapper() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Login) {

        composable<Login> {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Register)
                },
                onLoginSuccess = {
                    navController.navigate(Register) // Pendiente poner la ruta HOME
                }
            )
        }
        composable<Register> {
            RegisterScreen(
                onNavigateBack = {
                    navController.navigate(Login)
                },
                onRegisterSuccess = {
                    navController.navigate(Login) // Pendiente poner la ruta HOME
                }
            )
        }
    }
}