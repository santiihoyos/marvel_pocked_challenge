package com.santiihoyos.marvel.data.datasource

import com.santiihoyos.marvel.data.entity.response.BaseResponse
import com.santiihoyos.marvel.data.entity.response.CharacterResponse

interface CloudCharacterDataSource {

    suspend fun getCharacters(
        orderBy: String,
        offset: Int,
        limit: Int
    ): BaseResponse<CharacterResponse>

    suspend fun getCharacterById(id: String): BaseResponse<CharacterResponse>
}