package com.example.jetpackcomposeapp.list6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeapp.R
import com.example.jetpackcomposeapp.services.ImageRepo
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
                    List6NavHost()
                }
            }
        }
    }
}



@Composable
fun ShowGridActivity(onNavigateToSwipe: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        ShowImagesGrid(onNavigateToSwipe, Modifier.weight(1f))
        Button(onClick = {}, modifier = Modifier.padding(16.dp))
        {
            Text("Add photo", fontSize = 24.sp, modifier = Modifier.padding(horizontal = 10.dp))
        }
    }
}

@Composable
fun ShowImagesGrid(onNavigateToSwipe: (Int) -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val images = ImageRepo.getInstance(context).getSharedList() ?: mutableListOf()
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = modifier,
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
                            onNavigateToSwipe(index)
                        })
            }
        }
    )
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
                    Image(painter = painterResource(id = R.drawable.bird_icon), null,
                        modifier = Modifier.size(100.dp))
                }
            })
    }
}