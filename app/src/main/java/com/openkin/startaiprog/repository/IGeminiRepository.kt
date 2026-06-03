package com.openkin.startaiprog.repository

import kotlinx.coroutines.flow.Flow

interface IGeminiRepository {

    suspend fun askGemini(question: String) : Flow<String>
}
