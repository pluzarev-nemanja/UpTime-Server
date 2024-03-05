package com.example.repository

import com.example.models.ApiResponse
import com.example.models.Watch

interface WatchRepository {

    val watches : Map<Int,List<Watch>>

    val page1 : List<Watch>
    val page2 : List<Watch>
    val page3 : List<Watch>
    val page4 : List<Watch>
    val page5 : List<Watch>
    suspend fun getAllWatches(page: Int = 1):ApiResponse
    suspend fun searchWatches(model: String): ApiResponse

}