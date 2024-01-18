package com.example.jetpackcomposeapp.list1

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.jetpackcomposeapp.R
import com.example.jetpackcomposeapp.Helpers.ImageFromUri
import com.example.jetpackcomposeapp.services.PreferencesManager

enum class AppScreens {
    List1Home,
    PhoneView,
    FormsView,
    RatingView
}

private class HomeInfo(
    val name: String,
    val nick: String,
    val imageUri: Uri?
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun List1NavHost() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val defaultName = "Marcin Tkocz"
    val defaultNick = "Nick"

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
            composable(AppScreens.List1Home.name) {
                val preferencesManager = PreferencesManager.getInstance()
                val (name, nick) = preferencesManager.getNameAndNick(context, defaultName, defaultNick)
                val imageUri = preferencesManager.getHomeImageUri(context)
                val homeInfo = HomeInfo(name, nick, imageUri)
                ShowList1Home(homeInfo, navController)
            }
            composable(AppScreens.PhoneView.name) { ShowPhoneView() }
            composable(AppScreens.RatingView.name) {
                ShowRatingView { navController.popBackStack(AppScreens.List1Home.name, false) }
            }
            composable(AppScreens.FormsView.name) {
                val (name, nick) = PreferencesManager.getInstance().getNameAndNick(context, defaultName, defaultNick)
                ShowFormsView(name, nick) { navController.popBackStack(AppScreens.List1Home.name, false) }
            }
        }
    }

}

@Composable
private fun ShowList1Home(homeInfo: HomeInfo, navController: NavController) {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ShowHomeInfo(homeInfo)
        Spacer(modifier = Modifier.height(50.dp))
        ShowHomeNavigationButtons(navController)
    }
}

@Composable
private fun ShowHomeInfo(homeInfo: HomeInfo) {
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
}

@Composable
private fun ShowHomeNavigationButtons(navController: NavController) {
    class NavButton(val title: String, val navigatePath: String)
    val navButtons = arrayOf(
        NavButton("Mobile", AppScreens.PhoneView.name),
        NavButton("Forms", AppScreens.FormsView.name),
        NavButton("Rate", AppScreens.RatingView.name)
    )

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        navButtons.forEach { navButton ->
            Button(
                onClick = { navController.navigate(navButton.navigatePath) },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(navButton.title, fontSize = 18.sp)
            }
        }
    }
}
