package com.santiihoyos.marvelpocket.ui.feature.characters.detail

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santiihoyos.marvelpocket.domain.entity.Character
import com.santiihoyos.marvelpocket.domain.usecase.GetCharacterByIdUseCase
import com.santiihoyos.marvelpocket.ui.feature.characters.R
import kotlinx.coroutines.launch

/**
 * UI state for CharacterDetail view.
 */
data class CharacterDetailUiState(

    /**
     * View should to show some loading status
     */
    val isLoading: Boolean = false,

    /**
     * View should show character information
     */
    val character: Character? = null,

    /**
     * String Res id to show on view when something went wrong
     */
    @StringRes val errorText: Int? = null
)

/**
 * CharacterDetailViewModel state
 */
data class CharacterDetailViewModelState(

    /**
     * Id of character that view requires.
     */
    val characterId: String? = null,
)

/**
 * ViewModel class for Character detail views.
 */
class CharacterDetailViewModel(
    private val getCharacterById: GetCharacterByIdUseCase
) : ViewModel() {

    /**
     * current state that View should show.
     */
    var uiState by mutableStateOf(CharacterDetailUiState(isLoading = true))
        private set

    fun getCharacterById(characterId: String?) = viewModelScope.launch {
        if (characterId.isNullOrEmpty()) {
            uiState = uiState.copy(isLoading = false, errorText = R.string.error_loading_character)
        }
        uiState = uiState.copy(isLoading = true, errorText = null, character = null)
        val resultCharacter = getCharacterById.getCharacterById(characterId!!)
        uiState = if (resultCharacter.isSuccess) {
            uiState.copy(
                isLoading = false,
                errorText = null,
                character = resultCharacter.getOrNull()
            )
        } else {
            uiState.copy(isLoading = false, errorText = R.string.error_loading_character)
        }
    }
}