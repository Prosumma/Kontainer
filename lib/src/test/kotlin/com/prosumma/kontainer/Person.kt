package com.prosumma.kontainer

import kotlinx.serialization.Serializable

@Serializable
data class Person(val firstName: String, val lastName: String, val age: Int)