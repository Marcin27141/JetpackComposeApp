package com.example.jetpackcomposeapp.list6

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeapp.services.ImageRepo

@Composable
fun ShowPhotosGrid(onNavigateToSwipe: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        ImagesGrid(onNavigateToSwipe, Modifier.weight(1f))
        Button(onClick = {}, modifier = Modifier.padding(16.dp))
        {
            Text("Add photo", fontSize = 24.sp, modifier = Modifier.padding(horizontal = 10.dp))
        }
    }
}

@Composable
fun ImagesGrid(onNavigateToSwipe: (Int) -> Unit, modifier: Modifier = Modifier) {
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
