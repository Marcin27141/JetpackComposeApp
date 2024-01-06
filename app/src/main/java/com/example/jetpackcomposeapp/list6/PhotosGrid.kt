package com.example.jetpackcomposeapp.list6

import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.jetpackcomposeapp.services.FileItem
import com.example.jetpackcomposeapp.services.ImageRepo
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ShowPhotosGrid(onNavigateToSwipe: (Int) -> Unit) {
    val context = LocalContext.current

    val files = remember {
        mutableStateOf(ImageRepo.getInstance(context).getSharedList()?.toList() ?: listOf())
    }
    val lastFile: MutableState<File?> = remember {
        mutableStateOf(null)
    }

    fun getNewFileUri() : Uri {
        val timeStamp : String = SimpleDateFormat("yyyMMdd_HHmmss").format(Date())
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val tmpFile = File.createTempFile("Photo_" + timeStamp ,".jpg", dir)
        lastFile.value = tmpFile
        return FileProvider.getUriForFile(context, context.packageName + ".provider", tmpFile)
    }
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { result: Boolean ->
        if (result) {
            files.value = ImageRepo.getInstance(context).getSharedList()?.toList() ?: listOf()
            Toast.makeText(context, "Photo was taken", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Photo wasn't taken", Toast.LENGTH_LONG).show()
            lastFile.value?.delete()
        }
    }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        ImagesGrid(files.value, onNavigateToSwipe, Modifier.weight(1f))
        Button(onClick = {
            val lastFileUri = getNewFileUri()
            try {
                takePictureLauncher.launch(lastFileUri)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "Camera doesn't work", Toast.LENGTH_SHORT).show()
            }
        }, modifier = Modifier.padding(16.dp))
        {
            Text("Add photo", fontSize = 24.sp, modifier = Modifier.padding(horizontal = 10.dp))
        }
    }
}

@Composable
fun ImagesGrid(images: List<FileItem>, onNavigateToSwipe: (Int) -> Unit, modifier: Modifier = Modifier) {
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
