package com.example.proyecto_corpaliv.data.repository

import com.example.proyecto_corpaliv.data.remote.ApiService
import com.example.proyecto_corpaliv.data.remote.RetrofitInstance
import com.example.proyecto_corpaliv.data.remote.dto.ProductoDto
import java.io.IOException

/**
 * Acceso al catálogo del microservicio (catalogo-service a través del gateway).
 */
class CatalogoRepository(
    private val apiService: ApiService = RetrofitInstance.apiService
) {

    suspend fun listarProductos(): List<ProductoDto> {
        val response = apiService.getProductos(soloActivos = true)
        if (!response.isSuccessful) {
            throw IOException("No se pudo cargar el catálogo (HTTP ${response.code()})")
        }
        return response.body().orEmpty()
    }
}
