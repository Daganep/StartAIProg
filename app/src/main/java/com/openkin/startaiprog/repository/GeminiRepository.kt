package com.openkin.startaiprog.repository

import android.util.Log
import com.openkin.startaiprog.network.GeminiApi
import com.openkin.startaiprog.network.model.RequestPromt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GeminiRepository(
    private val geminiApi: GeminiApi,
) : IGeminiRepository {

    override suspend fun askGemini(question: String) : Flow<String> {
        val response = geminiApi.getKekPek(
            RequestPromt(model = "gemini-3-flash-preview", input = question)
        )
        val result = if (response.isSuccess) {
            response.getOrNull()
                ?.steps
                ?.firstOrNull { it.type == "text" }
                ?.content
                ?.get(0)
                ?.text
                ?: "empty"
        } else {
            Log.e("MyFilter", "Ошибка из-за: ${response.exceptionOrNull()?.stackTraceToString()}")
            "ERROR REQUEST блин"
        }
        return flowOf(result)
    }
}
