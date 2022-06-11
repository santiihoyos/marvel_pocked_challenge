package com.santiihoyos.marvelpocket.ui.feature.characters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterList(characterListViewModel: CharacterListViewModel) {
    Surface(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val lazyItems = characterListViewModel.charactersFlow.collectAsLazyPagingItems()
        LazyColumn{
            items(lazyItems){ _character ->
                Card(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth().height(100.dp)
                        .padding(all = 6.dp)
                ) {
                    Row(
                    ) {
                        val imageUrl =  _character!!.getPreviewImageUrl();
                        Surface(shape = CircleShape, modifier = Modifier.align(Alignment.CenterVertically)) {
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = _character.name,
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(80.dp),
                                contentScale = ContentScale.Inside
                            )
                        }
                        Text(text = _character!!.name!!)
                    }
                }
            }
        }
    }
}