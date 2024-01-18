package com.example.jetpackcomposeapp.list1

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeapp.R
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle


@Composable
fun ShowRatingView(onBackPressed: () -> Unit) {
    val rating = remember { mutableFloatStateOf(3.2f) }

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UpperImagesRow()
        LowerImagesRow()
        ShowRatingBar(rating)
        BackButton(onBackPressed)
    }
}

@Composable
private fun ShowRatingBar(rating: MutableFloatState) {
    RatingBar(
        value = rating.floatValue,
        style = RatingBarStyle.Fill(),
        onValueChange = {rating.floatValue = it },
        onRatingChanged = {
        },
        modifier = Modifier.padding(vertical = 50.dp)
    )
}

@Composable
private fun LowerImagesRow() {
    Row {
        Image(painter = painterResource(id = R.drawable.person_icon), contentDescription = "A woman",
            modifier = Modifier
                .size(100.dp)
                .background(Color.Gray))
    }
}

@Composable
private fun UpperImagesRow() {
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
}

@Composable
private fun BackButton(onBackPressed: () -> Unit) {
    Button(onClick = {
        onBackPressed()
    }) {
        Text("Back", fontSize = 30.sp)
    }
}
