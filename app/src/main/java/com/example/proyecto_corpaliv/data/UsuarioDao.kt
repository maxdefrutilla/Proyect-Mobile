package com.example.proyecto_corpaliv.data

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Dao

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuario(usuario: UsuarioEntity)

    @Query("SELECT * FROM tabla_usuarios ORDER BY id DESC LIMIT 1")
    suspend fun obtenerUltimoUsuario(): UsuarioEntity?
}