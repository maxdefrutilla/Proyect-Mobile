package com.example.proyecto_corpaliv.data.remote

import com.example.proyecto_corpaliv.data.remote.dto.NoticiaExternaDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NoticiasApiService {

    @GET("posts?_limit=3")
    suspend fun getNoticias(): Response<List<NoticiaExternaDto>>

    companion object {

        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        val instance: NoticiasApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NoticiasApiService::class.java)
        }

    }

}