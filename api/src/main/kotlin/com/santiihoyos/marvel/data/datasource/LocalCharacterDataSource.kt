package com.santiihoyos.marvel.data.datasource

import com.santiihoyos.marvel.data.entity.response.CharacterResponse

/**
 * Local storage operations
 */
interface LocalCharacterDataSource {

    /**
     * Get saved favorite characters.
     * @return - [List] of [CharacterResponse] or empty
     */
    suspend fun getFavoriteCharacters(): Result<List<CharacterResponse>>

    /**
     * Save into local storage a new Favorite Character
     */
    suspend fun saveFavoriteCharacter(
        characterResponse: CharacterResponse
    ): Result<Boolean>

    /**
     * Removes from local storage an favorite Character
     */
    suspend fun deleteFavoriteCharacter(
        characterId: String,
    ): Result<List<CharacterResponse>>
}