package com.example.jetpackcomposeapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.jetpackcomposeapp.services.MyRepository
import kotlinx.coroutines.flow.Flow

class MyViewModel(context: Context) : ViewModel() {
    private var myRepo = MyRepository(context)
    fun getAllItems() : MutableList<AnimalItem> = myRepo.getAnimals()!!
    fun getAllItemsLive() : LiveData<MutableList<AnimalItem>> = myRepo.getAnimalsLive()

    fun getAllItemsFlow() : Flow<MutableList<AnimalItem>> = myRepo.getAnimalsFlow()

    fun addItem(item: AnimalItem) = myRepo.addAnimal(item)
    fun deleteItem(item: AnimalItem) = myRepo.deleteAnimal(item)
    fun getAnimalById(animalItemId: Int): AnimalItem? = myRepo.getAnimalById(animalItemId)
    fun updateAnimal(id: Int, animal: AnimalItem) = myRepo.updateAnimal(id, animal)

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MyViewModel(application.applicationContext) as T
            }
        }
    }
}