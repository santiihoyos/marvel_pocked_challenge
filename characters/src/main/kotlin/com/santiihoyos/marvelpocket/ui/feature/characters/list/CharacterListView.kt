package com.santiihoyos.marvelpocket.ui.feature.characters.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.santiihoyos.marvelpocket.domain.entity.Character
import com.santiihoyos.marvelpocket.ui.feature.characters.R

@Composable
fun CharactersList(
    characterListViewModel: CharacterListViewModel,
    onNavigateToDetail: (characterId: String) -> Unit
) {

    LaunchedEffect(key1 = "onCreate", block = {
        if (characterListViewModel.uiState.charactersListPager == null) {
            characterListViewModel.loadCharactersPager()
        }
    })

    Surface(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        CharactersListContainer(
            characterListViewModel = characterListViewModel,
            onNavigateToDetail = onNavigateToDetail
        )
    }
}

@Composable
private fun CharactersListContainer(
    characterListViewModel: CharacterListViewModel,
    onNavigateToDetail: (characterId: String) -> Unit
) {

    Surface {
        val charactersState =
            characterListViewModel.uiState.charactersListPager?.collectAsLazyPagingItems()
        if (characterListViewModel.uiState.isLoading) {
            ProgressIndicator()
        }
        LazyVerticalGrid(
            columns = androidx.compose.foundation.lazy.grid.GridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
        ) {
            items(charactersState?.itemCount ?: 0) { index ->
                CharacterItem(character = charactersState!![index]!!, onNavigateToDetail)
            }
        }
    }
    ErrorContainer(
        characterListViewModel = characterListViewModel,
        show = characterListViewModel.uiState.errorText != null,
    )
}

@Composable
private fun ErrorContainer(
    show: Boolean,
    characterListViewModel: CharacterListViewModel,
) {
    if (show) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text(
                    text = stringResource(id = R.string.error_dialog_title),
                    style = TextStyle(color = MaterialTheme.colorScheme.error)
                )
            },
            confirmButton = {
                Button(
                    onClick = { characterListViewModel.loadCharactersPager() },
                ) {
                    Text(stringResource(id = R.string.error_dialog_button_retry))
                }
            },
            text = {
                val textId = characterListViewModel.uiState.errorText
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
private fun ProgressIndicator() {
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
private fun CharacterItem(character: Character, onNavigateToDetail: (characterId: String) -> Unit) {
    Surface(
        onClick = {
            onNavigateToDetail(character.id.toString())
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(all = 6.dp),
        color = MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.13f),
        shape = CutCornerShape(corner = CornerSize(10.dp))
    ) {
        Column(

        ) {
            val imageUrl = character.getPreviewImageUrl()
            AsyncImage(
                model = imageUrl,
                contentDescription = character.name,
                modifier = Modifier
                    .width(200.dp)
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = character.name!!, style = TextStyle(
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.padding(all = 8.dp),
                    maxLines = 3,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}