package com.example.jetpackcomposeapp.startView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetpackcomposeapp.startView.components.MainScreenAppBar
import com.example.jetpackcomposeapp.startView.components.StartupNavigationDrawer


@Composable
fun MainScreenView(navController: NavController) {
    StartupNavigationDrawer(navController) {
        Scaffold(
            topBar = { MainScreenAppBar() }
        ) {
            MainScreenView(it,
                { navController.navigate(MainAppScreens.List1.name) },
                { navController.navigate(MainAppScreens.List6.name) }
            )
        }
    }

}
@Composable
private fun MainScreenView(padding: PaddingValues, navigateList1: () -> Unit, navigateList6: () -> Unit) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        ShowAppTitle()
        Spacer(modifier = Modifier.weight(1f))
        ButtonList1(navigateList1)
        ButtonList6(navigateList6)
    }
}

@Composable
fun ShowAppTitle() {
    Text(
        text = "Jetpack Compose",
        fontSize = 30.sp,
        modifier = Modifier
            .padding(16.dp)
    )
}
@Composable
fun ButtonList1(navigateList1: () -> Unit) {
    Button(
        onClick = navigateList1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Text("List 1",
            fontSize = 26.sp)
    }
}

@Composable
fun ButtonList6(navigateList6: () -> Unit) {
    Button(
        onClick = navigateList6,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp)) {
        Text("List 6", fontSize = 26.sp)
    }
}


