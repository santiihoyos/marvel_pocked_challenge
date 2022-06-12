package com.santiihoyos.marvelpocket.domain.usecase

import androidx.paging.PagingSource
import com.santiihoyos.marvelpocket.domain.entity.Character
interface GetPaginatedCharacters {

    /**
     * Items per load page
     */
    var itemsPerPage: Int

    /**
     * Get a page for [Character]
     * @param page - current page to load
     * @return [Result] of [List] of [Character] if next items were loaded ok, manage failure
     *          to known there is error.
     */
    suspend fun getCharactersByPage(page: Int): Result<List<Character>>

    /**
     *
     */
    fun getCharactersPagingSource(): PagingSource<Int, Character>
}