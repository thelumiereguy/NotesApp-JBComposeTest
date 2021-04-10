package me.user.android

import android.app.Application
import me.user.common.AndroidDependencies
import me.user.common.di.initKoin
import me.user.common.getDbClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class NotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin({
            androidContext(this@NotesApplication)
        }, module {
            single { getDbClient(AndroidDependencies(this@NotesApplication)) }
        })
    }
}