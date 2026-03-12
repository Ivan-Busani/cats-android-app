package com.mivanba.catsapp.data

data class Cat(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breeds: List<Breed>? = null
)

data class Breed(
    val name: String,
    val temperament: String,
    val origin: String
)