package com.santiihoyos.marvelpocket.di

import com.santiihoyos.marvelpocket.domain.usecase.GetCharacterByIdUseCase
import com.santiihoyos.marvelpocket.domain.usecase.GetPaginatedCharactersUseCase
import com.santiihoyos.marvelpocket.domain.usecase.impl.GetCharacterByIdImpl
import com.santiihoyos.marvelpocket.domain.usecase.impl.GetPaginatedCharactersImpl
import com.santiihoyos.marvelpocket.ui.feature.characters.detail.CharacterDetailViewModel
import com.santiihoyos.marvelpocket.ui.feature.characters.list.CharacterListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val charactersModule = module {

    single<GetPaginatedCharactersUseCase> {
        GetPaginatedCharactersImpl(
            characterRepository = get()
        )
    }

    single<GetCharacterByIdUseCase> {
        GetCharacterByIdImpl(
            characterRepository = get()
        )
    }

    //Resolves CharacterListViewModel
    viewModel {
        CharacterListViewModel(get())
    }

    viewModel {
        CharacterDetailViewModel(get())
    }
}