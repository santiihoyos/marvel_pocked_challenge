package com.santiihoyos.marvel.data.datasource.impl

import com.santiihoyos.marvel.data.datasource.CloudCharacterDataSource
import com.santiihoyos.marvel.data.entity.DataError
import com.santiihoyos.marvel.data.entity.response.BaseResponse
import com.santiihoyos.marvel.data.entity.response.CharacterResponse
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.time.Duration
import java.util.*
import kotlin.Exception

/**
 * Retrofit interface and default implementation for
 * [CloudCharacterDataSource]
 */
internal interface RestMarvelDataSource : CloudCharacterDataSource {

    /**
     * Endpoint definition to get a list of [CharacterResponse]
     */
    @GET("/v1/public/characters")
    @Headers(HEADER_ACCEPT_JSON)
    override suspend fun getCharacters(
        @Query("orderBy") orderBy: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): BaseResponse<CharacterResponse>

    @GET("/v1/public/characters/{id}")
    @Headers(HEADER_ACCEPT_JSON)
    override suspend fun getCharacterById(
        @Path("id") id: String
    ): BaseResponse<CharacterResponse>

    //Add more Marvel api endpoints here.

    companion object {

        private const val HEADER_ACCEPT_JSON = "Accept: application/json"
        private const val API_KEY_PARAM_NAME = "apikey"

        private lateinit var instance: CloudCharacterDataSource

        /**
         * Build if it is necessary create RetrofitRestRepository or existing instance
         *
         * @return RetrofitRestRepository implementation instance
         */
        fun getInstance(
            apiKey: String,
            baseUrl: String,
            privateKey: String
        ): CloudCharacterDataSource {

            if (!Companion::instance.isInitialized) {
                instance = Retrofit.Builder()
                    .client(getOkHttpClient(apiKey, privateKey))
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RestMarvelDataSource::class.java)
            }

            return instance
        }

        /**
         * Builds http client for intercept all calls and add required query params.
         * also add some interceptors to log request and response
         *
         * @return OkHttpClient - http client with interceptors
         */
        private fun getOkHttpClient(
            apiKey: String,
            privateKey: String
        ): OkHttpClient {

            val okHttpBuilder = OkHttpClient.Builder()
            okHttpBuilder.callTimeout(Duration.ofSeconds(2))
            okHttpBuilder.addInterceptor(getAuthInterceptor(apiKey, privateKey))
            okHttpBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            return okHttpBuilder.build()
        }

        /**
         * Creates Interceptor to provide required params by api
         * apikey, ts and hash query params
         */
        @Throws(DataError::class)
        private fun getAuthInterceptor(
            apiKey: String,
            privateKey: String
        ): Interceptor = Interceptor { chain ->
            try {
                val ts = Date().time.toString()
                val hash = md5(ts + privateKey + apiKey)
                val request = chain.request().newBuilder()
                val url = chain.request().url.newBuilder()
                url.addQueryParameter(API_KEY_PARAM_NAME, apiKey)
                url.addQueryParameter("ts", ts)
                url.addQueryParameter("hash", hash)
                request.url(url.build())
                chain.proceed(request.build())
            } catch (ex: Exception) {
                Response.Builder()
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .code(999)
                    .message(ex.message.toString())
                    .body(
                        "{ code: ${999}, status: \"IOException\"}"
                            .toResponseBody("application/json".toMediaType())
                    ).build()
            }
        }


        /**
         * Converts to MD5 timeStamp + private key + api key
         * string.
         *
         * @param stringToHash to calculate MD5
         * @return String of passed string
         */
        private fun md5(stringToHash: String): String {
            val md5 = "MD5"

            try {
                val digest = MessageDigest.getInstance(md5)
                digest.update(stringToHash.toByteArray())
                val messageDigest = digest.digest()

                val hexString = StringBuilder()
                for (aMessageDigest in messageDigest) {
                    var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                    while (h.length < 2) {
                        h = "0$h"
                    }
                    hexString.append(h)
                }
                return hexString.toString()

            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }

            return ""
        }
    }
}