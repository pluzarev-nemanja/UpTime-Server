package com.example.routes

import com.example.models.ApiResponse
import com.example.repository.WatchRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.getAllWatches() {

    val watchRepository: WatchRepository by inject()

    get("/uptime/watches") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            require(page in 1..5)

            val apiResponse = watchRepository.getAllWatches(page = page)

            call.respond(
                message = apiResponse,
                status = HttpStatusCode.OK

            )
        } catch (e: NumberFormatException) {
            call.respond(
                message = ApiResponse(
                    success = false,
                    message = "Only numbers allowed."
                ),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: IllegalArgumentException) {

            call.respond(
                message = ApiResponse(
                    success = false,
                    message = "Watches not found."
                ),
                status = HttpStatusCode.NotFound
            )
        }

    }
}