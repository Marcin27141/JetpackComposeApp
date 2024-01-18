package com.example.jetpackcomposeapp.startView

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeapp.list1.List1NavHost
import com.example.jetpackcomposeapp.list6.List6NavHost

enum class MainAppScreens {
    Home,
    List1,
    List6
}

@Composable
fun ApplicationNavController() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = MainAppScreens.Home.name) {
        composable(MainAppScreens.Home.name) { MainScreenView(navController) }
        composable(MainAppScreens.List1.name) { List1NavHost() }
        composable(MainAppScreens.List6.name) { List6NavHost() }
    }
}