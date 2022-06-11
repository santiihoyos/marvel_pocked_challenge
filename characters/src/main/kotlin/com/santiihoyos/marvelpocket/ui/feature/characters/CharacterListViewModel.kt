package com.santiihoyos.marvelpocket.ui.feature.characters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.santiihoyos.marvelpocket.domain.entity.Character
import com.santiihoyos.marvelpocket.domain.usecase.GetPaginatedCharacters
import kotlinx.coroutines.flow.Flow

class CharacterListViewModel(
    //private val getCharacterById: GetCharacterById,
    private val getPaginatedCharacters: GetPaginatedCharacters,
    //private val getFavoriteCharacters: GetFavoriteCharacters,
    //private val addFavoriteCharacters: GetFavoriteCharacters,
): ViewModel() {

    val charactersFlow: Flow<PagingData<Character>> by lazy {
        Pager(
            initialKey = 1,
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            pagingSourceFactory = { CharacterPagingSource(this::getCharactersPage) },
        ).flow.cachedIn(viewModelScope)
    }

    suspend fun getCharactersPage(page: Int): Result<List<Character>> {
        val result = getPaginatedCharacters.getCharactersPage(page)
        return if (result.isSuccess && result.getOrNull() != null) {
            result
        } else {
            //TODO: manage error on get Characters here.
            Log.e(this.javaClass.name, "Error on get character page: $page")
            result.exceptionOrNull()?.printStackTrace()
            Result.failure(
                result.exceptionOrNull() ?: Exception("Error on get character page: $page")
            )
        }
    }
}

private class CharacterPagingSource(
    private val onGetNextPage: suspend (page: Int) -> Result<List<Character>>
) : PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val nextPage = params.key ?: 1
        val characters = onGetNextPage(nextPage)
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