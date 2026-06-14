package com.openkin.startaiprog.presentation.screen.mainscreen

import com.openkin.startaiprog.domain.model.ChatUI

data class MainViewState(
    val chatsList: List<ChatUI> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)
