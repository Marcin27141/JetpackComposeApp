package com.example.jetpackcomposeapp.list6

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpackcomposeapp.list6.components.List6BottomNavigation
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

@Composable
fun List6NavHost() {
    val context = LocalContext.current
    val navController = rememberNavController()
    
    List6NavigationDrawer(navController) {
        List6BottomNavigation(navController) {
            List6NavHost(navController, context, Modifier.padding(it))
        }
    }
}

@Composable
private fun List6NavHost(navController: NavHostController, context: Context, modifier: Modifier) {
    NavHost(navController, startDestination = AppScreens.List6Home.name, modifier = modifier) {
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