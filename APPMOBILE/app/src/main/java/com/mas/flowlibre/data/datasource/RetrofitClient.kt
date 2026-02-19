package com.mas.flowlibre.data.datasource







import android.content.Context

import android.util.Log

import com.mas.flowlibre.data.session.SessionManager

import okhttp3.Interceptor

import okhttp3.OkHttpClient



import okhttp3.logging.HttpLoggingInterceptor



import retrofit2.Retrofit



import retrofit2.converter.gson.GsonConverterFactory



import java.util.concurrent.TimeUnit



import kotlin.getValue







object RetrofitClient {







    private const val BASE_URL= "http://10.0.2.2:8000/" //ip = IPv4 de su Wifi

    private var context: Context? = null





    fun init(context: Context){

        this.context = context

    }





    private val authInterceptor: Interceptor by lazy {

        Interceptor { chain ->
            val request = chain.request()
            val url = request.url.toString()
            Log.d("RetrofitClient", "Context disponible: ${context != null}")
            Log.d("RetrofitClient", "URL: ${request.url}")

            val isPublicEndpoint = url.contains("/api/users/login/") ||
                    url.contains("/api/users/register/")

            if (!isPublicEndpoint && context != null) {
                val sessionManager = SessionManager(context!!)
                val token = sessionManager.getAccessToken()

                Log.d("RetrofitClient", "Token recuperado: $token")

                if (token != null) {
                    val newRequest = request.newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                    Log.d("RetrofitClient", "Authorization header agregado: Bearer $token")
                    chain.proceed(newRequest)
                } else {
                    Log.d("RetrofitClient", "Token es null")
                    chain.proceed(request)
                }
            } else {
                if (isPublicEndpoint) {
                    Log.d("RetrofitClient", "Endpoint público - no se envía token")
                } else {
                    Log.d("RetrofitClient", "Context es null - no se puede obtener token")
                }
                chain.proceed(request)
            }
        }
    }





    private val logging: HttpLoggingInterceptor by lazy {



        HttpLoggingInterceptor().apply {



            level = HttpLoggingInterceptor.Level.BODY



        }



    }







    private val okHttpClient: OkHttpClient by lazy {



        OkHttpClient.Builder()



            .addInterceptor(logging)



            .addInterceptor(authInterceptor)



            .connectTimeout(20, TimeUnit.SECONDS)



            .readTimeout(20, TimeUnit.SECONDS)



            .writeTimeout(20, TimeUnit.SECONDS)



            .build()



    }







    private val retrofit: Retrofit by lazy{



        Retrofit.Builder()



            .baseUrl(BASE_URL)



            .client(okHttpClient)



            .addConverterFactory(GsonConverterFactory.create())



            .build()



    }







    val api: ApiService by lazy {



        retrofit.create(ApiService::class.java)



    }



}