package com.example.jetpackcomposeapp.Helpers

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.example.jetpackcomposeapp.services.FileItem
import com.example.jetpackcomposeapp.services.ImageRepo

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