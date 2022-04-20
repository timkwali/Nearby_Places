package com.adyen.android.assignment.common.data.api

import com.adyen.android.assignment.BuildConfig
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlacesApiTest {

    private val mockResponse = "{\n" +
            "    \"results\": [\n" +
            "        {\n" +
            "            \"fsq_id\": \"4a270700f964a520a58c1fe3\",\n" +
            "            \"categories\": [\n" +
            "                {\n" +
            "                    \"id\": 10028,\n" +
            "                    \"name\": \"Art Museum\",\n" +
            "                    \"icon\": {\n" +
            "                        \"prefix\": \"https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_\",\n" +
            "                        \"suffix\": \".png\"\n" +
            "                    }\n" +
            "                }\n" +
            "            ],\n" +
            "            \"chains\": [],\n" +
            "            \"distance\": 38,\n" +
            "            \"geocodes\": {\n" +
            "                \"main\": {\n" +
            "                    \"latitude\": 52.364049,\n" +
            "                    \"longitude\": 4.893104\n" +
            "                },\n" +
            "                \"roof\": {\n" +
            "                    \"latitude\": 52.364049,\n" +
            "                    \"longitude\": 4.893104\n" +
            "                }\n" +
            "            },\n" +
            "            \"location\": {\n" +
            "                \"address\": \"Keizersgracht 609\",\n" +
            "                \"country\": \"NL\",\n" +
            "                \"cross_street\": \"Vijzelstraat\",\n" +
            "                \"formatted_address\": \"Keizersgracht 609 (Vijzelstraat), 1017 DS Amsterdam\",\n" +
            "                \"locality\": \"Amsterdam\",\n" +
            "                \"neighborhood\": [\n" +
            "                    \"Grachtengordel-Zuid\"\n" +
            "                ],\n" +
            "                \"postcode\": \"1017 DS\",\n" +
            "                \"region\": \"North Holland\"\n" +
            "            },\n" +
            "            \"name\": \"Foam Fotografiemuseum Amsterdam (Foam)\",\n" +
            "            \"related_places\": {},\n" +
            "            \"timezone\": \"Europe/Amsterdam\"\n" +
            "        }\n" +
            "    ]\n" +
            "}".trimIndent()

    private lateinit var mockWebServer: MockWebServer

    private fun getUserApiService(): PlacesApi {
        val baseUrl = mockWebServer.url(BuildConfig.FOURSQUARE_BASE_URL)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(PlacesApi::class.java)
    }

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.enqueue(MockResponse().setBody(mockResponse))
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun canResponseObjectBeParsed() = runTest {

        val service = getUserApiService()
        val query = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(52.36391, 4.8939)
            .setResponseLimit("1")
            .build()

        val response = service.getVenueRecommendations(query)
        Assert.assertNotNull(response)
        response.results?.isNotEmpty()?.let { Assert.assertTrue(it) }
    }
}