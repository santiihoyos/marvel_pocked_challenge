package com.santiihoyos.marvelpocket.domain.entity.error


sealed class CharacterError : Exception() {

    object NotFoundCharacterError : CharacterError()

    object UnknownCharacterError : CharacterError()
}