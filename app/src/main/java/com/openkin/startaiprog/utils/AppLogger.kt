package com.openkin.startaiprog.utils

import android.util.Log

class AppLogger: IAppLogger {

    override fun logEvent(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun logError(tag: String, errorMessage: String) {
        Log.e(tag, errorMessage)
    }
}
