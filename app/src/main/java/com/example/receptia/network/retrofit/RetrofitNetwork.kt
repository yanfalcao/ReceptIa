package com.example.receptia.network.retrofit

import com.example.receptia.BuildConfig
import com.example.receptia.network.GptApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitNetwork {
    private val okHttpClientGpt = OkHttpClient.Builder().apply {
        readTimeout(30, TimeUnit.SECONDS)
        connectTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        addInterceptor(
            Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("Content-Type", "application/json")
                builder.header("Authorization", "Bearer ${BuildConfig.GPT_API_KEY}")
                return@Interceptor chain.proceed(builder.build())
            },
        )
    }.build()

    fun getRetrofitInstance(path: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(path)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientGpt)
            .build()
    }

    private val GTP_BASE_URL = "https://api.openai.com/"

    @Provides
    @Singleton
    fun gptService(): GptApiService =
        getRetrofitInstance(GTP_BASE_URL).create(GptApiService::class.java)
}
