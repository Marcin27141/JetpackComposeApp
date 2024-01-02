package com.example.jetpackcomposeapp

import java.io.Serializable

class AnimalItem(
    var name: String,
    var latinName: String,
    var animalType: AnimalType,
    var health: Int,
    var strength: Float,
    var isDeadly: Boolean
): Serializable {

    enum class AnimalType {
        BIRD, INSECT, RODENT, PREDATOR
    }

    companion object {
        @JvmStatic
        fun getAnimalIconId(animalItem: AnimalItem) : Int {
            return when (animalItem.animalType) {
                AnimalType.BIRD -> R.drawable.bird_icon
                AnimalType.PREDATOR -> R.drawable.predator_icon
                AnimalType.INSECT -> R.drawable.insect_icon
                AnimalType.RODENT -> R.drawable.rodent_icon
            }
        }

        val initialAnimals = mutableListOf(
            AnimalItem("Trumpeter swan", "Cygnus buccinator", AnimalType.BIRD, 5, 3f, false),
            AnimalItem("Tiger mosquito", "Aedes albopictus", AnimalType.INSECT, 1, 5f, true),
            AnimalItem("Eurasian beaver", "Castor fiber", AnimalType.RODENT, 3, 2f, false),
            AnimalItem("Shoebill", "Balaeniceps rex", AnimalType.BIRD, 4, 2f, false),
            AnimalItem("Housefly ", "Musca domestica", AnimalType.INSECT, 2, 2f, false),
            AnimalItem("Snow leopard", "Panthera uncia", AnimalType.PREDATOR, 4, 5f, true),
            AnimalItem("Silverfish", "Lepisma saccharinum", AnimalType.INSECT, 3, 1f, false),
            AnimalItem("Tasmanian devil", "Sarcophilus harrisii", AnimalType.PREDATOR, 3, 2f, false),
            AnimalItem("Common Vole", "Microtus arvalis", AnimalType.RODENT, 2, 2f, false),
            AnimalItem( "Bonelli's eagle", "Aquila fasciata", AnimalType.BIRD, 4, 4f, true),
            AnimalItem( "Lion", "Panthera leo", AnimalType.PREDATOR, 4, 5f, true),
            AnimalItem( "European mantis", "Mantis religiosa", AnimalType.INSECT, 3, 3f, false),
            AnimalItem( "Black Widow Spider", "Latrodectus mactans", AnimalType.INSECT, 2, 5f, true),
            AnimalItem( "Great Horned Owl", "Bubo virginianus", AnimalType.BIRD, 3, 4f, true)
        )


    }


}