package com.fukuhara.douglas.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
    In case that we would use Dependency Injection or a Service Locator to generate an instance of RetrofitClient, we could:
    - change from `object` to `internal class` --> Hiding visibility of the implementation classes to upper modules;
    - pass some constructor parameters, such as "debuggable flag" for loggingInterceptor, baseUrl value, etc.
 */
object RetrofitClient : RestClient {
    private val client by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .loggingInterceptor()
            .build()
    }

    // Logging Interceptor used only when Network Module is built using DEBUG variant
    private fun Retrofit.Builder.loggingInterceptor() : Retrofit.Builder {
        return if (BuildConfig.DEBUG) {
            val clientConfig = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }).build()
            this.client(clientConfig)
        } else {
            this
        }
    }

    override fun <T> getApi(service: Class<T>): T {
        return client.create(service)
    }
}