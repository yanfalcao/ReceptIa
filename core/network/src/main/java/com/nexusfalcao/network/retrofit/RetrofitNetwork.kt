package com.nexusfalcao.network.retrofit

import com.google.gson.GsonBuilder
import com.nexusfalcao.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitNetwork {
    val intercepter = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClientGpt = OkHttpClient.Builder().apply {
        readTimeout(30, TimeUnit.SECONDS)
        connectTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        addInterceptor(intercepter)
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
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(path)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClientGpt)
            .build()
    }

    private val GTP_BASE_URL = "https://api.openai.com/"

    @Provides
    @Singleton
    fun gptService(): ChatgptNetworkApi =
        getRetrofitInstance(GTP_BASE_URL).create(ChatgptNetworkApi::class.java)
}
