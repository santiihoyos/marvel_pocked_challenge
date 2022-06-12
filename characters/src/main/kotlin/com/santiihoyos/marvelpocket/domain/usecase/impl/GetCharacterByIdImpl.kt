package com.santiihoyos.marvelpocket.domain.usecase.impl

import com.santiihoyos.marvel.data.entity.DataError
import com.santiihoyos.marvel.data.repository.CharacterRepository
import com.santiihoyos.marvelpocket.domain.entity.Character
import com.santiihoyos.marvelpocket.domain.entity.error.CharacterError
import com.santiihoyos.marvelpocket.domain.extension.toCharacter
import com.santiihoyos.marvelpocket.domain.usecase.GetCharacterByIdUseCase

class GetCharacterByIdImpl(
    private val characterRepository: CharacterRepository
) : GetCharacterByIdUseCase {

    override suspend fun getCharacterById(id: String): Result<Character> {
        val result = characterRepository.getCharactersById(id)
        return if (result.isSuccess && result.getOrNull() != null) {
            Result.success(result.getOrNull()!!.toCharacter())
        } else {
            Result.failure(
                if (result.exceptionOrNull() is DataError.NotFoundError) {
                    CharacterError.NotFoundCharacterError
                } else {
                    CharacterError.UnknownCharacterError
                }
            )
        }
    }
}