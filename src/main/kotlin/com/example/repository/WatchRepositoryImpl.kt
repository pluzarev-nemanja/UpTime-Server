package com.example.repository

import com.example.models.ApiResponse
import com.example.models.Watch

class WatchRepositoryImpl : WatchRepository {
    override val watches: Map<Int, List<Watch>>
        get() = TODO("Not yet implemented")
    override val page1: List<Watch>
        get() = TODO("Not yet implemented")
    override val page2: List<Watch>
        get() = TODO("Not yet implemented")
    override val page3: List<Watch>
        get() = TODO("Not yet implemented")
    override val page4: List<Watch>
        get() = TODO("Not yet implemented")
    override val page5: List<Watch>
        get() = TODO("Not yet implemented")

    override suspend fun getAllWatches(page: Int): ApiResponse {
        TODO("Not yet implemented")
    }

    override suspend fun searchWatches(model: String): ApiResponse {
        TODO("Not yet implemented")
    }
}