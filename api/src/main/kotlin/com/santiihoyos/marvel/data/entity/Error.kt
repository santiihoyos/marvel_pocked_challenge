package com.santiihoyos.marvel.data.entity

/**
 * Data layer errors
 */
sealed class DataError: Exception() {

    object ConnectionError : DataError()

    object NotFoundError : DataError()

    //Other managed Data layer errors here...

    object UnknownError : DataError()
}