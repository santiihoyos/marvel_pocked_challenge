package com.santiihoyos.marvel.data.entity.response

import com.google.gson.annotations.SerializedName

data class CharacterResponse(

    /**
     * The unique ID of the character resource
     */
    val id: Int?,

    /**
     * The name of the character
     */
    val name: String?,

    /**
     * A short bio or description of the character
     */
    val description: String?,

    /**
     * The date the resource was most recently modified
     */
    val modified: String?,

    /**
     * The canonical URL identifier for this resource.
     */
    @SerializedName("resourceURI")
    val resourceUri: String?,

    /**
     * The representative image for this character.
     */
    val thumbnail: ImageResponse?,

    //NOTE: below props are unnecessary for only list and detail implementations at this moment
    //so something response entities are not mapped yet.

    /**
     * A set of public web site URLs for the resource
     */
    //val urls: List<String>?,

    /**
     * A resource list containing comics which feature this character
     * //TODO: map Comic entity response if we need
     */
    //val comics: Any?,

    /**
     * A resource list of stories in which this character appears
     * //TODO: map Story entity response if we need
     */
    //val stories: Any?,

    /**
     * A resource list of events in which this character appears
     * //TODO: map Event entity response if we need
     */
    //val events: Any?,

    /**
     * A resource list of series in which this character appears
     * //TODO: map Serie entity response if we need
     */
    //val series: Any?
)