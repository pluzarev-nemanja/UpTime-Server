package com.example

import com.example.models.ApiResponse
import com.example.plugins.*
import com.example.repository.NEXT_PAGE_KEY
import com.example.repository.PREVIOUS_PAGE_KEY
import com.example.repository.WatchRepositoryImpl
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.*

class ApplicationTest {
    @Test
    fun `access root endpoint,assert correct information`() = testApplication {
        val response = client.get("/")
        assertEquals(
            HttpStatusCode.OK,
            response.status
        )
        assertEquals(expected = "Welcome to UpTime Api!", actual = response.bodyAsText())
    }

    @Test
    fun `access all watches endpoint,query non existing page number,assert error`() = testApplication {
        val response = client.get("/uptime/watches?page=6")
        assertEquals(expected = HttpStatusCode.NotFound, response.status)
        val expected = ApiResponse(
            success = false,
            message = "Watches not found."
        )
        val actual = Json.decodeFromString<ApiResponse>(response.body())
        assertEquals(
            expected = expected, actual = actual
        )
    }

    @Test
    fun `access all watches endpoint,queary invalid page number, assert error`() = testApplication {
        val response = client.get("/uptime/watches?page=invalid")
        assertEquals(
            expected = HttpStatusCode.BadRequest, actual = response.status
        )
        val expected = ApiResponse(
            success = false,
            message = "Only numbers allowed.",
        )
        val actual = Json.decodeFromString<ApiResponse>(response.body())
        assertEquals(
            expected = expected, actual = actual
        )
    }

    @Test
    fun `access search watches endpoint,queary watch model, assert single watch result`() = testApplication {
        val response = client.get("/uptime/watches/search?model=KHAKI FIELD MECHANICAL 38MM")
        assertEquals(
            expected = HttpStatusCode.OK, actual = response.status
        )
        val actual = Json.decodeFromString<ApiResponse>(response.body()).watches.size
        assertEquals(
            expected = 1, actual = actual
        )
    }


    @Test
    fun `access search watches endpoint,queary watch model, assert multiple watch result`() = testApplication {
        val response = client.get("/uptime/watches/search?model=L")
        assertEquals(
            expected = HttpStatusCode.OK, actual = response.status
        )
        val actual = Json.decodeFromString<ApiResponse>(response.body()).watches.size
        assertEquals(
            expected = 7, actual = actual
        )
    }

    @Test
    fun `access search watches endpoint,queary an empty watch model, assert empty list as a result`() =
        testApplication {
            val response = client.get("/uptime/watches/search?model=")
            assertEquals(
                expected = HttpStatusCode.OK, actual = response.status
            )
            val actual = Json.decodeFromString<ApiResponse>(response.body()).watches
            assertEquals(
                expected = emptyList(), actual = actual
            )
        }

    @Test
    fun `access search watches endpoint,queary non existing watch, assert empty list as a result`() = testApplication {

        val response = client.get("/uptime/watches/search?model=unknown")
        assertEquals(
            expected = HttpStatusCode.OK, actual = response.status
        )
        val actual = Json.decodeFromString<ApiResponse>(response.body()).watches
        assertEquals(
            expected = emptyList(), actual = actual
        )

    }

    @Test
    fun `access non existing endpoint, assert not found`() = testApplication {

        val response = client.get("/unknown")
        assertEquals(
            expected = HttpStatusCode.NotFound, actual = response.status
        )
        assertEquals(
            expected = "", actual = response.body()
        )

    }

    @Test
    fun `access all watches endpoint,query all pages,assert correct information`() = testApplication {
        val watchRepository = WatchRepositoryImpl()
        val pages = 1..5
        val watches = listOf(
            watchRepository.page1,
            watchRepository.page2,
            watchRepository.page3,
            watchRepository.page4,
            watchRepository.page5,
        )
        pages.forEach{page->
            val response = client.get("/uptime/watches?page=$page")
            assertEquals(
                expected = HttpStatusCode.OK, actual = response.status
            )
            val actual = Json.decodeFromString<ApiResponse>(response.body())
            val expected = ApiResponse(
                success = true,
                message = "ok",
                prevPage = calculatePage(page = page)["prevPage"],
                nextPage = calculatePage(page = page)["nextPage"],
                watches = watches[page - 1],
            )
            assertEquals(
                expected = expected, actual = actual
            )
        }
    }

    private fun calculatePage(page: Int) =
        mapOf(
            PREVIOUS_PAGE_KEY to if (page in 2..5) page.minus(1) else null,
            NEXT_PAGE_KEY to if (page in 1..4) page.plus(1) else null
        )
}
