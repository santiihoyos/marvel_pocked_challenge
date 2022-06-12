package com.santiihoyos.marvelpocket.domain.usecase

import com.santiihoyos.marvelpocket.domain.entity.Character

interface GetCharacterByIdUseCase {

    /**
     * Gets a Character by Id from server.
     * @return [Result] of [Character] when went ok.
     */
    suspend fun getCharacterById(id: String): Result<Character>
}