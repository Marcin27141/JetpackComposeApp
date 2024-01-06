package com.example.jetpackcomposeapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeapp.list1.ShowList1NavHost
import com.example.jetpackcomposeapp.list6.List6NavHost
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

    enum class AppScreens {
        Home,
        List1,
        List6
    }


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAppTheme {
                val navController = rememberNavController()
//                val name = intent?.getStringExtra("Name") ?: "Name"
//                val nick = intent?.getStringExtra("Nick") ?: "Nick"
//                val preferencesManager = PreferencesManager.getInstance()
//                val imageUri = preferencesManager.getHomeImageUri(this)
//
//                List1View1(name, nick, imageUri)

                NavHost(navController, startDestination = AppScreens.Home.name) {
                    composable(AppScreens.Home.name) {
                        MainScreenView(
                            { navController.navigate(AppScreens.List1.name) },
                            { navController.navigate(AppScreens.List6.name) }
                        )
                    }
                    composable(AppScreens.List1.name) {
                        ShowList1NavHost()
                    }
                    composable(AppScreens.List6.name) {
                        List6NavHost()
                    }
                }
            }
        }
        if (!allPermissionsGranted())
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE)
    }
}

@Composable
fun MainScreenView(navigateList1: () -> Unit, navigateList6: () -> Unit) {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = "Jetpack Compose",
            fontSize = 30.sp,
            modifier = Modifier
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { navigateList1() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Text("List 1",
                fontSize = 26.sp)
        }
        Button(
            onClick = { navigateList6() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp)) {
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