package com.santiihoyos.marvelpocket.domain.usecase.impl

import com.santiihoyos.marvel.data.repository.CharacterRepository
import com.santiihoyos.marvelpocket.domain.entity.Character
import com.santiihoyos.marvelpocket.domain.extension.toCharacter
import com.santiihoyos.marvelpocket.domain.usecase.GetPaginatedCharacters

class GetPaginatedCharactersImpl(
    var itemsPerPage: Int = 20,
    private val characterRepository: CharacterRepository
): GetPaginatedCharacters {

    override suspend fun getCharactersPage(page: Int): Result<List<Character>> {
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
            result.exceptionOrNull()?.printStackTrace()
            Result.failure(Exception("Error on ${this::class.java.name}"))
        }
    }
}