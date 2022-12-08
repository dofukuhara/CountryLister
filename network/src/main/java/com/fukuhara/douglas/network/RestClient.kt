package com.fukuhara.douglas.network

interface RestClient {
    fun <T> getApi(service: Class<T>) : T
}