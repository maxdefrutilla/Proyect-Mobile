package com.example.proyecto_corpaliv.data.repository

import com.example.proyecto_corpaliv.data.local.SolicitudDao
import com.example.proyecto_corpaliv.data.model.SolicitudAlianza
import com.example.proyecto_corpaliv.data.remote.ApiService
import com.example.proyecto_corpaliv.data.remote.RetrofitInstance
import com.example.proyecto_corpaliv.data.remote.dto.SolicitudDto
import kotlinx.coroutines.flow.Flow
import java.io.IOException

/**
 * Coordina la persistencia local (Room) con el microservicio de alianzas (Retrofit).
 * El servidor es la fuente de verdad para el listado; Room guarda una copia de respaldo.
 */
class SolicitudRepository(
    private val solicitudDao: SolicitudDao,
    private val apiService: ApiService = RetrofitInstance.apiService
) {

    val todasLasSolicitudes: Flow<List<SolicitudAlianza>> =
        solicitudDao.obtenerTodasLasSolicitudes()

    suspend fun insertar(solicitud: SolicitudAlianza) {
        solicitudDao.insertarSolicitud(solicitud)
    }

    // --- Operaciones remotas ---

    suspend fun listarRemotas(): List<SolicitudDto> {
        val response = apiService.getSolicitudes()
        if (!response.isSuccessful) {
            throw IOException("No se pudo cargar el listado (HTTP ${response.code()})")
        }
        return response.body().orEmpty()
    }

    suspend fun crearRemota(solicitud: SolicitudDto): SolicitudDto {
        val response = apiService.crearSolicitud(solicitud)
        if (!response.isSuccessful) {
            throw IOException("No se pudo enviar la solicitud (HTTP ${response.code()})")
        }
        return response.body()
            ?: throw IOException("El servidor no devolvió la solicitud creada")
    }

    suspend fun actualizarRemota(id: Long, solicitud: SolicitudDto): SolicitudDto {
        val response = apiService.actualizarSolicitud(id, solicitud)
        if (!response.isSuccessful) {
            throw IOException("No se pudo actualizar el estado (HTTP ${response.code()})")
        }
        return response.body()
            ?: throw IOException("El servidor no devolvió la solicitud actualizada")
    }

    suspend fun eliminarRemota(id: Long) {
        val response = apiService.eliminarSolicitud(id)
        if (!response.isSuccessful) {
            throw IOException("No se pudo eliminar la solicitud (HTTP ${response.code()})")
        }
    }
}
