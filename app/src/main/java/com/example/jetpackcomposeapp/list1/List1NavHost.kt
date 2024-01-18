package com.example.jetpackcomposeapp.list1

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class AppScreens {
    List1Home,
    PhoneView,
    FormsView,
    RatingView
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun List1NavHost() {
    val context = LocalContext.current
    val navController = rememberNavController()

    Scaffold(topBar = { TopAppBar(
        title = { Text("List 1") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) }) { values ->
        NavHost(navController, startDestination = AppScreens.List1Home.name, modifier = Modifier.padding(values)) {
            composable(AppScreens.List1Home.name) { ShowList1Home(navController) }
            composable(AppScreens.PhoneView.name) { ShowPhoneView() }
            composable(AppScreens.RatingView.name) {
                ShowRatingView { navController.popBackStack(AppScreens.List1Home.name, false) }
            }
            composable(AppScreens.FormsView.name) {
                ShowFormsView { navController.popBackStack(AppScreens.List1Home.name, false) }
            }
        }
    }

}
