package com.example.jetpackcomposeapp.list6

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpackcomposeapp.R
import com.example.jetpackcomposeapp.services.MyRepository
import com.example.jetpackcomposeapp.services.PreferencesManager


enum class AppScreens {
    List6Home,
    PhotosGrid,
    PhotosSwipe,
    AnimalsList,
    AnimalForm,
    AnimalDetails
}

private class List6HomeInfo(
    val name: String,
    val nick: String,
    val imageUri: Uri?
)

@Composable
fun ShowList6NavHost() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val defaultName = "Marcin Tkocz"
    val defaultNick = "Nick"

    NavHost(navController, startDestination = AppScreens.List6Home.name) {
        composable(AppScreens.List6Home.name) {
            val preferencesManager = PreferencesManager.getInstance()
            val (name, nick) = preferencesManager.getNameAndNick(context, defaultName, defaultNick)
            val imageUri = preferencesManager.getHomeImageUri(context)
            val homeInfo = List6HomeInfo(name, nick, imageUri)
            List6Home(homeInfo, navController)
        }
        composable(AppScreens.AnimalsList.name) { ShowAnimalsList(navController) }
        composable("${AppScreens.AnimalDetails.name}/{id}") {
            val animalId = it.arguments?.getString("id")
            val animal = MyRepository.getInstance(context).getAnimalById(animalId!!.toInt())
            if (animal != null)
                ShowDetails(animal, navController)
        }
        composable("${AppScreens.AnimalForm.name}?id={id}",
            arguments = listOf(navArgument("id") { defaultValue = "" })) {
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
                ShowSwipeImages(it.toInt()) { navController.popBackStack(AppScreens.List6Home.name, false) }
            }
        }
    }
}


@Composable
private fun List6Home(homeInfo: List6HomeInfo, navController: NavController) {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = homeInfo.name,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(16.dp)
        )
        if (homeInfo.imageUri != null)
            ImageFromUri(homeInfo.imageUri, Modifier.size(200.dp))
        else {
            Image(
                painter = painterResource(id = R.drawable.person_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Gray)
            )
        }
        Text(
            text = homeInfo.nick,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.navigate(AppScreens.PhotosGrid.name)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
            ) {
                Text("Photos",
                    fontSize = 26.sp)
            }
            Button(
                onClick = {
                    navController.navigate(AppScreens.AnimalsList.name)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)) {
                Text("Animals",
                    fontSize = 26.sp)
            }
        }
    }
}