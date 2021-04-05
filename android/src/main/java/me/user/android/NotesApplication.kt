package me.user.android

import android.app.Application
import me.user.common.di.initKoin
import org.koin.android.ext.koin.androidContext

class NotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@NotesApplication)
        }
    }
}