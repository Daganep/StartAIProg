package com.openkin.startaiprog.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.openkin.startaiprog.network.model.InteractionResponse
import com.openkin.startaiprog.network.model.RequestPromt
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

interface GeminiApi {

    @POST("v1beta/interactions")
    suspend fun getKekPek(@Body body: RequestPromt) : Result<InteractionResponse>
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

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(Json.asConverterFactory(CONTENT_TYPE_JSON.toMediaType()))
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(okHttpClient.build())
        .build()
        .create(GeminiApi::class.java)
}
