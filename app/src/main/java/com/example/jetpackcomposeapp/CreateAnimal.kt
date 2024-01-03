package com.example.jetpackcomposeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeapp.database.AnimalItem
import com.example.jetpackcomposeapp.ui.theme.JetpackComposeAppTheme
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle

class CreateAnimal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShowCreate({ onBackPressedDispatcher.onBackPressed()}, {animal ->
                        val intent = Intent(this, ListActivity::class.java)
                        MyRepository.getInstance(this).addAnimal(animal);
                        intent.putExtra("newAnimal", animal.id)
                        startActivity(intent)
                    })
                }
            }
        }
    }
}

@Composable
fun ShowCreate(onBackPressed: () -> Unit, onSave: (AnimalItem) -> Unit) {
    val radioItems = listOf(
        AnimalItem.AnimalType.PREDATOR,
        AnimalItem.AnimalType.RODENT,
        AnimalItem.AnimalType.INSECT,
        AnimalItem.AnimalType.BIRD
    )
    var name = remember {
        mutableStateOf("")
    }
    var latin = remember {
        mutableStateOf("")
    }
    var radioOption = remember {
        mutableStateOf(-1)
    }
    var isDeadly = remember {
        mutableStateOf(false)
    }
    var health = remember {
        mutableStateOf(4)
    }
    var power = remember {
        mutableStateOf(3f)
    }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
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
                value = name.value,
                onValueChange = { text ->
                    name.value = text
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
                value = latin.value,
                onValueChange = { text ->
                    latin.value = text
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
                            selected = radioOption.value == i,
                            onClick = { radioOption.value = i },
                            enabled = true
                        )
                        Text(radioItems[i].toString().lowercase().replaceFirstChar { ch -> ch.uppercaseChar() }, fontSize = 16.sp)
                    }
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Is deadly?", fontSize = 20.sp)
                Checkbox(checked = isDeadly.value,
                    onCheckedChange = { checked -> isDeadly.value = checked })
            }

        }
        Row(
            modifier = Modifier.padding(14.dp),
        ) {
            Text("Health:", fontSize = 30.sp)
            Spacer(modifier = Modifier.width(20.dp))
            Slider(value = health.value.toFloat(), onValueChange = { newHealth -> health.value = newHealth.toInt() }, valueRange = 0f..5f, steps = 5)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(14.dp),
        ) {
            Text("Power:", fontSize = 30.sp)
            Spacer(modifier = Modifier.width(20.dp))
            RatingBar(
                value = power.value,
                style = RatingBarStyle.Fill(),
                onValueChange = { newPower -> power.value = newPower},
                onRatingChanged = {},
            )
        }
        Row(
            modifier = Modifier.padding(14.dp)
        ){
            Button(onClick = {
                if (name.value.isNotBlank() && latin.value.isNotBlank() && radioOption.value != -1) {
                    val animal = AnimalItem(0, name.value, latin.value, radioItems[radioOption.value], health.value, power.value, isDeadly.value)
                    onSave(animal)
                }
                else {
                    Toast.makeText(context, "Please fill necessary info", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Save", fontSize = 28.sp)
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = onBackPressed) {
                Text("Cancel", fontSize = 28.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreatePreview() {
    val radioItems = listOf(
        AnimalItem.AnimalType.PREDATOR,
        AnimalItem.AnimalType.RODENT,
        AnimalItem.AnimalType.INSECT,
        AnimalItem.AnimalType.BIRD
    )
    Column(
        modifier = Modifier.fillMaxSize(),
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
                value = "",
                onValueChange = { text ->

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
                value = "",
                onValueChange = { text ->

                },
                label = { Text("Latin name") },
                modifier = Modifier.weight(1f))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column (
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                for (i in 0..3) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = false,
                            onClick = { },
                            enabled = true
                        )
                        Text(radioItems[i].toString().lowercase().replaceFirstChar { ch -> ch.uppercaseChar() }, fontSize = 16.sp)
                    }
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Is deadly?", fontSize = 20.sp)
                Checkbox(checked = false, onCheckedChange = {})
            }

        }
        Row(
            modifier = Modifier.padding(14.dp),
        ) {
            Text("Health:", fontSize = 30.sp)
            Spacer(modifier = Modifier.width(20.dp))
            Slider(value = 3f, onValueChange = {}, valueRange = 0f..5f, steps = 5)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(14.dp),
        ) {
            Text("Power:", fontSize = 30.sp)
            Spacer(modifier = Modifier.width(20.dp))
            RatingBar(
                value = 3f,
                style = RatingBarStyle.Fill(),
                onValueChange = {},
                onRatingChanged = {},
            )
        }
        Row(
            modifier = Modifier.padding(14.dp)
        ){
            Button(onClick = {}) {
                Text("Save", fontSize = 28.sp)
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = { /*TODO*/ }) {
                Text("Cancel", fontSize = 28.sp)
            }
        }
    }
}