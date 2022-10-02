package com.example.testyourself.data.network.api

import com.example.testyourself.utils.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ExamRetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val loging = HttpLoggingInterceptor()
            loging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loging)
                .build()
            Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create(ExamApiInterface::class.java)
        }
    }


}