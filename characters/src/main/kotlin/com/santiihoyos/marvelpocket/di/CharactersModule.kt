package com.santiihoyos.marvelpocket.di

import com.santiihoyos.marvelpocket.domain.usecase.GetPaginatedCharacters
import com.santiihoyos.marvelpocket.domain.usecase.impl.GetPaginatedCharactersImpl
import com.santiihoyos.marvelpocket.ui.feature.characters.CharacterListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val charactersModule = module {

    single<GetPaginatedCharacters> {
        GetPaginatedCharactersImpl(
            characterRepository = get()
        )
    }

    //Resolves CharacterListViewModel
    viewModel {
        CharacterListViewModel(get())
    }
}