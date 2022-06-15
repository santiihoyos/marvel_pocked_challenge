package com.santiihoyos.marvelpocket.domain.usecase.impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.santiihoyos.marvel.data.repository.CharacterRepository
import com.santiihoyos.marvelpocket.domain.entity.Character
import com.santiihoyos.marvelpocket.domain.entity.error.CharacterError
import com.santiihoyos.marvelpocket.domain.extension.toCharacter
import com.santiihoyos.marvelpocket.domain.usecase.GetPaginatedCharactersUseCase

class GetPaginatedCharactersImpl(
    private val characterRepository: CharacterRepository
) : GetPaginatedCharactersUseCase {

    override suspend fun getCharactersByPage(page: Int, itemsPerPage: Int): Result<List<Character>> {
        val result = characterRepository.getCharactersByPage(
            limit = itemsPerPage,
            offset = page * itemsPerPage,
            orderBy = "name"
        )
        return if (result.isSuccess && result.getOrNull() != null) {
            Result.success(
                result.getOrNull()!!.map {
                    it.toCharacter()
                }.toList()
            )
        } else {
            Result.failure(CharacterError.UnknownCharacterError)
        }
    }

    override fun getCharactersPagingSource(
        itemsPerPage: Int,
        onLoad: (Int, Result<List<Character>>) -> Unit
    ): PagingSource<Int, Character> {
        return CharacterPagingSource(itemsPerPage, this::getCharactersByPage, onLoad)
    }
}

/**
 * Paging Source.
 *
 * DISCLAIMER: Google says that we should put it into data layer but our data layer is android
 * libraries agnostic so we keep it here to allow reuse without forcing api module
 * accomplishment to android specific implementations.
 */
class CharacterPagingSource(
    private val itemsPerPage: Int,
    private val onNextPage: suspend (page: Int, itemsPerPage: Int) -> Result<List<Character>>,
    private val onLoad: (currentPage: Int, Result<List<Character>>) -> Unit
) : PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val nextPage = params.key ?: 1
        val characters = onNextPage(nextPage, itemsPerPage)
        onLoad(nextPage, characters)
        return if (characters.isFailure) {
            LoadResult.Error(
                characters.exceptionOrNull() ?: UnknownError()
            )
        } else {
            val newCharacters = characters.getOrNull()
            LoadResult.Page(
                data = newCharacters ?: emptyList(),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (newCharacters.isNullOrEmpty()) null else nextPage + 1
            )
        }
    }
}