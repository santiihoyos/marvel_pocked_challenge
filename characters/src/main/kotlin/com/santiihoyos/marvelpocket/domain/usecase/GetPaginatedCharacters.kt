package com.santiihoyos.marvelpocket.domain.usecase

import com.santiihoyos.marvelpocket.domain.entity.Character

interface GetPaginatedCharacters {

    /**
     * Get a page for [Character]
     */
    suspend fun getCharactersPage(page: Int): Result<List<Character>>
}