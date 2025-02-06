package com.example.financialtracker.API

import android.annotation.SuppressLint
import android.content.Context
import com.example.financialtracker.API.cookie.PersistentCookieJar
import com.example.financialtracker.API.routes.AuthAPI
import com.example.financialtracker.API.routes.TransactionAPI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("NewApi")
class APIService private constructor(context: Context) {

    private val BASE_URL = "http://37.151.225.71:5002/api/"
    private val mRetrofit: Retrofit
    private val cookieJar = PersistentCookieJar(context)

    init {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .cookieJar(cookieJar)
            .build()

        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    fun getAuthAPI(): AuthAPI {
        return mRetrofit.create(AuthAPI::class.java)
    }

    fun getTransactionAPI(): TransactionAPI {
        return mRetrofit.create(TransactionAPI::class.java)
    }


    fun getCookieJar(): PersistentCookieJar {
        return cookieJar
    }

    companion object {
        @Volatile
        private var mInstance: APIService? = null

        fun getInstance(context: Context): APIService? {
            if (mInstance == null) {
                mInstance = APIService(context)
            }
            return mInstance
        }
    }
}
