package com.example.jetpackcomposeapp.list1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeapp.ui.theme.JetpackComposeAppTheme
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeapp.R
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle

class List1RatingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAppTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

@Composable
fun ShowRatingView(onBackPressed: () -> Unit) {
    var rating: Float by remember { mutableStateOf(3.2f) }

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row (modifier = Modifier.padding(horizontal = 16.dp, vertical = 40.dp)){
            Image(painter = painterResource(id = R.drawable.man_icon), contentDescription = "A man",
                modifier = Modifier
                    .weight(1f)
                    .size(100.dp)
                    .background(Color.White))
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.woman_icon), contentDescription = "A woman",
                modifier = Modifier
                    .weight(1f)
                    .size(100.dp)
                    .background(Color.White))
        }
        Row {
            Image(painter = painterResource(id = R.drawable.person_icon), contentDescription = "A woman",
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Gray))
        }
        RatingBar(
            value = rating,
            style = RatingBarStyle.Fill(),
            onValueChange = {
                rating = it
            },
            onRatingChanged = {
            },
            modifier = Modifier.padding(vertical = 50.dp)
        )
        Button(onClick = {
            onBackPressed()
        }) {
            Text("Back", fontSize = 30.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    JetpackComposeAppTheme {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row (modifier = Modifier.padding(horizontal = 16.dp, vertical = 40.dp)){
                Image(painter = painterResource(id = R.drawable.man_icon), contentDescription = "A man",
                    modifier = Modifier
                        .weight(1f)
                        .size(100.dp)
                        .background(Color.White))
                Spacer(modifier = Modifier.weight(1f))
                Image(painter = painterResource(id = R.drawable.woman_icon), contentDescription = "A woman",
                    modifier = Modifier
                        .weight(1f)
                        .size(100.dp)
                        .background(Color.White))
            }
            Row {
                Image(painter = painterResource(id = R.drawable.person_icon), contentDescription = "A woman",
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Gray))
            }
            var rating = 0f
            RatingBar(
                value = rating,
                style = RatingBarStyle.Fill(),
                onValueChange = {
                    rating = it
                },
                onRatingChanged = {
                },
                modifier = Modifier.padding(vertical = 50.dp)
            )
            Button(onClick = {

            }) {
                Text("Back", fontSize = 30.sp)
            }
        }
    }
}