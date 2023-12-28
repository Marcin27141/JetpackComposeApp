package com.example.jetpackcomposeapp

import android.content.Intent
import android.os.Bundle
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
import com.example.jetpackcomposeapp.ui.theme.JetpackComposeAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAppTheme {
                val name = intent?.getStringExtra("Name") ?: "Name"
                val nick = intent?.getStringExtra("Nick") ?: "Nick"
                List1View1(name, nick)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier
            .background(Color.Gray)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello $name!",
            color = Color.Blue,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(16.dp)
        )
        Text(
            text = "Goodbye $name!",
            color = Color.Blue,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(16.dp)
        )
        ShowImage(false)
    }
}

@Composable
fun ShowImage(withBuild: Boolean) {
    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
        if (withBuild) {
            Icon(imageVector = Icons.Default.Build, contentDescription = null,
                modifier = Modifier.size(40.dp))
        }

    }

}

@Composable
fun List1View1(name: String, nick: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column (
        modifier = Modifier
            .background(Color.Cyan)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = name,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(16.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.person_icon),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .background(Color.Gray)
        )
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
                    Intent(context, List1FormsActivity::class.java).also {
                        it.putExtra("Name", name)
                        it.putExtra("Nick", nick)
                        context.startActivity(it)
                    }
                },
                modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
            ) {
                Text("1",
                    fontSize = 26.sp)
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f).padding(horizontal = 10.dp)) {
                Text("2",
                    fontSize = 26.sp)
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f).padding(horizontal = 10.dp)) {
                Text("3",
                    fontSize = 26.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackComposeAppTheme {
        List1View1("Name", "Nick")
    }
}