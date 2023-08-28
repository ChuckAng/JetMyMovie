package com.example.core.networking

import android.util.Log
import com.example.core.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetroClient {

    private const val IS_AUTHORIZABLE = "isAuthorizable"
    private const val AUTHORIZATION = "Authorization"
    private const val BEARER = "Bearer "
    private const val CULTURE = "culture"
    private const val BASE_URL = "http://localhost:3000/"

    private val okHttpBuilder = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .readTimeout(100, TimeUnit.SECONDS)
        .writeTimeout(100, TimeUnit.SECONDS)

    private val gson: Gson = GsonBuilder().serializeNulls().create()

    val client: Retrofit
        get() {
            Log.d("metag", "endpoint: " + BuildConfig.BASE_ENDPOINT)
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
        }

    private val interceptor: Interceptor
        get() = Interceptor { chain ->
            val request = chain.request()
            chain.proceed(request)
        }

    private val okHttpClient: OkHttpClient
        get() {
            return okHttpBuilder.build()
        }
}
