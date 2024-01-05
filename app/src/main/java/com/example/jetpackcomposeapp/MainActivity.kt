package com.example.jetpackcomposeapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.jetpackcomposeapp.ui.theme.JetpackComposeAppTheme

class MainActivity : ComponentActivity() {
    private val REQUEST_CODE = 313
    private val REQUIRED_PERMISSIONS = mutableListOf(
        //Manifest.permission.CAMERA,
        //Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE)
        .apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (!allPermissionsGranted()) {
                Toast.makeText(this, "Permissions not granted.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAppTheme {
                val name = intent?.getStringExtra("Name") ?: "Name"
                val nick = intent?.getStringExtra("Nick") ?: "Nick"
                val preferencesManager = PreferencesManager.getInstance()
                val imageUri = preferencesManager.getHomeImageUri(this)

//                if (imageUri != null) {
//                    iconView.setImageBitmap(ImageRepo.getInstance(requireContext()).getFileBitmap(imageUri, 150, 150))
//                } else {
//                    iconView.setImageResource(initialIcon)
//                }
                List1View1(name, nick, imageUri)
            }
        }
        if (!allPermissionsGranted())
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE)
    }
}

@Composable
fun List1View1(name: String, nick: String, imageUri: Uri?, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = name,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(16.dp)
        )
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
        Text(
            text = nick,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    Intent(context, List1PhoneActivity::class.java).also {
                        context.startActivity(it)
                    }
                },
                modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
            ) {
                Text("1",
                    fontSize = 26.sp)
            }
            Button(
                onClick = {
                    Intent(context, List1FormsActivity::class.java).also {
                        it.putExtra("Name", name)
                        it.putExtra("Nick", nick)
                        context.startActivity(it)
                    }
                },
                modifier = Modifier.weight(1f).padding(horizontal = 10.dp)) {
                Text("2",
                    fontSize = 26.sp)
            }
            Button(
                onClick = {
                    Intent(context, List1RatingActivity::class.java).also {
                        context.startActivity(it)
                    }
                },
                modifier = Modifier.weight(1f).padding(horizontal = 10.dp)) {
                Text("3",
                    fontSize = 26.sp)
            }
        }
        Button(
            onClick = {
//                Intent(context, ListActivity::class.java).also {
//                    context.startActivity(it)
//                }
                Intent(context, PhotosListActivity::class.java).also {
                    context.startActivity(it)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 8.dp)) {
            Text("List 6", fontSize = 26.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackComposeAppTheme {
    }
}