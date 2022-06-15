package com.santiihoyos.marvelpocket.domain.usecase

import androidx.paging.PagingSource
import com.santiihoyos.marvelpocket.domain.entity.Character
interface GetPaginatedCharactersUseCase {

    /**
     * Get a page for [Character]
     * @param page - current page to load
     * @return [Result] of [List] of [Character] if next items were loaded ok, manage failure
     *          to known there is error.
     */
    suspend fun getCharactersByPage(page: Int, itemsPerPage: Int): Result<List<Character>>

    /**
     * Creates Page source by using Use case implementation call
     *
     * @param - onLoad allows to known load errors without depend on view events.
     * @return - ready [PagingSource]
     */
    fun getCharactersPagingSource(
        itemsPerPage: Int,
        onLoad: (Int, Result<List<Character>>) -> Unit
    ): PagingSource<Int, Character>
}