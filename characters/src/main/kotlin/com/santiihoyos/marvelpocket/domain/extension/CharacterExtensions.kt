package com.santiihoyos.marvelpocket.domain.extension

import com.santiihoyos.marvel.data.entity.response.CharacterResponse
import com.santiihoyos.marvelpocket.domain.entity.Character

/**
 * Extension to map [CharacterResponse] to [Character]
 */
fun CharacterResponse.toCharacter(): Character {
    return Character(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = this.thumbnail?.path,
        imageExtension = this.thumbnail?.extension
    )
}