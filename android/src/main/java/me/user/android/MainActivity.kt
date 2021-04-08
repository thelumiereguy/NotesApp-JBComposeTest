package me.user.android

import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import me.user.common.NotesApp
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent
import org.koin.android.ext.android.getKoin

class MainActivity : PreComposeActivity() {

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesApp(getKoin())
        }
    }
}