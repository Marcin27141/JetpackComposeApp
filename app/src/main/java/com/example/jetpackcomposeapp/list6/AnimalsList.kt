package com.example.jetpackcomposeapp.list6

import android.app.AlertDialog
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.jetpackcomposeapp.R
import com.example.jetpackcomposeapp.database.AnimalItem
import com.example.jetpackcomposeapp.services.ImageRepo
import com.example.jetpackcomposeapp.services.MyRepository

@Composable
fun ShowAnimalsList(navController: NavController) {
    val context = LocalContext.current
    var animalsList by remember {
        mutableStateOf(MyRepository.getInstance(context).getAnimals().toList())
    }
    var selectedAnimals = remember {
        mutableStateListOf<Int>()
    }
    var showDeleteMenuIcon by remember { mutableStateOf(false) }

    Scaffold(
        topBar = if (showDeleteMenuIcon) {
            { DefaultAppBar {
                IconButton(onClick = {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    builder
                        .setTitle("Are you sure?")
                        .setMessage("You are about to delete " + selectedAnimals.size + " animals. Do you want to continue?")
                        .setPositiveButton("Yes") { _, _ ->
                            val repository = MyRepository.getInstance(context)
                            animalsList.filter { selectedAnimals.contains(it.id) }.forEach {
                                repository.deleteAnimal(it)
                            }
                            animalsList = animalsList.filter { !selectedAnimals.contains(it.id) }
                            selectedAnimals.clear()

                            Toast.makeText(context, "Delete successful", Toast.LENGTH_SHORT).show()
                        }
                        .setNegativeButton("No") { dialog, _ ->
                            dialog.cancel()
                        }
                    builder.create().show()
                }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Shared storage"
                    )
                }
            } }
        } else { { DefaultAppBar {} } }
    ) { values ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
        ){
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Animals", fontSize = 42.sp, modifier = Modifier.padding(20.dp))
                AnimalsList(animalsList, selectedAnimals,
                    { animal, isChecked ->
                        if (isChecked) selectedAnimals.add(animal.id) else selectedAnimals.remove(animal.id)
                        showDeleteMenuIcon = selectedAnimals.isNotEmpty()
                    },
                    {animal ->
                        navController.navigate("${AppScreens.AnimalDetails.name}/${animal.id}")
                    },
                    {animal ->
                        run {
                            if (MyRepository.getInstance(context).deleteAnimal(animal)) {
                                if (selectedAnimals.contains(animal.id))
                                    selectedAnimals.remove(animal.id)
                                animalsList = MyRepository.getInstance(context).getAnimals()
                            }

                        }
                    })
            }
            FloatingActionButton(onClick = { navController.navigate(AppScreens.AnimalForm.name) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(40.dp)) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    }
}

@Composable
fun AnimalsList(animals: List<AnimalItem>, selectedIds: List<Int>,
                onSelectedChanged: (AnimalItem, Boolean) -> Unit,
                navigateToDetails: (AnimalItem) -> Unit,
                tryDeleteAnimal: (AnimalItem) -> Unit) {
    LazyColumn {
        items(
            items = animals,
            itemContent = {
                AnimalListItem(animal = it,
                    selectedIds.contains(it.id),
                    onSelectedChanged,
                    navigateToDetails, tryDeleteAnimal)
            })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimalListItem(animal: AnimalItem,
                   isChecked: Boolean,
                   onSelectedChanged: (AnimalItem, Boolean) -> Unit,
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
        ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimalListImage(animal)
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                val nameColor = if (animal.isDeadly) Color.Red else Color.Green
                Text(text = animal.name, fontSize = 22.sp, color = nameColor)
                Text(text = animal.latinName, fontSize = 16.sp, fontStyle = FontStyle.Italic)
            }
            Checkbox(checked = isChecked,
                onCheckedChange = {
                    onSelectedChanged(animal, !isChecked)
                },
                modifier = Modifier.padding(horizontal = 10.dp)
            )
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

//@Preview
//@Composable
//fun Preview() {
//    val animalsList = listOf(
//        AnimalItem(1, "Trumpeter swan", "Cygnus buccinator", AnimalItem.AnimalType.BIRD, 5, 3f, false),
//        AnimalItem(2, "Tiger mosquito", "Aedes albopictus", AnimalItem.AnimalType.INSECT, 1, 5f, true),
//        AnimalItem(3, "Eurasian beaver", "Castor fiber", AnimalItem.AnimalType.RODENT, 3, 2f, false),
//        AnimalItem(4, "Shoebill", "Balaeniceps rex", AnimalItem.AnimalType.BIRD, 4, 2f, false),
//        AnimalItem(5, "Housefly ", "Musca domestica", AnimalItem.AnimalType.INSECT, 2, 2f, false),
//        AnimalItem(6, "Snow leopard", "Panthera uncia", AnimalItem.AnimalType.PREDATOR, 4, 5f, true),
//        AnimalItem(7, "Silverfish", "Lepisma saccharinum", AnimalItem.AnimalType.INSECT, 3, 1f, false),
//        AnimalItem(8, "Tasmanian devil", "Sarcophilus harrisii", AnimalItem.AnimalType.PREDATOR, 3, 2f, false),
//        AnimalItem(9, "Common Vole", "Microtus arvalis", AnimalItem.AnimalType.RODENT, 2, 2f, false),
//        AnimalItem(10, "Bonelli's eagle", "Aquila fasciata", AnimalItem.AnimalType.BIRD, 4, 4f, true),
//        AnimalItem(11, "Lion", "Panthera leo", AnimalItem.AnimalType.PREDATOR, 4, 5f, true),
//        AnimalItem(12, "European mantis", "Mantis religiosa", AnimalItem.AnimalType.INSECT, 3, 3f, false),
//        AnimalItem(13, "Black Widow Spider", "Latrodectus mactans", AnimalItem.AnimalType.INSECT, 2, 5f, true),
//        AnimalItem(14, "Great Horned Owl", "Bubo virginianus", AnimalItem.AnimalType.BIRD, 3, 4f, true)
//    )
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        Column (horizontalAlignment = Alignment.CenterHorizontally) {
//            Text("Animals", fontSize = 42.sp, modifier = Modifier.padding(20.dp))
//            AnimalsList(animalsList,
//                { sth, other -> },
//                {animal ->
//                },
//                {animal ->
//                    run {
//                    }
//                })
//        }
//        FloatingActionButton(onClick = { },
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(40.dp)) {
//            Icon(imageVector = Icons.Default.Add, contentDescription = null)
//        }
//    }
//}