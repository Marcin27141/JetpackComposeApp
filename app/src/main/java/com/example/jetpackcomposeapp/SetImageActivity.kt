package com.example.jetpackcomposeapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    ImageFromUri(file.contentUri!!, modifier)
}

@Composable
fun ImageFromUri(uri: Uri, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val imageBitmap: ImageBitmap = ImageRepo.getInstance(context).getFileBitmap(uri)!!.asImageBitmap()
    Image(bitmap = imageBitmap, contentDescription = uri.toString(),
        contentScale = ContentScale.FillWidth, modifier = modifier)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowSwipeImages(startPage: Int = 0) {
    val context = LocalContext.current
    val images = ImageRepo.getInstance(context).getSharedList() ?: mutableListOf()
    val pagerState = rememberPagerState(initialPage = startPage)
    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            pageCount = images.size,
            state = pagerState
            ) { index ->
            ImageFromFile(images[index],
                Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, bottom = 100.dp, top = 30.dp))
        }
        Button(onClick = {
            PreferencesManager.getInstance().setHomeImage(images[pagerState.currentPage].contentUri!!, context)
            Intent(context, MainActivity::class.java).also {
                context.startActivity(it)
            }
        }, modifier = Modifier.align(Alignment.BottomCenter).padding(vertical = 30.dp)) {
            Text("Set", fontSize = 24.sp, modifier = Modifier.padding(horizontal = 10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    JetpackComposeAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Yellow)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxSize()
            )
            Button(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.BottomCenter).padding(vertical = 30.dp)) {
                Text("Set", fontSize = 24.sp, modifier = Modifier.padding(horizontal = 10.dp))
            }
        }
    }
}