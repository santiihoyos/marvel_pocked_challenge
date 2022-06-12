package com.santiihoyos.marvel.data.repository.impl

import com.santiihoyos.marvel.data.datasource.CloudCharacterDataSource
import com.santiihoyos.marvel.data.datasource.LocalCharacterDataSource
import com.santiihoyos.marvel.data.entity.DataError
import com.santiihoyos.marvel.data.entity.response.CharacterResponse
import com.santiihoyos.marvel.data.repository.CharacterRepository
import retrofit2.http.HTTP
import javax.xml.crypto.Data

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
        return try {
            val response = cloudCharacterDataSource.getCharacters(orderBy, offset, limit)
            if (response.httpCode == 200 && response.data?.results != null) {
                Result.success(response.data.results)
            } else if (response.httpCode == 404) {
                Result.failure(DataError.NotFoundError)
            } else {
                Result.failure(DataError.UnknownError)
            }
        } catch (ex: Exception) {
            Result.failure(if (ex is DataError) ex else DataError.UnknownError)
        }
    }

    override suspend fun getCharactersById(id: String): Result<CharacterResponse> {
        return try {
            val response = cloudCharacterDataSource.getCharacterById(id)
            if (response.httpCode == 200 && response.data?.results != null) {
                Result.success(response.data.results[0])
            } else if (response.httpCode == 404) {
                Result.failure(DataError.NotFoundError)
            } else {
                Result.failure(DataError.UnknownError)
            }
            ///TODO: manage all possible api code errors here..
        } catch (ex: Exception) {
            Result.failure(if (ex is DataError) ex else DataError.UnknownError)
        }
    }
}