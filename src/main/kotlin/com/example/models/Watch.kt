package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Watch(
    val id : Int,
    val image : String,
    val rating : Double,
    val brand: String,
    val model : String,
    val description: String,
    val lugToLug : Float,
    val thickness: Float,
    val caseDiameter :Float,
    val caseMaterial : String,
    val dialColor : String,
    val crystal : String,
    val waterResistance : String,
    val movement : String,
    val powerReserve : Int,
    val bracelet : String,
    val openCaseBack : String
)
