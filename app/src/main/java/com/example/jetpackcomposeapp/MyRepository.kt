package com.example.jetpackcomposeapp

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.jetpackcomposeapp.database.AnimalItem
import com.example.jetpackcomposeapp.database.MyDB
import com.example.jetpackcomposeapp.database.MyDao
import kotlinx.coroutines.flow.Flow

class MyRepository(context: Context) {
    private var myDao: MyDao
    private var db: MyDB

    fun getAnimals() : MutableList<AnimalItem> {
        return myDao.getAllAnimals()
    }

    fun getAnimalsLive() : LiveData<MutableList<AnimalItem>> {
        return myDao.getAllAnimalsLive()
    }

    fun getAnimalsFlow() : Flow<MutableList<AnimalItem>> {
        return myDao.getAllAnimalsFlow()
    }

    fun addAnimal(animal: AnimalItem) : Boolean {
        return myDao.insert(animal)>= 0
    }

    fun addAnimalWithId(animal: AnimalItem) : Long {
        return myDao.insert(animal)
    }

    fun deleteAnimal(animal: AnimalItem) : Boolean {
        return myDao.delete(animal) > 0
    }


    fun updateAnimal(id: Int, animal: AnimalItem) {
        deleteAnimal(getAnimalById(id)!!)
        addAnimal(animal)
    }

    fun getAnimalById(id: Int) : AnimalItem? {
        return myDao.getAnimalById(id)
    }

    suspend fun updateAnimal(animal: AnimalItem) {
        return myDao.updateAnimal(animal)
    }


    companion object {
        private var R_INSTANCE: MyRepository? = null
        fun getInstance(context: Context): MyRepository {
            if (R_INSTANCE == null) {
                R_INSTANCE = MyRepository(context)
            }
            return R_INSTANCE as MyRepository
        }
        fun getAnimalIconId(animalItem: AnimalItem) : Int {
            return when (animalItem.animalType) {
                AnimalItem.AnimalType.BIRD -> R.drawable.bird_icon
                AnimalItem.AnimalType.PREDATOR -> R.drawable.predator_icon
                AnimalItem.AnimalType.INSECT -> R.drawable.insect_icon
                AnimalItem.AnimalType.RODENT -> R.drawable.rodent_icon
            }
        }
    }

    init {
        db = MyDB.getDatabase(context)!!
        myDao = db.myDao()!!

        val initialAnimals = listOf(
            AnimalItem(1, "Trumpeter swan", "Cygnus buccinator", AnimalItem.AnimalType.BIRD, 5, 3f, false),
            AnimalItem(2, "Tiger mosquito", "Aedes albopictus", AnimalItem.AnimalType.INSECT, 1, 5f, true),
            AnimalItem(3, "Eurasian beaver", "Castor fiber", AnimalItem.AnimalType.RODENT, 3, 2f, false),
            AnimalItem(4, "Shoebill", "Balaeniceps rex", AnimalItem.AnimalType.BIRD, 4, 2f, false),
            AnimalItem(5, "Housefly ", "Musca domestica", AnimalItem.AnimalType.INSECT, 2, 2f, false),
            AnimalItem(6, "Snow leopard", "Panthera uncia", AnimalItem.AnimalType.PREDATOR, 4, 5f, true),
            AnimalItem(7, "Silverfish", "Lepisma saccharinum", AnimalItem.AnimalType.INSECT, 3, 1f, false),
            AnimalItem(8, "Tasmanian devil", "Sarcophilus harrisii", AnimalItem.AnimalType.PREDATOR, 3, 2f, false),
            AnimalItem(9, "Common Vole", "Microtus arvalis", AnimalItem.AnimalType.RODENT, 2, 2f, false),
            AnimalItem(10, "Bonelli's eagle", "Aquila fasciata", AnimalItem.AnimalType.BIRD, 4, 4f, true),
            AnimalItem(11, "Lion", "Panthera leo", AnimalItem.AnimalType.PREDATOR, 4, 5f, true),
            AnimalItem(12, "European mantis", "Mantis religiosa", AnimalItem.AnimalType.INSECT, 3, 3f, false),
            AnimalItem(13, "Black Widow Spider", "Latrodectus mactans", AnimalItem.AnimalType.INSECT, 2, 5f, true),
            AnimalItem(14, "Great Horned Owl", "Bubo virginianus", AnimalItem.AnimalType.BIRD, 3, 4f, true)
        )

        myDao.deleteAll()
        for (animal: AnimalItem in initialAnimals) {
            myDao.insert(animal)
        }
    }
}