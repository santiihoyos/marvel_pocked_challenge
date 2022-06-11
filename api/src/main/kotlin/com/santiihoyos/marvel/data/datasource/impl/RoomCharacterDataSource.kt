package com.santiihoyos.marvel.data.datasource.impl

import com.santiihoyos.marvel.data.datasource.LocalCharacterDataSource
import com.santiihoyos.marvel.data.entity.response.CharacterResponse

internal class RoomCharacterDataSource : LocalCharacterDataSource {

    override suspend fun getFavoriteCharacters(): Result<List<CharacterResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveFavoriteCharacter(characterResponse: CharacterResponse): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavoriteCharacter(characterId: String): Result<List<CharacterResponse>> {
        TODO("Not yet implemented")
    }
}