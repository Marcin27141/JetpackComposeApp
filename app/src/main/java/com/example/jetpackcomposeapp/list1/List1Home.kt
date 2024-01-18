package com.example.jetpackcomposeapp.list1

import android.content.Context
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
import com.example.jetpackcomposeapp.helpers.ImageFromUri
import com.example.jetpackcomposeapp.R
import com.example.jetpackcomposeapp.services.PreferencesManager

private class List1HomeInfo(
    val name: String,
    val nick: String,
    val imageUri: Uri?
)
@Composable
fun ShowList1Home(navController: NavController) {
    val context = LocalContext.current
    val homeInfo = getHomeInfo(context)

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

private fun getHomeInfo(context: Context): List1HomeInfo {
    val preferencesManager = PreferencesManager.getInstance()
    val (name, nick) = preferencesManager.getNameAndNick(context)
    val imageUri = preferencesManager.getHomeImageUri(context)
    return List1HomeInfo(name, nick, imageUri)
}

@Composable
private fun ShowHomeInfo(homeInfo: List1HomeInfo) {
    ShowName(homeInfo.name)
    ShowHomeImage(homeInfo.imageUri)
    ShowNick(homeInfo.nick)
}

@Composable
fun ShowNick(nick: String) {
    Text(
        text = nick,
        fontSize = 30.sp,
        modifier = Modifier
            .padding(16.dp)
    )
}

@Composable
private fun ShowName(name: String) {
    Text(
        text = name,
        fontSize = 30.sp,
        modifier = Modifier
            .padding(16.dp)
    )
}

@Composable
private fun ShowHomeImage(imageUri: Uri?) {
    if (imageUri != null)
        ImageFromUri(imageUri, Modifier.size(200.dp))
    else {
        Image(
            painter = painterResource(id = R.drawable.person_icon),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .background(Color.Gray)
        )
    }
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