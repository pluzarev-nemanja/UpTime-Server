package com.example.di

import com.example.repository.WatchRepository
import com.example.repository.WatchRepositoryImpl
import org.koin.dsl.module

val koinModule = module {
    single<WatchRepository> {
        WatchRepositoryImpl()
    }
}