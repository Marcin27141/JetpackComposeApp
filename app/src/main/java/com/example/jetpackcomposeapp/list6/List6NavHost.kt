package com.example.jetpackcomposeapp.list6

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpackcomposeapp.NavigationItem
import com.example.jetpackcomposeapp.R
import com.example.jetpackcomposeapp.services.MyRepository
import com.example.jetpackcomposeapp.services.PreferencesManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(actions: @Composable RowScope.() -> Unit = {}) {
    TopAppBar(
        title = { Text("List 6") },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}

@Composable
fun ContentWithDefaultAppBar(content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        topBar = { DefaultAppBar {} },
        content = content
    )
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowList6NavHost() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val defaultName = "Marcin Tkocz"
    val defaultNick = "Nick"

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }


    val navigationItems = listOf(
        NavigationItem(
            "Home",
                Icons.Default.Home,
                AppScreens.List6Home.name
        ),
        NavigationItem(
            "Photos",
            ImageVector.vectorResource(R.drawable.photos_icon),
            AppScreens.PhotosGrid.name
        ),
        NavigationItem(
            "Animals",
            ImageVector.vectorResource(R.drawable.predator_icon),
            AppScreens.AnimalsList.name
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 50.dp),
                ) {
                    Text(text = "List 6", style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.align(Alignment.Center))
                }
                Spacer(modifier = Modifier.height(16.dp))
                navigationItems.forEachIndexed { index, navigationItem ->
                    NavigationDrawerItem(
                        label = { Text(navigationItem.title) },
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                            navController.navigate(navigationItem.route)
                        },
                        icon = {
                            Icon(imageVector = navigationItem.icon, contentDescription = navigationItem.title)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding))
                }

            }
        }
    ) {
        val bottomNavItems = listOf(
            NavigationItem(
                "Photos",
                ImageVector.vectorResource(R.drawable.photos_icon),
                AppScreens.PhotosGrid.name
            ),
            NavigationItem(
                "Home",
                Icons.Default.Home,
                AppScreens.List6Home.name
            ),
            NavigationItem(
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
                composable(AppScreens.List6Home.name) {
                    val preferencesManager = PreferencesManager.getInstance()
                    val (name, nick) = preferencesManager.getNameAndNick(
                        context,
                        defaultName,
                        defaultNick
                    )
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


@Composable
private fun List6Home(homeInfo: List6HomeInfo, navController: NavController) {
    ContentWithDefaultAppBar {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
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
//            Spacer(modifier = Modifier.height(50.dp))
//            Row (
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Button(
//                    onClick = {
//                        navController.navigate(AppScreens.PhotosGrid.name)
//                    },
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 10.dp)
//                ) {
//                    Text("Photos",
//                        fontSize = 26.sp)
//                }
//                Button(
//                    onClick = {
//                        navController.navigate(AppScreens.AnimalsList.name)
//                    },
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 10.dp)) {
//                    Text("Animals",
//                        fontSize = 26.sp)
//                }
//            }
        }
    }
}