package com.example.jetpackcomposeapp.list6.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.example.jetpackcomposeapp.R
import com.example.jetpackcomposeapp.helpers.NavigationItem
import com.example.jetpackcomposeapp.list6.AppScreens

private val bottomNavItems = listOf(
    NavigationItem(
        "Photos",
        R.drawable.photos_icon,
        AppScreens.PhotosGrid.name
    ),
    NavigationItem(
        "Home",
        R.drawable.home_icon,
        AppScreens.List6Home.name
    ),
    NavigationItem(
        "Animals",
        R.drawable.predator_icon,
        AppScreens.AnimalsList.name
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun List6BottomNavigation(navController: NavController, content: @Composable (PaddingValues) -> Unit) {
    val bottomSelectedIndex = rememberSaveable {
        mutableIntStateOf(1)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEachIndexed { index, navigationItem ->
                    NavigationBarItem(
                        selected = bottomSelectedIndex.intValue == index,
                        onClick = {
                            bottomSelectedIndex.intValue = index

                            if (navController.currentBackStack.value.any { entry -> entry.destination.route == navigationItem.route })
                                navController.popBackStack(navigationItem.route, false)
                            else
                                navController.navigate(navigationItem.route)
                        },
                        label = {
                            Text(navigationItem.title)
                        },
                        icon = {
                            BadgedBox(badge = {}) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(navigationItem.resourceId),
                                    contentDescription = navigationItem.title
                                )
                            }
                        })
                }
            }
        },
        content = content
    )
}