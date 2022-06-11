package com.santiihoyos.marvel.di

import com.santiihoyos.marvel.data.datasource.LocalCharacterDataSource
import com.santiihoyos.marvel.data.datasource.impl.RestMarvelDataSource
import com.santiihoyos.marvel.data.datasource.impl.RoomCharacterDataSource
import com.santiihoyos.marvel.data.repository.CharacterRepository
import com.santiihoyos.marvel.data.repository.impl.CharacterRepositoryImpl
import org.koin.dsl.module

val apiModule = module {

    single<CharacterRepository> {
        CharacterRepositoryImpl(get(), get())
    }

    single {
        RestMarvelDataSource.getInstance(
            baseUrl = "https://gateway.marvel.com",
            apiKey = "ee1e6532fc234738b7a212da7c0c98e8",
            privateKey = "55c6c85b915ad56af9ac5007aef90588aa1a2b70"
        )
    }

    single<LocalCharacterDataSource> {
        RoomCharacterDataSource()
    }
}