package com.santiihoyos.marvel.data.entity.response

import com.google.gson.annotations.SerializedName

open class BaseResponse<E>(

    /**
     * The HTTP status code of the returned result
     */
    @SerializedName("code")
    val httpCode: Int = -1,

    /**
     * A string description of the http call status
     */
    @SerializedName("status")
    val message: String? = "",

    /**
     * A digest value of the content
     */
    val etg: String = "",

    /**
     * The results returned by the call
     */
    val data: PaginatedResponse<E>? = null,

    /**
     * The copyright notice for the returned result
     */
    val copyright: String? = null,

    /**
     * The attribution notice for this result
     */
    val attributionText: String? = null,

    /**
     * An HTML representation of the attribution notice for this result
     */
    @SerializedName("attributionHTML")
    val attributionHtml: String? = null
)