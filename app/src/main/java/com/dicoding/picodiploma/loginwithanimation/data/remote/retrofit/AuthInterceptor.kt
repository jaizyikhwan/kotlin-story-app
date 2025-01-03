package com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit

import android.content.Context
import android.util.Log
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val pref = UserPreference.getInstance(context.dataStore)
        val session = runBlocking { pref.getSession().first() }
        Log.d("UserPreference", "Session: $session")
        val token = runBlocking { pref.getSession().first().token }
        Log.d("AuthInterceptor", "Token: $token")
        token.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}