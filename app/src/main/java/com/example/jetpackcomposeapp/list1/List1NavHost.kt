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
import com.example.jetpackcomposeapp.R
import com.example.jetpackcomposeapp.list6.ImageFromUri
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

@Composable
fun List1NavHost() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val defaultName = "Marcin Tkocz"
    val defaultNick = "Nick"

    NavHost(navController, startDestination = AppScreens.List1Home.name) {
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

@Composable
private fun ShowList1Home(homeInfo: HomeInfo, navController: NavController) {
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
                    navController.navigate(AppScreens.PhoneView.name)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
            ) {
                Text("1",
                    fontSize = 26.sp)
            }
            Button(
                onClick = {
                    navController.navigate(AppScreens.FormsView.name)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)) {
                Text("2",
                    fontSize = 26.sp)
            }
            Button(
                onClick = {
                    navController.navigate(AppScreens.RatingView.name)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)) {
                Text("3",
                    fontSize = 26.sp)
            }
        }
    }
}