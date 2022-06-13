package com.santiihoyos.marvel.data.repository

import com.santiihoyos.marvel.data.Mocks
import com.santiihoyos.marvel.data.datasource.impl.RestMarvelDataSource
import com.santiihoyos.marvel.data.entity.DataError
import com.santiihoyos.marvel.data.repository.impl.CharacterRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class) //android libraries...
class CharacterRepositoryImplTest {

    @Test
    fun `test getCharactersByPage was ok`() = runTest {
        val restDataSourceMock = mock<RestMarvelDataSource>()
        whenever(restDataSourceMock.getCharacters(any(), any(), any())).thenReturn(
            Mocks.baseResponse200
        )
        val repo = CharacterRepositoryImpl(restDataSourceMock, mock())
        val result = repo.getCharactersByPage("name", 1, limit = 3)
        assert(
            result.isSuccess && (result.getOrNull()?.count()
                ?: 0) == Mocks.baseResponse200.data?.count &&
                    result.getOrNull()?.get(1) == Mocks.baseResponse200.data?.results?.get(1)
        )
    }

    @Test
    fun `test getCharactersByPage was ko with DataError_NotFound on 404`() = runTest {
        val restDataSourceMock = mock<RestMarvelDataSource>()
        whenever(restDataSourceMock.getCharacters(any(), any(), any())).thenReturn(
            Mocks.baseResponse404
        )
        val repo = CharacterRepositoryImpl(restDataSourceMock, mock())
        val result = repo.getCharactersByPage("name", 1, limit = 3)
        assert(
            result.isFailure && result.exceptionOrNull() is DataError.NotFoundError
        )
    }

    @Test
    fun `test getCharactersByPage was ko with DataError_Unknown on 500`() = runTest {
        val restDataSourceMock = mock<RestMarvelDataSource>()
        whenever(restDataSourceMock.getCharacters(any(), any(), any())).thenReturn(
            Mocks.baseResponse500
        )
        val repo = CharacterRepositoryImpl(restDataSourceMock, mock())
        val result = repo.getCharactersByPage("name", 1, limit = 3)
        assert(
            result.isFailure && result.exceptionOrNull() is DataError.UnknownError
        )
    }

    @Test
    fun `test getCharactersByPage was ko with DataError_Unknown on another Exception`() = runTest {
        val restDataSourceMock = mock<RestMarvelDataSource>()
        whenever(restDataSourceMock.getCharacters(any(), any(), any())).thenAnswer {
            throw Exception()
        }
        val repo = CharacterRepositoryImpl(restDataSourceMock, mock())
        val result = repo.getCharactersByPage("name", 1, limit = 3)
        assert(
            result.isFailure && result.exceptionOrNull() is DataError.UnknownError
        )
    }

}