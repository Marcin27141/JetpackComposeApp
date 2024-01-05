package com.example.jetpackcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeapp.ui.theme.JetpackComposeAppTheme

class SetImageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val initialPage = intent?.getIntExtra("StartPage", 0) ?: 0
                    ShowSwipeImages(initialPage)
                }
            }
        }
    }
}

@Composable
fun ImageFromFile(file: FileItem, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val imageBitmap: ImageBitmap = ImageRepo.getInstance(context).getFileBitmap(file.contentUri!!)!!.asImageBitmap()
    Image(bitmap = imageBitmap, contentDescription = file.name,
    contentScale = ContentScale.FillWidth, modifier = modifier)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowSwipeImages(startPage: Int = 0) {
    val images = ImageRepo.getInstance(LocalContext.current).getSharedList() ?: mutableListOf()
    val pagerState = rememberPagerState(initialPage = startPage)
    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            pageCount = images.size,
            state = pagerState
            ) { index ->
            ImageFromFile(images[index], Modifier.fillMaxSize().padding(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    JetpackComposeAppTheme {
        
    }
}