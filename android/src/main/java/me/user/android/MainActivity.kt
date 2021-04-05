package me.user.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import me.user.common.NotesApp
import me.user.common.feature.notes.presentation.viewmodel.NotesViewModel

class MainActivity : AppCompatActivity() {

    val viewmodel = get<NotesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotesApp(viewmodel, {

            })
        }
    }
}