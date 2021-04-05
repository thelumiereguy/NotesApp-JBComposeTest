package me.user.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import me.user.common.App
import me.user.common.feature.notes.presentation.viewmodel.NotesViewModel
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {

    val viewmodel = get<NotesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(viewmodel, {

            })
        }
    }
}