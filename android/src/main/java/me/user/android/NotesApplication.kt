package me.user.android

import android.app.Application
import me.user.common.di.AndroidDependencies
import me.user.common.di.createDbClient
import me.user.common.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class NotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@NotesApplication)
            modules(module {
                single { createDbClient(AndroidDependencies(this@NotesApplication)) }
            })
        }
    }
}