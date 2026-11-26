package com.example.proyecto_corpaliv.ui.screens.solicitudes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_corpaliv.data.remote.dto.SolicitudDto
import com.example.proyecto_corpaliv.ui.components.DatoConIcono
import com.example.proyecto_corpaliv.ui.components.EncabezadoSeccion
import com.example.proyecto_corpaliv.ui.components.EstadoVacio
import com.example.proyecto_corpaliv.viewmodel.SolicitudViewModel


@Composable
fun SolicitudesScreen(
    modifier: Modifier = Modifier,
    viewModel: SolicitudViewModel = viewModel()
) {
    val solicitudes by viewModel.listaSolicitudes.collectAsStateWithLifecycle()
    val cargando by viewModel.cargando.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    // GET al entrar a la pantalla. El botón de actualizar permite volver a consultar.
    LaunchedEffect(Unit) { viewModel.cargarSolicitudes() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    EncabezadoSeccion(
                        titulo = "Solicitudes enviadas",
                        subtitulo = "Registros obtenidos en tiempo real desde el microservicio."
                    )
                }
                IconButton(onClick = { viewModel.cargarSolicitudes() }) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Actualizar lista"
                    )
                }
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = if (solicitudes.size == 1) {
                    "1 solicitud registrada"
                } else {
                    "${solicitudes.size} solicitudes registradas"
                },
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            if (error != null) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = error.orEmpty(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        when {
            cargando && solicitudes.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            solicitudes.isEmpty() -> {
                EstadoVacio(
                    mensaje = "No hay solicitudes en el servidor. Envía una desde el " +
                        "formulario de vinculación y vuelve a esta pantalla."
                )
            }

            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = solicitudes,
                        key = { solicitud -> solicitud.id ?: 0L }
                    ) { solicitud ->
                        TarjetaSolicitud(
                            solicitud = solicitud,
                            onAprobar = { viewModel.cambiarEstado(solicitud, "APROBADA") },
                            onRechazar = { viewModel.cambiarEstado(solicitud, "RECHAZADA") },
                            onEliminar = { viewModel.eliminarSolicitud(solicitud) },
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            }
        }
    }
}

/**
 * Tarjeta con los datos de una solicitud del servidor y acciones PUT / DELETE.
 */
@Composable
private fun TarjetaSolicitud(
    solicitud: SolicitudDto,
    onAprobar: () -> Unit,
    onRechazar: () -> Unit,
    onEliminar: () -> Unit,
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
                        text = "#${solicitud.id ?: "?"}",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(Modifier.height(8.dp))
            EtiquetaEstado(estado = solicitud.estado)

            Spacer(Modifier.height(12.dp))
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

            Spacer(Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onAprobar,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("Aprobar")
                }

                OutlinedButton(
                    onClick = onRechazar,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("Rechazar")
                }

                IconButton(onClick = onEliminar) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar solicitud",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun EtiquetaEstado(estado: String, modifier: Modifier = Modifier) {
    val (colorFondo, colorTexto) = when (estado.uppercase()) {
        "APROBADA" -> MaterialTheme.colorScheme.tertiaryContainer to
            MaterialTheme.colorScheme.onTertiaryContainer
        "RECHAZADA" -> MaterialTheme.colorScheme.errorContainer to
            MaterialTheme.colorScheme.onErrorContainer
        else -> MaterialTheme.colorScheme.secondaryContainer to
            MaterialTheme.colorScheme.onSecondaryContainer
    }

    Surface(
        shape = MaterialTheme.shapes.small,
        color = colorFondo,
        contentColor = colorTexto,
        modifier = modifier
    ) {
        Text(
            text = estado,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
        )
    }
}
