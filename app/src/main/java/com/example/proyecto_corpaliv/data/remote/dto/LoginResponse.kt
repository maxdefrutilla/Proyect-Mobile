package com.example.proyecto_corpaliv.data.remote.dto

/**
 * /auth/login responde un Map con "status" ("ok" | "error") y "token" (puede ser null).
 */
data class LoginResponse(
    val status: String,
    val token: String? = null
)
