package com.santiihoyos.marvelpocket.ui.feature.home

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
import com.santiihoyos.marvelpocket.ui.Screen
import com.santiihoyos.marvelpocket.ui.feature.characters.detail.CharacterDetail
import com.santiihoyos.marvelpocket.ui.feature.characters.list.CharacterListViewModel
import com.santiihoyos.marvelpocket.ui.feature.characters.list.CharactersList
import com.santiihoyos.marvelpocket.ui.theme.MarvelPocketTheme
import org.koin.androidx.compose.koinViewModel

/**
 * Entry point of app.
 */
class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeContainer()
        }
    }
}

/**
 * Shows Characters list flow views as main
 * depends on [CharacterListViewModel] and []
 * //TODO: end doc..
 */
@Composable
private fun HomeContainer() {
    MarvelPocketTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeNavHost()
        }
    }
}

/**
 * Creates and setup app navhost
 */
@Composable
private fun HomeNavHost() {
    val navHostController = rememberNavController()
    NavHost(navController = navHostController, startDestination = Screen.List.route) {
        composable(Screen.List.route) {
            CharactersList(
                characterListViewModel = koinViewModel(),
                onNavigateToDetail = { id ->
                    navHostController.navigate(Screen.Detail.withId(id))
                }
            )
        }
        composable(Screen.Detail.route) {
            CharacterDetail(
                characterId = it.arguments?.get("id") as? String ?: "",
                onBack = { navHostController.popBackStack() },
                characterDetailViewModel = koinViewModel()
            )
        }
    }
}