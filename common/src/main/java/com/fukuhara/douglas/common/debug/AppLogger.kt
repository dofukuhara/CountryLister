package com.fukuhara.douglas.common.debug

import android.util.Log

/*
    In here we are only using android Log, but we can make it more robust, by implementing Timber.
    Also, making it more/less verbose depending on build variant, as well as send/block log requests to crashlytics, newrelic, etc.
 */

class AppLogger : Logger {
    override fun d(tag: String?, message: String) {
        Log.d(tag, message)
    }
}