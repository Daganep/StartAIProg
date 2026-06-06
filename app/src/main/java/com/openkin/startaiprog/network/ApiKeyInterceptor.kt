package com.openkin.startaiprog.network

import com.openkin.startaiprog.utils.IAppLogger
import okhttp3.Interceptor
import okhttp3.Response
import com.openkin.startaiprog.utils.CONTENT_TYPE_JSON
import com.openkin.startaiprog.utils.CONTENT_TYPE

class ApiKeyInterceptor(
    private val apiKey: String,
    private val logger: IAppLogger,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        return if (apiKey.isNotEmpty()) {
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("x-goog-api-key", apiKey)
                    .addHeader(CONTENT_TYPE, CONTENT_TYPE_JSON)
                    .addHeader("Api-Revision", "2026-05-20")
                    .build()
            )
        } else {
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader(CONTENT_TYPE, CONTENT_TYPE_JSON)
                    .build()
            )
        }
    }

    private companion object {
        const val LOG_TAG = "MyInterceptor"
        const val REQUEST_METHOD_POST = "POST"
    }
}
