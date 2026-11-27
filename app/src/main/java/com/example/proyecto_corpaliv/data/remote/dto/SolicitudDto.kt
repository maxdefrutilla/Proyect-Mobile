package com.example.proyecto_corpaliv.data.remote.dto

/**
 * Espejo de cl.duoc.corpaliv.alianza.Model.AlianzaModel (backend).
 * En el POST se envía id = null y estado = "PENDIENTE".
 */
data class SolicitudDto(
    val id: Long? = null,
    val nombreEmpresa: String,
    val emailContacto: String,
    val telefono: String,
    val cantidadTrabajadores: Int,
    val fechaReunion: String,
    val requiereCapacitacion: Boolean = false,
    val aceptaTerminos: Boolean = false,
    val estado: String = "PENDIENTE"
)
