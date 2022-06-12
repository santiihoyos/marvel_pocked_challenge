package com.santiihoyos.marvelpocket.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.santiihoyos.marvelpocket.ui.feature.characters.detail.CharacterDetail
import com.santiihoyos.marvelpocket.ui.feature.characters.list.CharacterListViewModel
import com.santiihoyos.marvelpocket.ui.feature.characters.list.CharactersList
import com.santiihoyos.marvelpocket.ui.theme.MarvelPocketTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelPocketTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CharactersLists(koinViewModel())
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
fun CharactersLists(
    characterListViewModel: CharacterListViewModel
) {
    val navHostController = rememberNavController()
    NavHost(navController = navHostController, startDestination = "list") {
        composable("list") {
            CharactersList(
                characterListViewModel = characterListViewModel,
                onNavigateToDetail = { id -> navHostController.navigate("detail/${id}") }
            )
        }
        composable("detail/{id}") {
            CharacterDetail(
                characterId = it.arguments?.get("id") as? String ?: "",
                onBack = { navHostController.popBackStack() },
                characterDetailViewModel = koinViewModel()
            )
        }
    }
}