package org.example.project

import kotlinx.serialization.Serializable

@Serializable
data class Joke(
    val joke: String
)