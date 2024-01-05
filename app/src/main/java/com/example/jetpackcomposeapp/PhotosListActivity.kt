package com.example.jetpackcomposeapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeapp.ui.theme.JetpackComposeAppTheme

class PhotosListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShowImagesGrid()
                }
            }
        }
    }
}

@Composable
fun ShowImagesGrid() {
    val context = LocalContext.current
    val images = ImageRepo.getInstance(context).getSharedList() ?: mutableListOf()
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(images.size) { index ->
                ImageFromFile(file = images[index],
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            Intent(context, SetImageActivity::class.java).also {
                                it.putExtra("StartPage", index)
                                context.startActivity(it)
                            }
                        })
            }
        })

}




@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    JetpackComposeAppTheme {
        val images = (1..10).map { it.toString() }
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            content = {
                items(images.size) { index ->
                    //ImageFromFile(file = images[index], Modifier.size(100.dp))
//                    Card(
//                        modifier = Modifier.padding(16.dp)
//                    ) {
//                        Text(
//                            text = images[index],
//                            fontWeight = FontWeight.Bold,
//                            color = Color(0xFFFFFFFF),
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier.padding(16.dp)
//                        )
//                    }
                    Image(painter = painterResource(id = R.drawable.bird_icon), null,
                        modifier = Modifier.size(100.dp))
                }
            })
    }
}