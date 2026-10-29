package com.example.proyecto_corpaliv.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyecto_corpaliv.data.model.SolicitudAlianza
import kotlinx.coroutines.flow.Flow

@Dao
interface SolicitudDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarSolicitud(solicitud: SolicitudAlianza)

    @Query("SELECT * FROM solicitudes_alianza ORDER BY id DESC")
    fun obtenerTodasLasSolicitudes(): Flow<List<SolicitudAlianza>>
}