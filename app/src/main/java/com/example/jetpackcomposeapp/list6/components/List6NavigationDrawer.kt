package com.example.jetpackcomposeapp.list6.components

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
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpackcomposeapp.R
import com.example.jetpackcomposeapp.ResourceNavigationItem
import com.example.jetpackcomposeapp.list6.AppScreens
import kotlinx.coroutines.launch


private val navigationItems = listOf(
    ResourceNavigationItem(
        "Home",
        R.drawable.home_icon,
        AppScreens.List6Home.name
    ),
    ResourceNavigationItem(
        "Photos",
        R.drawable.photos_icon,
        AppScreens.PhotosGrid.name
    ),
    ResourceNavigationItem(
        "Animals",
        R.drawable.predator_icon,
        AppScreens.AnimalsList.name
    )
)
@Composable
fun List6NavigationDrawer(navController: NavController, content: @Composable () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedItemIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.85F)) {
                DrawerSheetContent(selectedItemIndex) { route ->
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
fun DrawerSheetContent(currentIndex: MutableIntState, navigate: (String) -> Unit) {
    SheetTitle()
    Spacer(modifier = Modifier.height(16.dp))
    NavigationItemsList(currentIndex, navigate)
}

@Composable
private fun SheetTitle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 50.dp),
    ) {
        Text(text = "List 6", style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun NavigationItemsList(currentIndex: MutableIntState, navigate: (String) -> Unit) {
    navigationItems.forEachIndexed { index, navigationItem ->
        NavigationDrawerItem(
            label = { Text(navigationItem.title) },
            selected = currentIndex.intValue == index,
            onClick = {
                currentIndex.intValue = index
                navigate(navigationItem.route)
              },
            icon = {
                Icon(imageVector = ImageVector.vectorResource(navigationItem.resourceId), contentDescription = navigationItem.title)
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding))
    }
}
