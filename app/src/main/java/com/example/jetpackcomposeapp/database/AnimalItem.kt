package com.example.jetpackcomposeapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animals")
class AnimalItem constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var name: String = "",
    var latinName: String = "",
    var animalType: AnimalType = AnimalType.BIRD,
    var health: Int = 0,
    var strength: Float = 0f,
    var isDeadly: Boolean = false,
){

    enum class AnimalType {
        BIRD, INSECT, RODENT, PREDATOR
    }
}