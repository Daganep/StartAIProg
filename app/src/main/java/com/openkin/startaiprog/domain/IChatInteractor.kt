package com.openkin.startaiprog.domain

import com.openkin.startaiprog.domain.model.ChatUI

interface IChatInteractor {

    suspend fun loadChat(): ChatUI
}
