package me.user.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import me.user.common.NotesApp
import org.koin.android.ext.android.getKoin

class MainActivity : AppCompatActivity() {

        @ExperimentalFoundationApi
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotesApp(getKoin())
        }
    }
}