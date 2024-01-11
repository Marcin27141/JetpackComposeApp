package com.example.jetpackcomposeapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeapp.list1.List1NavHost
import com.example.jetpackcomposeapp.list6.ShowList6NavHost
import com.example.jetpackcomposeapp.ui.theme.JetpackComposeAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val REQUEST_CODE = 313
    private val REQUIRED_PERMISSIONS = mutableListOf(
        Manifest.permission.CAMERA,
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
                ApplicationNavController()
            }
        }

        if (!allPermissionsGranted())
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE)
    }
}

private enum class AppScreens {
    Home,
    List1,
    List6
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationNavController() {
    val navController = rememberNavController()

    val navigationItems = listOf(
        NavigationItem(
            "List 1",
            ImageVector.vectorResource(R.drawable.one_icon),
            AppScreens.List1.name
        ),
        NavigationItem(
            "List 6",
            ImageVector.vectorResource(R.drawable.two_icon),
            AppScreens.List6.name
        )
    )

    NavHost(navController, startDestination = AppScreens.Home.name) {
        composable(AppScreens.Home.name) {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 50.dp),
                        ) {
                            Text(text = "Jetpack Compose", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.align(
                                Alignment.Center))
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        navigationItems.forEachIndexed { index, navigationItem ->
                            NavigationDrawerItem(
                                label = { Text(navigationItem.title) },
                                selected = false,
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate(navigationItem.route)
                                },
                                icon = {
                                    Icon(imageVector = navigationItem.icon, contentDescription = navigationItem.title)
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding))
                        }
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Jetpack Compose")
                            },
                            navigationIcon = {
                                IconButton(onClick = { scope.launch {
                                    drawerState.open()
                                }}) {
                                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                                actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                            )
                        )
                    }
                ) {
                    MainScreenView(it,
                        { navController.navigate(AppScreens.List1.name) },
                        { navController.navigate(AppScreens.List6.name) }
                    )
                }
            }
        }
        composable(AppScreens.List1.name) {
            List1NavHost()
        }
        composable(AppScreens.List6.name) {
            ShowList6NavHost()
        }
    }


}

@Composable
fun MainScreenView(padding: PaddingValues, navigateList1: () -> Unit, navigateList6: () -> Unit) {
    Column (
        modifier = Modifier.fillMaxSize().padding(padding),
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