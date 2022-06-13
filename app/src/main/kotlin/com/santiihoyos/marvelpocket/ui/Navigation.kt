package com.santiihoyos.marvelpocket.ui

sealed class Screen(
    val route: String
) {

    object List: Screen("list")

    object Detail: Screen("detail/{id}") {
        fun withId(id: String) = "detail/$id"
    }
}