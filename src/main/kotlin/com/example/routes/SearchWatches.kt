package com.example.routes

import com.example.repository.WatchRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.searchWatches(){

    val watchRepository : WatchRepository by inject()


    get("/uptime/watches/search") {
        val model = call.request.queryParameters["model"]

        val apiResponse = watchRepository.searchWatches(model)
        call.respond(
            message = apiResponse,
            status = HttpStatusCode.OK
        )
    }
}