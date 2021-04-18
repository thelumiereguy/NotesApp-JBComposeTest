/*
 * Created by Piyush Pradeepkumar on 18/04/21, 3:19 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package me.user.common.notes.presentation.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ITextFieldStateProvider {
    val titleTextState: StateFlow<String>
    val contentTextState: StateFlow<String>

    fun onTitleChanged(title: String)
    fun onContentChanged(content: String)
}


class TextFieldStateProvider : ITextFieldStateProvider {

    private val _titleTextState = MutableStateFlow("")
    override val titleTextState: StateFlow<String> = _titleTextState

    private val _contentTextState = MutableStateFlow("")
    override val contentTextState: StateFlow<String> = _contentTextState

    override fun onTitleChanged(title: String) {
        _titleTextState.value = title
    }

    override fun onContentChanged(content: String) {
        _contentTextState.value = content
    }


}