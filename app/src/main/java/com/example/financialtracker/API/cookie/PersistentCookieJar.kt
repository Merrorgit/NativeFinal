package com.example.financialtracker.API.cookie

import android.content.Context
import android.content.SharedPreferences
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class PersistentCookieJar(context: Context) : CookieJar {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("cookie_prefs", Context.MODE_PRIVATE)

    private val cookieStore: MutableMap<String, List<Cookie>> = mutableMapOf()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore[url.host()] = cookies
        saveCookiesToPrefs(url.host(), cookies)
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        // Если куки уже загружены в память — используем их
        if (cookieStore.containsKey(url.host())) {
            return cookieStore[url.host()] ?: emptyList()
        }

        // Иначе загружаем из SharedPreferences
        val cookies = loadCookiesFromPrefs(url.host())
        cookieStore[url.host()] = cookies
        return cookies
    }

    private fun saveCookiesToPrefs(host: String, cookies: List<Cookie>) {
        val cookieStrings = cookies.map { it.toString() }
        sharedPreferences.edit().putStringSet(host, cookieStrings.toSet()).apply()
    }

    private fun loadCookiesFromPrefs(host: String): List<Cookie> {
        val cookieStrings = sharedPreferences.getStringSet(host, emptySet()) ?: emptySet()
        return cookieStrings.mapNotNull { Cookie.parse(HttpUrl.Builder().scheme("http").host(host).build(), it) }
    }

    fun clearCookies() {
        sharedPreferences.edit().clear().apply()
        cookieStore.clear()
    }
}

