package com.santiihoyos.marvel.data.datasource

import com.santiihoyos.marvel.data.datasource.impl.RestMarvelDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.File
import java.util.*

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RestCharacterDataSourceImplTest {

    /**
     * DISCLAIMER: api calls wont test because it's should be tested by backend team.
     */

    @Test
    fun `get test retrofit instance returns ok`() {
        val localProperties = Properties()
        localProperties.load(File("resources/local.properties").inputStream())
        runTest {
            val publicKey = localProperties["dev.api.publicKey"].toString()
            val privKey = localProperties["dev.api.privateKey"].toString()
            val instance = RestMarvelDataSource.getInstance(
                "",
                "https://gateway.marvel.com",
                ""
            )
            val resquestOk = instance.getCharacters("name", 1, limit = 3)
            assert(resquestOk.httpCode == 200 && resquestOk.data?.results?.count() == 3)
        }
    }

    //Test for calls..

    //Maybe extract interceptors to test...
}