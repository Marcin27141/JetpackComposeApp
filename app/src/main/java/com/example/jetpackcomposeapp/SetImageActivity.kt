package com.example.jetpackcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
                    ShowImages()
                }
            }
        }
    }
}

@Composable
fun ImageFromFile(file: FileItem) {
    val context = LocalContext.current
    val imageBitmap: ImageBitmap = ImageRepo.getInstance(context).getFileBitmap(file.contentUri!!)!!.asImageBitmap()
    Image(bitmap = imageBitmap, contentDescription = file.name,
    contentScale = ContentScale.FillWidth, modifier = Modifier.fillMaxSize())
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowImages() {
    val images = ImageRepo.getInstance(LocalContext.current).getSharedList() ?: mutableListOf()
    val pagerState = rememberPagerState()
    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            pageCount = images.size,
            state = pagerState
            ) { index ->
            ImageFromFile(images[index])
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    JetpackComposeAppTheme {
        
    }
}