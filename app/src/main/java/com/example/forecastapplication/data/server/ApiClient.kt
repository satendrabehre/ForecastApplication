package com.example.forecastapplication.data.server

import com.example.forecastapplication.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object ApiClient {
    private var retrofit: Retrofit? = null
    private const val REQUEST_TIMEOUT = 60
    private var okHttpClient: OkHttpClient? = null

    /**
     * @return retrofit instance [Retrofit]
     */
    val client: Retrofit?
        get() {
            if (okHttpClient == null) initOkHttp()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(okHttpClient) //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

    /**
     * init instance of [OkHttpClient]
     */
    private fun initOkHttp() {
        val httpClient: OkHttpClient.Builder = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        //httpClient.addInterceptor(interceptor);
        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                val request: Request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
        okHttpClient = httpClient.build()
    }
}