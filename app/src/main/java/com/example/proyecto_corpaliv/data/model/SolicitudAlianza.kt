package com.example.proyecto_corpaliv.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "solicitudes_alianza")
data class SolicitudAlianza (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreEmpresa: String,
    val emailContacto: String,
    val telefono: String,
    val cantidadTrabajadores: Int,
    val fechaReunion: String,
    val requiereCapacitacion: Boolean,
    val aceptaTerminos: Boolean
)