package com.example.proyecto_corpaliv.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val rut: String,
    val telefono: String,
    val correo: String,
    val fotoUri: String?
)