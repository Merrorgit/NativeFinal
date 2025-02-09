package com.example.financialtracker.API
import com.example.financialtracker.BuildConfig
import android.annotation.SuppressLint
import android.content.Context
import com.example.financialtracker.API.cookie.PersistentCookieJar
import com.example.financialtracker.API.routes.AuthAPI
import com.example.financialtracker.API.routes.GoalAPI
import com.example.financialtracker.API.routes.TransactionAPI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@SuppressLint("NewApi")
class APIService private constructor(context: Context) {

    private val BASE_URL = "http://37.151.225.71:5002/api/"

    private val mRetrofit: Retrofit
    private val cookieJar = PersistentCookieJar(context)

    init {
        val interceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE //
        }

        val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .cookieJar(cookieJar)
            .connectTimeout(30, TimeUnit.SECONDS) // ✅ Added timeout settings
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    fun getAuthAPI(): AuthAPI = mRetrofit.create(AuthAPI::class.java)

    fun getTransactionAPI(): TransactionAPI = mRetrofit.create(TransactionAPI::class.java)
    fun getGoalApi(): GoalAPI = mRetrofit.create(GoalAPI::class.java)
    fun getCookieJar(): PersistentCookieJar = cookieJar

    companion object {
        @Volatile
        private var INSTANCE: APIService? = null

        fun getInstance(context: Context): APIService {
            return INSTANCE ?: synchronized(this) { // ✅ Thread-safe singleton
                INSTANCE ?: APIService(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
}
