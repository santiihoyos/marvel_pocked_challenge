package com.santiihoyos.marvel.data.entity.response

data class PaginatedResponse<T> (

    /**
     * The requested offset (skipped results) of the call
     */
    val offset: Int = 0,

    /**
     * The requested result limit
     */
    val limit: Int = 0,

    /**
     * The total number of results available
     */
    val total: Int = 0,

    /**
     * The total number of results returned by this call
     */
    val count: Int = 0,

    /**
     * The list of entities returned by the call
     */
    val results: List<T>? = null
)