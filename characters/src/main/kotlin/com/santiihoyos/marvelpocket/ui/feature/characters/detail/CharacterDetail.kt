package com.santiihoyos.marvelpocket.ui.feature.characters.detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.santiihoyos.marvelpocket.domain.entity.Character
import com.santiihoyos.marvelpocket.ui.feature.characters.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetail(
    characterDetailViewModel: CharacterDetailViewModel,
    characterId: String,
    onBack: () -> Unit
) {
    LaunchedEffect(key1 = "onStart", block = {
        characterDetailViewModel.getCharacterById(characterId = characterId)
    })

    val uiState = characterDetailViewModel.uiState
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(uiState.character?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        })
    {
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
            PortraitInfoContainer(characterDetailViewModel, characterId)
        } else {
            LandscapeInfoContainer(characterDetailViewModel, characterId)
        }
    }
}

@Composable
private fun PortraitInfoContainer(
    characterDetailViewModel: CharacterDetailViewModel,
    characterId: String,
) {
    Surface {
        if (characterDetailViewModel.uiState.isLoading) {
            ProgressIndicator()
        } else {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                ErrorDialog(
                    show = characterDetailViewModel.uiState.errorText != null,
                    characterDetailViewModel = characterDetailViewModel,
                    characterId = characterId
                )
                characterDetailViewModel.uiState.character?.let { character ->
                    Surface(
                        color = Color.White, modifier = Modifier
                            .fillMaxWidth()
                            .height(420.dp)
                    ) {
                        AsyncImage(
                            model = character.getImageDetailUrl(),
                            contentDescription = character.name,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Id(character = character)
                    Description(character = character)
                }
            }
        }
    }
}

@Composable
fun LandscapeInfoContainer(
    characterDetailViewModel: CharacterDetailViewModel,
    characterId: String,
) {
    Surface {
        if (characterDetailViewModel.uiState.isLoading) {
            ProgressIndicator()
        } else {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                ErrorDialog(
                    show = characterDetailViewModel.uiState.errorText != null,
                    characterDetailViewModel = characterDetailViewModel,
                    characterId = characterId
                )
                characterDetailViewModel.uiState.character?.let { character ->
                    Surface(
                        color = Color.White, modifier = Modifier
                            .fillMaxHeight()
                            .width(400.dp)
                    ) {
                        AsyncImage(
                            model = character.getImageDetailUrl(),
                            contentDescription = character.name,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(70.dp))
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Id(character = character)
                        Description(character = character)
                    }
                }
            }
        }
    }
}

@Composable
private fun Id(character: Character) {
    Text(
        "ID: ${character.id}",
        style = TextStyle(
            fontSize = 20.sp
        ),
        color = LocalContentColor.current,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    )
}

@Composable
private fun Description(character: Character) {
    Text(
        if (!character.description.isNullOrEmpty())
            character.description
        else
            stringResource(id = R.string.theres_not_description),
        style = TextStyle(
            fontSize = 16.sp
        ),
        color = LocalContentColor.current,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    )
}

@Composable
private fun ProgressIndicator() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}

@Composable
private fun ErrorDialog(
    show: Boolean,
    characterDetailViewModel: CharacterDetailViewModel,
    characterId: String,
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
                    onClick = {
                        characterDetailViewModel.getCharacterById(characterId)
                    },
                ) {
                    Text(stringResource(id = R.string.error_dialog_button_retry))
                }
            },
            text = {
                val textId = characterDetailViewModel.uiState.errorText
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