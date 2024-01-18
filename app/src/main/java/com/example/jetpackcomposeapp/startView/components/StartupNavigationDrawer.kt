package com.example.jetpackcomposeapp.startView.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpackcomposeapp.helpers.NavigationItem
import com.example.jetpackcomposeapp.R
import com.example.jetpackcomposeapp.startView.MainAppScreens
import kotlinx.coroutines.launch


private val navigationItems = listOf(
    NavigationItem(
        "List 1",
        R.drawable.one_icon,
        MainAppScreens.List1.name
    ),
    NavigationItem(
        "List 6",
        R.drawable.two_icon,
        MainAppScreens.List6.name
    )
)
@Composable
fun StartupNavigationDrawer(navController: NavController, content: @Composable () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.85F)) {
                DrawerSheetContent() { route ->
                    scope.launch {
                        drawerState.close()
                    }
                    navController.navigate(route)
                }
            }
        },
        content = content
    )
}

@Composable
fun DrawerSheetContent(navigate: (String) -> Unit) {
    SheetTitle()
    Spacer(modifier = Modifier.height(16.dp))
    NavigationItemsList(navigate)
}

@Composable
private fun SheetTitle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 50.dp),
    ) {
        Text(text = "Jetpack Compose", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.align(
            Alignment.Center))
    }
}

@Composable
private fun NavigationItemsList(navigate: (String) -> Unit) {
    navigationItems.forEach { navigationItem ->
        NavigationDrawerItem(
            label = { Text(navigationItem.title) },
            selected = false,
            onClick = { navigate(navigationItem.route) },
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(navigationItem.resourceId),
                    contentDescription = navigationItem.title
                )
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding))
    }
}
