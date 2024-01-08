package com.example.jetpackcomposeapp.list6

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetpackcomposeapp.database.AnimalItem
import com.example.jetpackcomposeapp.services.MyRepository
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle


@Composable
fun ShowCreate(animal: AnimalItem?, navController: NavController) {
    val radioItems = listOf(
        AnimalItem.AnimalType.PREDATOR,
        AnimalItem.AnimalType.RODENT,
        AnimalItem.AnimalType.INSECT,
        AnimalItem.AnimalType.BIRD
    )
    var name by remember {
        mutableStateOf(animal?.name ?: "")
    }
    var latin by remember {
        mutableStateOf(animal?.latinName ?: "")
    }
    var radioOption by remember {
        mutableIntStateOf(radioItems.indexOf(animal?.animalType))
    }
    var isDeadly by remember {
        mutableStateOf(animal?.isDeadly ?: false)
    }
    var health by remember {
        mutableIntStateOf(animal?.health ?: 4)
    }
    var power by remember {
        mutableFloatStateOf(animal?.strength ?: 3f)
    }
    val context = LocalContext.current

    ContentWithDefaultAppBar {
        Column(
            modifier = Modifier.fillMaxSize().padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ) {
                Text("Name:", fontSize = 22.sp, modifier = Modifier.padding(14.dp))
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { text ->
                        name = text
                    },
                    label = { Text("Name") },
                    modifier = Modifier.weight(1f))
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ) {
                Text("Latin:", fontSize = 22.sp, modifier = Modifier.padding(14.dp))
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = latin,
                    onValueChange = { text ->
                        latin = text
                    },
                    label = { Text("Latin name") },
                    modifier = Modifier.weight(1f))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column (
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    for (i in 0..3) {
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = radioOption == i,
                                onClick = { radioOption = i },
                                enabled = true
                            )
                            Text(radioItems[i].toString().lowercase().replaceFirstChar { ch -> ch.uppercaseChar() }, fontSize = 16.sp)
                        }
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Is deadly?", fontSize = 20.sp)
                    Checkbox(checked = isDeadly,
                        onCheckedChange = { checked -> isDeadly = checked })
                }

            }
            Row(
                modifier = Modifier.padding(14.dp),
            ) {
                Text("Health:", fontSize = 30.sp)
                Spacer(modifier = Modifier.width(20.dp))
                Slider(value = health.toFloat(), onValueChange = { newHealth -> health = newHealth.toInt() }, valueRange = 0f..5f, steps = 5)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(14.dp),
            ) {
                Text("Power:", fontSize = 30.sp)
                Spacer(modifier = Modifier.width(20.dp))
                RatingBar(
                    value = power,
                    style = RatingBarStyle.Fill(),
                    onValueChange = { newPower -> power = newPower},
                    onRatingChanged = {},
                )
            }
            Row(
                modifier = Modifier.padding(14.dp)
            ){
                Button(onClick = {
                    if (name.isNotBlank() && latin.isNotBlank() && radioOption != -1) {
                        val newAnimal = AnimalItem(animal?.id ?: 0, name, latin, radioItems[radioOption], health, power, isDeadly)
                        if (animal == null) {
                            newAnimal.id = MyRepository.getInstance(context).addAnimalWithId(newAnimal).toInt()
                        }
                        else
                            MyRepository.getInstance(context).updateAnimal(animal.id, newAnimal)
                        navController.navigate("${AppScreens.AnimalDetails.name}/${newAnimal.id}")
                    }
                    else {
                        Toast.makeText(context, "Please fill necessary info", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Save", fontSize = 28.sp)
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text("Cancel", fontSize = 28.sp)
                }
            }
        }
    }


}
