package com.santiihoyos.marvelpocket.ui.feature.characters.list

import androidx.paging.PagingSource
import com.santiihoyos.marvelpocket.domain.usecase.GetPaginatedCharactersUseCase
import com.santiihoyos.marvelpocket.domain.usecase.impl.CharacterPagingSource
import com.santiihoyos.marvelpocket.ui.feature.characters.CharacterMocks
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class CharacterListViewModelTest {

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `test init uiState is loading`() = runTest {
        val getUseCaseMock = mock<GetPaginatedCharactersUseCase>()
        whenever(getUseCaseMock.getCharactersByPage(any(), any())).then {
            return@then Result.success(CharacterMocks.page1)
        }
        val viewModel = CharacterListViewModel(getUseCaseMock)
        assert(
            viewModel.uiState.isLoading &&
                    viewModel.uiState.errorText == null &&
                    viewModel.uiState.charactersListPager == null
        )
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `test uiState is loading with CharacterPagingSource`() = runTest {
        val getUseCaseMock = mock<GetPaginatedCharactersUseCase>()
        whenever(getUseCaseMock.getCharactersByPage(any(), any())).then {
            return@then Result.success(CharacterMocks.page1)
        }
        val viewModel = CharacterListViewModel(getUseCaseMock)
        whenever(getUseCaseMock.getCharactersPagingSource(any(), any())).then {
            runTest {
                CharacterPagingSource(
                    3,
                    getUseCaseMock::getCharactersByPage,
                    viewModel::onLoadCharactersListener
                )
            }
        }
        viewModel.loadCharactersPager()
        assert(
            viewModel.uiState.charactersListPager != null &&
                    viewModel.uiState.isLoading &&
                    viewModel.uiState.errorText == null
        )
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `test when load some page success isLoading is false`() = runTest {
        val getUseCaseMock = mock<GetPaginatedCharactersUseCase>()
        val viewModel = CharacterListViewModel(getUseCaseMock)
        whenever(getUseCaseMock.getCharactersPagingSource(any(), any())).then {
            runTest {
                CharacterPagingSource(
                    3,
                    { _, _ -> Result.success(CharacterMocks.page1) },
                    viewModel::onLoadCharactersListener
                )
            }
        }
        viewModel.loadCharactersPager()
        assert(
            viewModel.uiState.charactersListPager != null && viewModel.uiState.isLoading
        )
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `test uiState is not loading after some load`() = runTest {
        val getUseCaseMock = mock<GetPaginatedCharactersUseCase>()
        val viewModel = CharacterListViewModel(getUseCaseMock)
        val pagingSource = CharacterPagingSource(
            3,
            { _, _ -> Result.success(CharacterMocks.page1) },
            viewModel::onLoadCharactersListener
        )
        whenever(getUseCaseMock.getCharactersPagingSource(any(), any())).then { pagingSource }
        viewModel.loadCharactersPager()
        pagingSource.load(PagingSource.LoadParams.Refresh(key = 1, loadSize = 3, false))
        assert(
            !viewModel.uiState.isLoading && viewModel.uiState.errorText == null &&
                    viewModel.uiState.charactersListPager != null
        )
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `test uiState is not loading and there is errorText on fail load`() = runTest {
        val getUseCaseMock = mock<GetPaginatedCharactersUseCase>()
        val viewModel = CharacterListViewModel(getUseCaseMock)
        val pagingSource = CharacterPagingSource(
            3,
            { _, _ -> Result.success(CharacterMocks.page1) },
            viewModel::onLoadCharactersListener
        )
        whenever(getUseCaseMock.getCharactersPagingSource(any(), any())).then { pagingSource }
        viewModel.loadCharactersPager()
        pagingSource.load(PagingSource.LoadParams.Refresh(key = 1, loadSize = 3, false))
        assert(
            !viewModel.uiState.isLoading && viewModel.uiState.errorText != null &&
                    viewModel.uiState.charactersListPager != null
        )
    }
}