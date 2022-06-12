package com.santiihoyos.marvelpocket.ui.feature.characters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.santiihoyos.marvelpocket.domain.entity.Character
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Composable
fun CharacterList(characterListViewModel: CharacterListViewModel) {
    Surface(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val uiState = characterListViewModel.uiState.collectAsState()
        when {
            uiState.value.charactersListPager != null -> {
                CharacterList(
                    characterListViewModel = characterListViewModel,
                    charactersFlow = uiState.value.charactersListPager!!,
                    firstLoading = uiState.value.isLoading,
                    showErrorDialog = uiState.value.errorText != null
                )
            }
        }
    }
}

@Composable
fun CharacterList(
    characterListViewModel: CharacterListViewModel,
    charactersFlow: Flow<PagingData<Character>>,
    firstLoading: Boolean,
    showErrorDialog: Boolean,
) {
    val charactersState = charactersFlow.collectAsLazyPagingItems()
    val (showError, setDialog) = remember { mutableStateOf(showErrorDialog) }
    Surface {
        if (firstLoading) {
            InitialLoading()
        }
        LazyColumn {
            items(charactersState) { _character ->
                CharacterItem(character = _character!!)
                manageListState(characterListViewModel, charactersState)
            }
        }
        ErrorContainer(
            characterListViewModel = characterListViewModel,
            show = characterListViewModel.uiState.value.errorText != null,
            setShow = setDialog
        )
    }
}

@Composable
fun ErrorContainer(
    show: Boolean,
    setShow: (Boolean) -> Unit,
    characterListViewModel: CharacterListViewModel,
) {
    if (show) {
        AlertDialog(
            onDismissRequest = {
                setShow(false)
            },
            title = {
                Text(
                    text = stringResource(id = R.string.error_dialog_title),
                    style = TextStyle(color = MaterialTheme.colorScheme.error)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        characterListViewModel.viewModelScope.launch {
                            characterListViewModel.viewModelState.emit(
                                CharacterListViewModelState(
                                    filterText = characterListViewModel.viewModelState.value.filterText,
                                    isRetry = true,
                                )
                            )
                        }
                    },
                ) {
                    Text(stringResource(id = R.string.error_dialog_button_retry))
                }
            },
            text = {
                val textId = characterListViewModel.uiState.value.errorText
                Text(
                    if (textId != null) stringResource(id = textId) else "error.",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onErrorContainer,
                    )
                )
            },
        )
    }
}

@Composable
fun InitialLoading() {
    Dialog(
        onDismissRequest = {

        }, properties = DialogProperties()
    ) {
        Surface(
            color = Color.Black.copy(alpha = 0.2f)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CharacterItem(character: Character) {
    Card(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(all = 6.dp)
    ) {
        Row(
        ) {
            val imageUrl = character.getPreviewImageUrl();
            Surface(
                shape = CircleShape,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = character.name,
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp),
                    contentScale = ContentScale.Inside
                )
            }
            Text(text = character.name!!)
        }
    }
}

fun manageListState(
    characterListViewModel: CharacterListViewModel,
    lazyItems: LazyPagingItems<Character>
) {
    characterListViewModel.viewModelScope.launch {
        lazyItems.apply {
            val loadStates = this.loadState
            when {
                loadStates.append is LoadState.Error -> characterListViewModel.viewModelState.emit(
                    CharacterListViewModelState(firstLoading = false, errorOnLoad = true)
                )
                loadStates.source.refresh is LoadState.NotLoading -> {
                    if (loadStates.append.endOfPaginationReached && itemCount > 1) {
                        characterListViewModel.viewModelState.emit(
                            CharacterListViewModelState(firstLoading = true)
                        )
                    } else {
                        characterListViewModel.viewModelState.emit(
                            CharacterListViewModelState(firstLoading = false)
                        )
                    }
                }
            }
        }
    }
}