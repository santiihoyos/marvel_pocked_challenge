package com.santiihoyos.marvelpocket.ui.feature.characters.list

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.santiihoyos.marvelpocket.domain.entity.Character
import com.santiihoyos.marvelpocket.domain.usecase.GetPaginatedCharactersUseCase
import com.santiihoyos.marvelpocket.ui.feature.characters.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * State that UI for View
 */
data class CharacterListUiState(

    /**
     * If this it is not null View should show to user
     * error String resource
     */
    @StringRes
    val errorText: Int? = null,

    /**
     * Indicates viewModel is processing some task
     */
    val isLoading: Boolean = false,

    /**
     * [Pager] to allows view loads/show paginated [Character] [List]
     */
    val charactersListPager: Flow<PagingData<Character>>? = null
)

class CharacterListViewModel(
    private val getPaginatedCharacters: GetPaginatedCharactersUseCase,
) : ViewModel() {

    /**
     * current state that View should show.
     */
    var uiState by mutableStateOf(CharacterListUiState(isLoading = true))
        private set

    /**
     * ensure page limits and initialize listening to [this.viewModelState].
     */
    init {
        getPaginatedCharacters.itemsPerPage = 20
    }

    /**
     * Event from paginated list when a error happens
     */
    fun onLoadError() {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = false,
                errorText = R.string.error_loading_characters,
            )
        }
    }

    fun onFirstLoad() {
        uiState = uiState.copy(isLoading = false)
    }

    fun loadCharactersPager() {
        uiState = uiState.copy(
            isLoading = true,
            errorText = null,
            charactersListPager = Pager(
                initialKey = 1,
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false,
                    prefetchDistance = 2
                ),
                pagingSourceFactory = {
                    getPaginatedCharacters.getCharactersPagingSource(::onLoadCharactersListener)
                },
            ).flow.cachedIn(viewModelScope)
        )
    }

    /**
     * Manage errors on Pager.
     *
     * @param result- last result on pager.
     */
    private fun onLoadCharactersListener(currentPage: Int, result: Result<List<Character>>) {
        if (currentPage >= 0 && result.isSuccess) {
            uiState = uiState.copy(isLoading = false)
        } else if(result.isFailure) {
            uiState = uiState.copy(isLoading = false, errorText = R.string.error_loading_characters)
        }
    }
}