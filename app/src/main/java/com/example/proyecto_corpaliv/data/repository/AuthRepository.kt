package com.example.proyecto_corpaliv.data.repository

import com.example.proyecto_corpaliv.data.remote.ApiService
import com.example.proyecto_corpaliv.data.remote.RetrofitInstance
import com.example.proyecto_corpaliv.data.remote.dto.LoginRequest
import com.example.proyecto_corpaliv.data.remote.dto.LoginResponse
import com.example.proyecto_corpaliv.data.remote.dto.RegisterResponse
import java.io.IOException

/**
 * Acceso al microservicio de autenticación (auth-service a través del gateway).
 */
class AuthRepository(
    private val apiService: ApiService = RetrofitInstance.apiService
) {

    suspend fun login(email: String, password: String): LoginResponse {
        val response = apiService.login(LoginRequest(email, password))
        if (!response.isSuccessful) {
            throw IOException("No se pudo iniciar sesión (HTTP ${response.code()})")
        }
        return response.body()
            ?: throw IOException("El servidor no devolvió una respuesta de login")
    }

    suspend fun registrar(email: String, password: String): RegisterResponse {
        val response = apiService.register(LoginRequest(email, password))
        if (!response.isSuccessful) {
            throw IOException("No se pudo registrar el usuario (HTTP ${response.code()})")
        }
        return response.body()
            ?: throw IOException("El servidor no devolvió una respuesta de registro")
    }
}
