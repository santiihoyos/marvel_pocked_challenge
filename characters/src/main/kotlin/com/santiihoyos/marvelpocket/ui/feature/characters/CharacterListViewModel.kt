package com.santiihoyos.marvelpocket.ui.feature.characters

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.santiihoyos.marvelpocket.domain.entity.Character
import com.santiihoyos.marvelpocket.domain.usecase.GetPaginatedCharacters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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

data class CharacterListViewModelState(
    val filterText: String? = null,
    val firstLoading: Boolean? = null,
    val isRetry: Boolean = false,
    val errorOnLoad: Boolean? = null,
)

class CharacterListViewModel(
    private val getPaginatedCharacters: GetPaginatedCharacters,
) : ViewModel() {

    /**
     * current state that View should show.
     */
    val uiState = MutableStateFlow(CharacterListUiState(isLoading = true))

    /**
     * current state that this [CharacterListViewModel] should manage.
     */
    val viewModelState = MutableStateFlow(CharacterListViewModelState(filterText = null))

    private val lastUiState: CharacterListUiState? = null
    private var lastViewModelState: CharacterListViewModelState? = null

    /**
     * ensure page limits and initialize listening to [this.viewModelState].
     */
    init {
        getPaginatedCharacters.itemsPerPage = 20
        listenViewModelState()
        viewModelScope.launch {
            uiState.emit(CharacterListUiState(isLoading = true))
        }
    }

    private fun listenViewModelState() = viewModelScope.launch {
        viewModelState.collect { _newViewModelState ->
            when {

                _newViewModelState.isRetry -> {
                    getNewPager()
                }

                _newViewModelState.firstLoading == true -> {
                    uiState.emit(
                        CharacterListUiState(isLoading = true)
                    )
                    getNewPager()
                }

                //Ui reports new error
                _newViewModelState.errorOnLoad == true ->
                    onLoadError()

                //Ui report new text
                _newViewModelState.filterText != null
                        && _newViewModelState.filterText != lastViewModelState?.filterText ->
                    getNewPager()

                _newViewModelState.firstLoading == false ->
                    uiState.emit(
                        CharacterListUiState(
                            isLoading = false,
                            charactersListPager = uiState.value.charactersListPager
                        )
                    )
            }
            lastViewModelState = _newViewModelState
        }
    }

    private fun onLoadError() {
        viewModelScope.launch {
            uiState.emit(
                CharacterListUiState(
                    isLoading = false,
                    errorText = R.string.error_loading_characters,
                    charactersListPager = uiState.value.charactersListPager
                )
            )
        }
    }

    private suspend fun getNewPager() {
        uiState.emit(
            CharacterListUiState(
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
                        getPaginatedCharacters.getCharactersPagingSource()
                    },
                ).flow.cachedIn(viewModelScope)
            )
        )
    }
}