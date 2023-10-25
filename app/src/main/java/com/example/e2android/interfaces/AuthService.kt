package com.example.e2android.interfaces

import com.example.e2android.models.Autenticacion
import com.example.e2android.models.Respuesta
import com.example.e2android.models.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService {
    @Headers("Content-Type: application/json")
    @POST("Usuarios/Token")
    suspend fun autenticarUsuario(@Body request: Autenticacion): Response<Respuesta<Usuario>>
}