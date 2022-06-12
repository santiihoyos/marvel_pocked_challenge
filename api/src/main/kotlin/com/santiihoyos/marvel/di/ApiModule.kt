package com.santiihoyos.marvel.di

import com.santiihoyos.marvel.data.datasource.LocalCharacterDataSource
import com.santiihoyos.marvel.data.datasource.impl.LocalCharacterDataSourceImpl
import com.santiihoyos.marvel.data.datasource.impl.RestMarvelDataSource
import com.santiihoyos.marvel.data.repository.CharacterRepository
import com.santiihoyos.marvel.data.repository.impl.CharacterRepositoryImpl
import org.koin.dsl.module

fun getApiModule(
    apiBaseUrl: String,
    publicKey: String,
    privateKey: String,
): org.koin.core.module.Module {
    return module {
        single<CharacterRepository> {
            CharacterRepositoryImpl(get(), get())
        }

        single {
            print("")
            RestMarvelDataSource.getInstance(
                baseUrl = apiBaseUrl,
                apiKey = publicKey,
                privateKey = privateKey,
            )
        }

        single<LocalCharacterDataSource> {
            LocalCharacterDataSourceImpl()
        }
    }
}