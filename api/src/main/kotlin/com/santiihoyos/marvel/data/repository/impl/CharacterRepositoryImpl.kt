package com.santiihoyos.marvel.data.repository.impl

import com.santiihoyos.marvel.data.datasource.CloudCharacterDataSource
import com.santiihoyos.marvel.data.datasource.LocalCharacterDataSource
import com.santiihoyos.marvel.data.entity.response.CharacterResponse
import com.santiihoyos.marvel.data.repository.CharacterRepository
import retrofit2.http.HTTP

/**
 * By using cloud and room DataSources implements
 * [CharacterRepository] specification
 */
internal class CharacterRepositoryImpl(
    private val cloudCharacterDataSource: CloudCharacterDataSource,
    private val localCharacterDataSource: LocalCharacterDataSource,
) : CharacterRepository {

    override suspend fun getCharactersByPage(
        orderBy: String,
        offset: Int,
        limit: Int
    ): Result<List<CharacterResponse>> {
        val response = cloudCharacterDataSource.getCharacters(orderBy, offset, limit)
        if (response.httpCode == 200 && response.data?.results != null ) {
            return Result.success(response.data.results)
        }
        return Result.failure(NotImplementedError())
    }

    override suspend fun getCharactersById(id: String): Result<CharacterResponse> {
        return Result.failure(NotImplementedError())
    }
}