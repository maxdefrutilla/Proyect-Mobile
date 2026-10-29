package com.example.proyecto_corpaliv.data.repository

import com.example.proyecto_corpaliv.data.local.SolicitudDao
import com.example.proyecto_corpaliv.data.model.SolicitudAlianza
import kotlinx.coroutines.flow.Flow

class SolicitudRepository(private val solicitudDao: SolicitudDao) {

    val todasLasSolicitudes: Flow<List<SolicitudAlianza>> = solicitudDao.obtenerTodasLasSolicitudes()

    suspend fun insertar(solicitud: SolicitudAlianza) {
        solicitudDao.insertarSolicitud(solicitud)
    }
}