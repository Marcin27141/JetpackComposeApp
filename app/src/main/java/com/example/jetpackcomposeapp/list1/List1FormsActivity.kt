package com.example.jetpackcomposeapp.list1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeapp.MainActivity
import com.example.jetpackcomposeapp.ui.theme.JetpackComposeAppTheme

class List1FormsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAppTheme {
                val context = LocalContext.current
                var name by remember {
                    mutableStateOf("")
                }
                var email by remember {
                    mutableStateOf("")
                }
                var phone by remember {
                    mutableStateOf("")
                }
                var nick by remember {
                    mutableStateOf("")
                }

                name = intent?.getStringExtra("Name") ?: ""
                nick = intent?.getStringExtra("Nick") ?: ""
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Name:", fontSize = 30.sp)
                            Spacer(modifier = Modifier.width(16.dp))
                            OutlinedTextField(
                                value = name,
                                onValueChange = { text ->
                                    name = text
                                },
                                label = { Text("Name") },
                                modifier = Modifier.weight(1f))
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Email:", fontSize = 30.sp)
                            Spacer(modifier = Modifier.width(16.dp))
                            OutlinedTextField(
                                value = email,
                                onValueChange = { text ->
                                    email = text
                                },
                                label = { Text("Email") },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Email
                                ),
                                modifier = Modifier.weight(1f))
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Phone:", fontSize = 30.sp)
                            Spacer(modifier = Modifier.width(16.dp))
                            OutlinedTextField(
                                value = phone,
                                onValueChange = { text ->
                                    phone = text
                                },
                                label = { Text("Phone") },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number
                                ),
                                modifier = Modifier.weight(1f))
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Nick:", fontSize = 30.sp)
                            Spacer(modifier = Modifier.width(16.dp))
                            OutlinedTextField(
                                value = nick,
                                onValueChange = { text ->
                                    nick = text
                                },
                                label = { Text("Nick") },
                                modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.height(50.dp))
                        Button(onClick = {

                            val intent = Intent(context, MainActivity::class.java)
                            intent.putExtra("Name", name)
                            intent.putExtra("Nick", nick)
                            context.startActivity(intent)
                        }) {
                            Text("Back", fontSize = 24.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    JetpackComposeAppTheme {
        val context = LocalContext.current
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Name:", fontSize = 30.sp)
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = "name",
                    onValueChange = { text ->
                    },
                    modifier = Modifier.weight(1f))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Email:", fontSize = 30.sp)
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = "email",
                    onValueChange = { text ->
                    },
                    modifier = Modifier.weight(1f))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Phone:", fontSize = 30.sp)
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = "phone",
                    onValueChange = { text ->
                    },
                    modifier = Modifier.weight(1f))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Nick:", fontSize = 30.sp)
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = "nick",
                    onValueChange = { text ->
                    },
                    modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(50.dp))
            Button(onClick = {

                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("Name", "other name")
                intent.putExtra("Nick", "other nick")
                context.startActivity(intent)
            }) {
                Text("Back", fontSize = 24.sp)
            }
        }
    }
}