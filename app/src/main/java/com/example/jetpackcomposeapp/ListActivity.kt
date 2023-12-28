package com.example.jetpackcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeapp.ui.theme.JetpackComposeAppTheme

class ListActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                var customItem by remember {
                    mutableStateOf("")
                }
                var listItems by remember {
                    mutableStateOf(listOf<String>())
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    //Greeting("Android")
                    Column (horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = customItem,
                                onValueChange = { text ->
                                    customItem = text
                                },
                                modifier = Modifier.padding(16.dp, 16.dp, 0.dp, 0.dp).weight(1f))
                            Spacer(modifier = Modifier.width(16.dp))
                            Button(
                                onClick = {
                                    if (customItem.isNotBlank()) {
                                        listItems = listItems + customItem
                                    }
                                    customItem = ""

                                },
                                modifier = Modifier.padding(16.dp)) {
                                Text(text = "Add")
                            }
                        }
                        ItemsList(listItems)
                    }
                }
                }
            }
        }
    }
}

@Composable
fun ItemsList(items: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        items(items) { item ->
            Text(
                text = item,
                fontSize = 50.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Divider(
                color = Color.Black,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}