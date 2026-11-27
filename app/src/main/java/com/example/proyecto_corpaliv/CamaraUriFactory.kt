package com.example.proyecto_corpaliv

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

object CamaraUriFactory {
    fun crearUriParaFoto(context: Context): Uri {
        val archivoTemporal = File(context.cacheDir, "foto_perfil_${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            archivoTemporal
        )
    }
}