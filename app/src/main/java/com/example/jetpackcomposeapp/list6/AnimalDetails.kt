package com.example.jetpackcomposeapp.list6

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetpackcomposeapp.database.AnimalItem
import com.example.jetpackcomposeapp.services.MyRepository
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle


@Composable
fun ShowDetails(animal: AnimalItem, navController: NavController) {
    ContentWithDefaultAppBar {
        Column(
            modifier = Modifier.fillMaxSize().padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(animal.name, fontSize = 38.sp, modifier = Modifier.padding(14.dp))
            Text(animal.latinName, fontSize = 28.sp, fontStyle = FontStyle.Italic, modifier = Modifier.padding(14.dp))
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = MyRepository.getAnimalIconId(animal)),
                    contentDescription = "${animal.name} image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(84.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                )
                Text(animal.animalType.toString(), fontSize = 30.sp)
            }
            Row(
                modifier = Modifier.padding(14.dp),
            ) {
                Text("Health:", fontSize = 30.sp)
                Spacer(modifier = Modifier.width(20.dp))
                Text(animal.health.toString(), fontSize = 30.sp)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(14.dp),
            ) {
                Text("Power:", fontSize = 30.sp)
                Spacer(modifier = Modifier.width(20.dp))
                RatingBar(
                    value = animal.strength,
                    style = RatingBarStyle.Fill(),
                    onValueChange = {},
                    onRatingChanged = {},
                )
            }
            val isDeadlyInfo = when (animal.isDeadly) {
                true -> "This is a deadly animal"
                false -> "This is not a deadly animal"
            }
            Text(text = isDeadlyInfo, fontSize = 30.sp, modifier = Modifier.padding(14.dp))
            Row(
                modifier = Modifier.padding(14.dp)
            ){
                Button(onClick = { navController.popBackStack(AppScreens.AnimalsList.name, false) }) {
                    Text("Back", fontSize = 28.sp)
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(onClick = {
                    navController.navigate("${AppScreens.AnimalForm.name}?id=${animal.id}")
                }) {
                    Text("Modify", fontSize = 28.sp)
                }
            }
        }
    }

}