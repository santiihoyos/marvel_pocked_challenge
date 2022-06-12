package com.santiihoyos.marvelpocket.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.santiihoyos.marvelpocket.ui.feature.characters.CharacterList
import com.santiihoyos.marvelpocket.ui.feature.characters.CharacterListViewModel
import com.santiihoyos.marvelpocket.ui.feature.characters.CharacterListViewModelState
import com.santiihoyos.marvelpocket.ui.theme.MarvelPocketTheme
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val charactersListViewModel: CharacterListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            charactersListViewModel.viewModelState.emit(
                CharacterListViewModelState(firstLoading = true)
            )
        }
        setContent {
            MarvelPocketTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CharactersLists(charactersListViewModel)
                }
            }
        }
    }
}

/**
 * Shows Characters list flow views as main
 * depends on [CharacterListViewModel] and []
 * //TODO: end doc..
 */
@Composable
fun CharactersLists(characterListViewModel: CharacterListViewModel) {
    val navHostController = rememberNavController()
    NavHost(navController = navHostController, startDestination = "list") {
        composable("list") {
            CharacterList(characterListViewModel = characterListViewModel)
        }
        /* composable("detail" + "/{id}") {
            CharacterDetailView(
                viewModel = viewModel(),
                characterId = it.arguments?.get("id") as? String ?: "",
                popBack = { navHostController.popBackStack() }
            )
        }
         */
    }
}