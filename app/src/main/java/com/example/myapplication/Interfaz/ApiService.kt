package com.example.myapplication.Interfaz

import com.example.myapplication.modelo.Recurso
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("RecursosApi/recursos")
    fun getRecursos(): Call<List<Recurso>>
    @POST("RecursosApi/recursos")
    fun postRecurso(@Body recurso: Recurso): Call<Recurso>
    @GET("RecursosApi/recursos/{id}")
    fun getRecursoPorId(@Path("id") id: String): Call<Recurso>

    @PUT("RecursosApi/recursos/{id}")
    fun putRecurso(@Path("id") id: String, @Body recurso: Recurso): Call<Recurso>

    @DELETE("RecursosApi/recursos/{id}")
    fun deleteRecurso(@Path("id") id: String): Call<Void>

}
