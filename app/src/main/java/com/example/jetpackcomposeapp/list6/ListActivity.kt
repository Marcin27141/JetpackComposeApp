package com.example.jetpackcomposeapp.list6

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeapp.MainActivity
import com.example.jetpackcomposeapp.database.AnimalItem
import com.example.jetpackcomposeapp.ui.theme.JetpackComposeAppTheme

class ListActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackComposeAppTheme {
                // A surface container using the 'background' color from the theme

                var animalsList by remember {
                    mutableStateOf(MyRepository.getInstance(applicationContext).getAnimals().toList())
                }
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    Column (horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Animals", fontSize = 42.sp, modifier = Modifier.padding(20.dp))
                        ItemsList(animalsList,
                            {animal -> startActivity(AnimalDetails.newIntent(applicationContext, animal))},
                            {animal ->
                                run {
                                    if (MyRepository.getInstance(applicationContext).deleteAnimal(animal))
                                        animalsList = MyRepository.getInstance(applicationContext).getAnimals()
                                }
                            })
                    }
                    FloatingActionButton(onClick = { startActivity(Intent(applicationContext, CreateAnimal::class.java))},
                        modifier = Modifier.align(Alignment.BottomEnd).padding(40.dp)) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun some() {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()) {
            Text("Animals", fontSize = 42.sp, modifier = Modifier.padding(20.dp))
        }
        FloatingActionButton(onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.BottomEnd).padding(40.dp)) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }

}


@Composable
fun ItemsList(animals: List<AnimalItem>,
              navigateToProfile: (AnimalItem) -> Unit,
              tryDeleteAnimal: (AnimalItem) -> Unit) {
    LazyColumn {
        items(
            items = animals,
            itemContent = {
                AnimalListItem(animal = it, navigateToProfile, tryDeleteAnimal)
            })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimalListItem(animal: AnimalItem,
                   navigateToDetails: (AnimalItem) -> Unit,
                   tryDeleteAnimal: (AnimalItem) -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(Modifier.combinedClickable(
            onClick = { navigateToDetails(animal) },
            onLongClick = {
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder
                    .setTitle("Are you sure?")
                    .setMessage("You are going to delete " + animal.name + ". Do you want to continue?")
                    .setPositiveButton("Yes") { _, _ ->
                        tryDeleteAnimal(animal)
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.cancel()
                    }
                builder.create().show()
            }
        )) {
            AnimalListImage(animal)
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = animal.name, style = typography.headlineMedium)
                Text(text = animal.latinName, fontSize = 16.sp, fontStyle = FontStyle.Italic)
            }
        }
    }
}

@Composable
private fun AnimalListImage(animal: AnimalItem) {
    Image(
        painter = painterResource(id = MyRepository.getAnimalIconId(animal)),
        contentDescription = "${animal.name} image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}