package com.santiihoyos.marvelpocket.ui.feature.characters

import com.santiihoyos.marvel.data.entity.response.CharacterResponse
import com.santiihoyos.marvel.data.entity.response.ImageResponse
import com.santiihoyos.marvelpocket.domain.entity.Character

object CharacterMocks {

    val page1 = listOf(
        Character(
            id = 1,
            name = "Santi",
            description = "HolaCaracola",
            imageUrl = "https://google",
            imageExtension = "png"
        ),
        Character(
            id = 2,
            name = "Santi2",
            description = "HolaCaracola2",
            imageUrl = "https://google2",
            imageExtension = "png"
        ),
        Character(
            id = 3,
            name = "Santi3",
            description = "HolaCaracola3",
            imageUrl = "https://google3",
            imageExtension = "png"
        )
    )

    val characterResponses = listOf(
        CharacterResponse(
            id = 1,
            name = "Santi",
            description = "HolaCaracola",
            thumbnail = ImageResponse(
                path = "https://google",
                extension = "png"
            ),
            modified = null,
            resourceUri = null
        ),
        CharacterResponse(
            id = 2,
            name = "Santi2",
            description = "HolaCaracola2",
            thumbnail = ImageResponse(
                path = "https://google2",
                extension = "png"
            ),
            modified = null,
            resourceUri = null
        ),
        CharacterResponse(
            id = 3,
            name = "Santi3",
            description = "HolaCaracola3",
            thumbnail = ImageResponse(
                path = "https://google3",
                extension = "png"
            ),
            modified = null,
            resourceUri = null
        )
    )
}