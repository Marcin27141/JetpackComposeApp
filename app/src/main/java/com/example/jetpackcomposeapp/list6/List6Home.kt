package com.example.jetpackcomposeapp.list6

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeapp.helpers.ImageFromUri
import com.example.jetpackcomposeapp.R
import com.example.jetpackcomposeapp.list6.components.ContentWithDefaultAppBar
import com.example.jetpackcomposeapp.services.PreferencesManager

private class List6HomeInfo(
    val name: String,
    val nick: String,
    val imageUri: Uri?
)
@Composable
fun List6Home() {
    val context = LocalContext.current
    val homeInfo = getHomeInfo(context)

    ContentWithDefaultAppBar {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ShowName(homeInfo.name)
            ShowHomeImage(homeInfo.imageUri)
            ShowNick(homeInfo.nick)
        }
    }
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

private fun getHomeInfo(context: Context): List6HomeInfo {
    val preferencesManager = PreferencesManager.getInstance()
    val (name, nick) = preferencesManager.getNameAndNick(context)
    val imageUri = preferencesManager.getHomeImageUri(context)
    return List6HomeInfo(name, nick, imageUri)
}