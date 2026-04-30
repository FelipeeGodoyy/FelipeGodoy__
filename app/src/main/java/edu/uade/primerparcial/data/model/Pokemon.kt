package edu.uade.primerparcial.data.model

data class Pokemon(
    val id: Int,
    val name: String
) {
    val spriteUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

    val nameFormatted: String
        get() = name.replaceFirstChar { it.uppercase() }
}
