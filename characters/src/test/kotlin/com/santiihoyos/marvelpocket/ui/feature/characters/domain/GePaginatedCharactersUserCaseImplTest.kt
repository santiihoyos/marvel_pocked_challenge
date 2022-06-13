package com.santiihoyos.marvelpocket.ui.feature.characters.domain

import com.santiihoyos.marvel.data.entity.DataError
import com.santiihoyos.marvel.data.repository.CharacterRepository
import com.santiihoyos.marvelpocket.domain.entity.error.CharacterError
import com.santiihoyos.marvelpocket.domain.usecase.impl.GetPaginatedCharactersImpl
import com.santiihoyos.marvelpocket.ui.feature.characters.CharacterMocks
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class GePaginatedCharactersUserCaseImplTest {

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `test any getCharacters page`() = runTest {
        val repoMock = mock<CharacterRepository>()
        whenever(repoMock.getCharactersByPage(any(), any(), any())).thenReturn(
            Result.success(CharacterMocks.characterResponses)
        )
        val getPaginatedCharactersImpl = GetPaginatedCharactersImpl(
            itemsPerPage = 3,
            characterRepository = repoMock
        )
        val result = getPaginatedCharactersImpl.getCharactersByPage(1)
        assert(result.isSuccess && result.getOrNull()?.count() == CharacterMocks.page1.count())
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `test failure when Repository fails`() = runTest {
        val repoMock = mock<CharacterRepository>()
        whenever(repoMock.getCharactersByPage(any(), any(), any())).thenReturn(
            Result.failure(DataError.NotFoundError)
        )
        val getPaginatedCharactersImpl = GetPaginatedCharactersImpl(
            itemsPerPage = 3,
            characterRepository = repoMock
        )
        val result = getPaginatedCharactersImpl.getCharactersByPage(1)
        assert(result.isFailure && result.exceptionOrNull() is CharacterError.UnknownCharacterError)
    }

    ///test more error codes here...
}