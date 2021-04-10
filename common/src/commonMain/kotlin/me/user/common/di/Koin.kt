package me.user.common.di

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.serialization.json.Json
import me.user.common.feature.notes.data.NotesRepository
import me.user.common.feature.notes.data.network.NotesAPI
import me.user.common.feature.notes.presentation.viewmodel.NotesViewModel
import me.user.common.getStompClient
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule())
    }

fun commonModule() = module {
    single { Json { isLenient = true; ignoreUnknownKeys = true } }
    single { createHttpClient(get(), true) }
    single { NotesRepository(get(), getStompClient(), get()) }
    single { NotesViewModel(get()) }
    single { NotesAPI(get()) }
}

fun createHttpClient(json: Json, enableNetworkLogs: Boolean) = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(json)
    }
    if (enableNetworkLogs) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }
}