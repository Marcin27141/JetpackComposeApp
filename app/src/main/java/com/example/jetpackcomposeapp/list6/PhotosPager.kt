package com.example.jetpackcomposeapp.list6

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeapp.Helpers.ImageFromFile
import com.example.jetpackcomposeapp.services.ImageRepo
import com.example.jetpackcomposeapp.services.PreferencesManager


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowSwipeImages(startPage: Int = 0, onNavigateToHome: () -> Unit) {
    val context = LocalContext.current
    val images = ImageRepo.getInstance(context).getFilesList() ?: mutableListOf()
    val pagerState = rememberPagerState(initialPage = startPage, pageCount = { images.size })
    ContentWithDefaultAppBar {
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            HorizontalPager(
                state = pagerState
            ) { index ->
                ImageFromFile(images[index],
                    Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp, bottom = 100.dp, top = 30.dp))
            }
            Button(onClick = {
                PreferencesManager.getInstance()
                    .setHomeImage(images[pagerState.currentPage].contentUri!!, context)
                onNavigateToHome()
            }, modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = 30.dp)) {
                Text("Set", fontSize = 24.sp, modifier = Modifier.padding(horizontal = 10.dp))
            }
        }
    }
}