package com.fukuhara.douglas.common.debug

/*
    Creating this interface to remove direct dependency from 'android' log.
    It will either ease the UnitTest as well as allowing to perform custom logging
 */
interface Logger {
    fun d(tag: String? = null, message: String)
}