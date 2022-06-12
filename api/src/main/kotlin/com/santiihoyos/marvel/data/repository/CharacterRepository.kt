package com.santiihoyos.marvel.data.repository

import com.santiihoyos.marvel.data.entity.response.CharacterResponse

interface CharacterRepository {

    /**
     * Get all heroes of Disney & Marvel universe
     * This list is paginated.
     *
     * @param offset - current page to read
     * @return HeroesResponse with server response
     */
    suspend fun getCharactersByPage(
        orderBy: String,
        offset: Int,
        limit: Int
    ): Result<List<CharacterResponse>>

    /**
     * Get Only one hero searching by id
     *
     * @param id - unique id of hero
     * @return HeroesResponse with server response
     */
    suspend fun getCharactersById(id: String): Result<CharacterResponse>
}