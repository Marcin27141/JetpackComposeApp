package com.example.jetpackcomposeapp.list6

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpackcomposeapp.VectorNavigationItem
import com.example.jetpackcomposeapp.R
import com.example.jetpackcomposeapp.list6.components.List6NavigationDrawer
import com.example.jetpackcomposeapp.services.MyRepository

enum class AppScreens {
    List6Home,
    PhotosGrid,
    PhotosSwipe,
    AnimalsList,
    AnimalForm,
    AnimalDetails
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowList6NavHost() {
    val context = LocalContext.current
    val navController = rememberNavController()
    
    List6NavigationDrawer(navController = navController) {
        val bottomNavItems = listOf(
            VectorNavigationItem(
                "Photos",
                ImageVector.vectorResource(R.drawable.photos_icon),
                AppScreens.PhotosGrid.name
            ),
            VectorNavigationItem(
                "Home",
                Icons.Default.Home,
                AppScreens.List6Home.name
            ),
            VectorNavigationItem(
                "Animals",
                ImageVector.vectorResource(R.drawable.predator_icon),
                AppScreens.AnimalsList.name
            )
        )

        var bottomSelectedIndex by rememberSaveable {
            mutableIntStateOf(1)
        }

        Scaffold(
            bottomBar = {
                NavigationBar {
                    bottomNavItems.forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            selected = bottomSelectedIndex == index,
                            onClick = {
                                bottomSelectedIndex = index

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
                                    Icon(imageVector = navigationItem.icon, contentDescription = navigationItem.title)
                                }
                            })
                    }
                }
            }
        ) {
            NavHost(navController, startDestination = AppScreens.List6Home.name, modifier = Modifier.padding(it)) {
                composable(AppScreens.List6Home.name) { List6Home() }
                composable(AppScreens.AnimalsList.name) { ShowAnimalsList(navController) }
                composable("${AppScreens.AnimalDetails.name}/{id}") {
                    val animalId = it.arguments?.getString("id")
                    val animal = MyRepository.getInstance(context).getAnimalById(animalId!!.toInt())
                    if (animal != null)
                        ShowDetails(animal, navController)
                }
                composable("${AppScreens.AnimalForm.name}?id={id}",
                    arguments = listOf(navArgument("id") { defaultValue = "" })
                ) {
                    val animalId = it.arguments?.getString("id")
                    val animal = if (animalId.isNullOrBlank()) null else
                        MyRepository.getInstance(context).getAnimalById(animalId.toInt())
                    ShowCreate(animal, navController)
                }
                composable(AppScreens.PhotosGrid.name) {
                    ShowPhotosGrid { page -> navController.navigate("${AppScreens.PhotosSwipe.name}/${page}") }
                }
                composable("${AppScreens.PhotosSwipe.name}/{startPage}") { navBackStackEntry ->
                    val startPage = navBackStackEntry.arguments?.getString("startPage")
                    startPage?.let {
                        ShowSwipeImages(it.toInt()) {
                            navController.popBackStack(
                                AppScreens.List6Home.name,
                                false
                            )
                        }
                    }
                }
            }
        }
    }
}