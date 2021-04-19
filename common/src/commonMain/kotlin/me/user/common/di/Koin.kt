package me.user.common.di

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.serialization.json.Json
import me.user.common.notes.data.NotesRepository
import me.user.common.notes.data.mapper.NoteMapper
import me.user.common.notes.data.network.NotesAPI
import me.user.common.notes.presentation.viewmodel.create_note.CreateNotesViewModel
import me.user.common.notes.presentation.viewmodel.notesfeed.NotesViewModel
import me.user.common.notes.presentation.viewmodel.update_note.UndoRedoHandler
import me.user.common.notes.presentation.viewmodel.update_note.UpdateNoteViewModel
import me.user.common.platformModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(), platformModule())
    }

fun commonModule() = module {
    factory { Json { isLenient = true; ignoreUnknownKeys = true } }
    factory { createHttpClient(get(), true) }
    factory { NotesRepository(get(), get(), get(), get()) }
    factory { NotesViewModel(get()) }
    factory { CreateNotesViewModel(get()) }
    factory { UpdateNoteViewModel(get(), get()) }
    factory { NotesAPI(get()) }
    factory { NoteMapper() }
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