package com.example.jetpackcomposeapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {
    @Query("SELECT * FROM animals ORDER BY id ASC")
    fun getAllAnimals(): MutableList<AnimalItem>

    @Query("SELECT * FROM animals ORDER BY id ASC")
    fun getAllAnimalsLive(): LiveData<MutableList<AnimalItem>>

    @Query("SELECT * FROM animals ORDER BY id ASC")
    fun getAllAnimalsFlow(): Flow<MutableList<AnimalItem>>


    @Query("SELECT * FROM animals WHERE id = :id")
    fun getAnimalById(id: Int): AnimalItem

    @Query("DELETE FROM animals")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(animal: AnimalItem): Long

    @Delete
    fun delete(animal: AnimalItem) : Int

    @Update
    suspend fun updateAnimal(animal: AnimalItem)
}