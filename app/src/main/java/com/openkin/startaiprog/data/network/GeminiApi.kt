package com.openkin.startaiprog.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.openkin.startaiprog.data.network.model.generatetext.TextGenerateRequest
import com.openkin.startaiprog.data.network.model.generatetext.TextGenerateResponse
import com.openkin.startaiprog.data.network.model.simplerequest.InteractionResponse
import com.openkin.startaiprog.data.network.model.simplerequest.RequestPromt
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import com.openkin.startaiprog.utils.CONTENT_TYPE_JSON
import com.openkin.startaiprog.utils.IAppLogger
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface GeminiApi {

    @POST("v1beta/interactions")
    suspend fun simpleRequest(@Body body: RequestPromt) : Result<InteractionResponse>

    @POST("/v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateText(@Body body: TextGenerateRequest) : Result<TextGenerateResponse>
}


fun GeminiApi(
    baseUrl: String,
    apiKey: String,
    logger: IAppLogger,
) : GeminiApi {

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(
            ApiKeyInterceptor(
                apiKey = apiKey,
                logger = logger,
            )
        )
        .connectTimeout(30, TimeUnit.SECONDS) // Время на установку соединения
        .readTimeout(120, TimeUnit.SECONDS)   // Время ожидания ответа от API (КРИТИЧНО)
        .writeTimeout(30, TimeUnit.SECONDS)   // Время на отправку запроса

    val jsonFactory = Json { ignoreUnknownKeys = true }
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonFactory.asConverterFactory(CONTENT_TYPE_JSON.toMediaType()))
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(okHttpClient.build())
        .build()
        .create(GeminiApi::class.java)
}
