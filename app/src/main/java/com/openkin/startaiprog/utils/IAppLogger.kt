package com.openkin.startaiprog.utils


interface IAppLogger {

    fun logEvent(tag: String, message: String)

    fun logError(tag: String, errorMessage: String)
}
