package com.ulisesg.admingolazopro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alilopez.kt_demohilt.core.ui.theme.AppTheme
import com.ulisesg.admingolazopro.core.navigate.NavigationWrapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme() {
                NavigationWrapper()
            }
        }
    }
}

