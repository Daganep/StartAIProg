package com.openkin.startaiprog.screen.mainscreen

import com.openkin.startaiprog.utils.EMPTY_STRING

data class MainViewState(
    val promt: String = EMPTY_STRING,
    val response: String = EMPTY_STRING,
    val showPromt: Boolean = true,
    val isLoading: Boolean = false,
)
