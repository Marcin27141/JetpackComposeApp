package com.example.jetpackcomposeapp.list1

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeapp.R
import com.example.jetpackcomposeapp.list6.ImageFromUri
import com.example.jetpackcomposeapp.list6.PhotosListActivity
import com.example.jetpackcomposeapp.list6.ShowGridActivity
import com.example.jetpackcomposeapp.list6.ShowSwipeImages
import com.example.jetpackcomposeapp.services.PreferencesManager
import com.example.jetpackcomposeapp.ui.theme.JetpackComposeAppTheme

class List1NavHost : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

private enum class AppScreens {
    List1Home,
    PhoneView,
    FormsView,
    RatingView
}

class HomeInfo(
    val name: String,
    val nick: String,
    val imageUri: Uri?
)

fun getNameAndNick(context: Context): Pair<String, String> {
    val defaultName = "Marcin Tkocz"
    val defaultNick = "Nick"
    val (name, nick) = PreferencesManager.getInstance().getNameAndNick(context)
    return Pair(
        if (name.isNullOrBlank()) defaultName else name,
        if (nick.isNullOrBlank()) defaultNick else nick
    )
}

@Composable
fun ShowList1NavHost() {
    val context = LocalContext.current
    val navController = rememberNavController()


    NavHost(navController, startDestination = AppScreens.List1Home.name) {
        composable(AppScreens.List1Home.name) {
            val (name, nick) = getNameAndNick(context)
            val imageUri = PreferencesManager.getInstance().getHomeImageUri(context)
            val homeInfo = HomeInfo(name, nick, imageUri)
            List1Home(homeInfo, navController)
        }
        composable(AppScreens.PhoneView.name) { ShowPhoneView() }
        composable(AppScreens.RatingView.name) {
            ShowRatingView { navController.navigate(AppScreens.List1Home.name) }
        }
        composable(AppScreens.FormsView.name) {
            val (name, nick) = getNameAndNick(context)
            ShowFormsView(name = name, nick = nick) { navController.navigate(AppScreens.List1Home.name) }
        }
    }
}

@Composable
fun List1Home(homeInfo: HomeInfo, navController: NavController) {
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