package com.example.proyecto_corpaliv.ui.screens.solicitudes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_corpaliv.data.model.SolicitudAlianza
import com.example.proyecto_corpaliv.ui.components.DatoConIcono
import com.example.proyecto_corpaliv.ui.components.EncabezadoSeccion
import com.example.proyecto_corpaliv.ui.components.EstadoVacio
import com.example.proyecto_corpaliv.viewmodel.SolicitudViewModel

/**
 * Pantalla con las solicitudes de alianza guardadas en la base de datos local.
 *
 * Expone en la interfaz el flujo `listaSolicitudes` del `SolicitudViewModel`,
 * de modo que la persistencia con Room no solo ocurre, sino que además puede
 * verse y demostrarse desde la propia aplicación: al enviar el formulario, el
 * registro aparece de inmediato en esta lista.
 */
@Composable
fun SolicitudesScreen(
    modifier: Modifier = Modifier,
    viewModel: SolicitudViewModel = viewModel()
) {
    val solicitudes by viewModel.listaSolicitudes.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(Modifier.padding(16.dp)) {
            EncabezadoSeccion(
                titulo = "Solicitudes enviadas",
                subtitulo = "Registros guardados en la base de datos local del dispositivo."
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = if (solicitudes.size == 1) {
                    "1 solicitud almacenada"
                } else {
                    "${solicitudes.size} solicitudes almacenadas"
                },
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (solicitudes.isEmpty()) {
            EstadoVacio(
                mensaje = "Todavía no hay solicitudes guardadas. Completa el formulario de " +
                    "alianza y vuelve a esta pantalla."
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = solicitudes,
                    key = { solicitud -> solicitud.id }
                ) { solicitud ->
                    TarjetaSolicitud(
                        solicitud = solicitud,
                        modifier = Modifier.animateItem()
                    )
                }
            }
        }
    }
}

/**
 * Tarjeta con los datos de una solicitud guardada.
 */
@Composable
private fun TarjetaSolicitud(
    solicitud: SolicitudAlianza,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = solicitud.nombreEmpresa,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Text(
                        text = "#${solicitud.id}",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            DatoConIcono(icono = Icons.Filled.Email, texto = solicitud.emailContacto)
            Spacer(Modifier.height(6.dp))
            DatoConIcono(icono = Icons.Filled.Phone, texto = solicitud.telefono)
            Spacer(Modifier.height(6.dp))
            DatoConIcono(
                icono = Icons.Filled.Groups,
                texto = "${solicitud.cantidadTrabajadores} trabajadores"
            )
            Spacer(Modifier.height(6.dp))
            DatoConIcono(
                icono = Icons.Filled.DateRange,
                texto = "Reunión: ${solicitud.fechaReunion}"
            )
            if (solicitud.requiereCapacitacion) {
                Spacer(Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.School,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Requiere capacitación",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}
