package com.example.proyecto_corpaliv.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.proyecto_corpaliv.data.content.CategoriaPrograma
import com.example.proyecto_corpaliv.data.content.TipoCaso
import com.example.proyecto_corpaliv.data.content.TipoOferta


object IconosContenido {

    /** Icono representativo de cada categoría de programa. */
    fun deCategoria(categoria: CategoriaPrograma): ImageVector = when (categoria) {
        CategoriaPrograma.FORMACION -> Icons.Filled.School
        CategoriaPrograma.EMPLEO -> Icons.Filled.Work
        CategoriaPrograma.PRODUCCION -> Icons.Filled.Inventory2
        CategoriaPrograma.SENSIBILIZACION -> Icons.Filled.Campaign
    }

    /** Icono representativo de cada tipo de oferta del catálogo. */
    fun deOferta(tipo: TipoOferta): ImageVector = when (tipo) {
        TipoOferta.PRODUCTO -> Icons.Filled.Storefront
        TipoOferta.SERVICIO -> Icons.Filled.Handshake
        TipoOferta.INICIATIVA_RSE -> Icons.Filled.Groups
    }

    /** Icono representativo de cada tipo de caso de impacto. */
    fun deCaso(tipo: TipoCaso): ImageVector = when (tipo) {
        TipoCaso.INSERCION -> Icons.Filled.WorkOutline
        TipoCaso.DONACION -> Icons.Filled.Handshake
        TipoCaso.VOLUNTARIADO -> Icons.Filled.VolunteerActivism
        TipoCaso.CAPACITACION -> Icons.Filled.Psychology
    }
}
