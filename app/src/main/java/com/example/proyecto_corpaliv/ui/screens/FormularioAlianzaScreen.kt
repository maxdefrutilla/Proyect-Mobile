package com.example.proyecto_corpaliv.ui.screens

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_corpaliv.viewmodel.SolicitudViewModel
import java.util.Calendar

/**
 * Formulario de vinculación empresarial.
 *
 * Toda la validación y el estado viven en `SolicitudViewModel`. La pantalla
 * solo dibuja los valores y los mensajes de error que el ViewModel expone,
 * de modo que la lógica queda desacoplada de la interfaz.
 *
 * Cada campo inválido muestra icono de advertencia, borde de error y texto de
 * apoyo, cumpliendo el requisito de retroalimentación visual.
 */
@Composable
fun FormularioAlianzaScreen(
    onVerSolicitudes: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SolicitudViewModel = viewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Estados desde el ViewModel
    val nombreEmpresa by viewModel.nombreEmpresa.collectAsStateWithLifecycle()
    val emailContacto by viewModel.emailContacto.collectAsStateWithLifecycle()
    val telefono by viewModel.telefono.collectAsStateWithLifecycle()
    val cantidadTrabajadores by viewModel.cantidadTrabajadores.collectAsStateWithLifecycle()
    val fechaReunion by viewModel.fechaReunion.collectAsStateWithLifecycle()
    val requiereCapacitacion by viewModel.requiereCapacitacion.collectAsStateWithLifecycle()
    val aceptaTerminos by viewModel.aceptaTerminos.collectAsStateWithLifecycle()

    // Errores desde el ViewModel
    val nombreError by viewModel.nombreError.collectAsStateWithLifecycle()
    val emailError by viewModel.emailError.collectAsStateWithLifecycle()
    val telefonoError by viewModel.telefonoError.collectAsStateWithLifecycle()
    val cantidadError by viewModel.cantidadError.collectAsStateWithLifecycle()
    val fechaError by viewModel.fechaError.collectAsStateWithLifecycle()
    val terminosError by viewModel.terminosError.collectAsStateWithLifecycle()

    val guardadoExitoso by viewModel.guardadoExitoso.collectAsStateWithLifecycle()

    // Retroalimentación al guardar correctamente
    LaunchedEffect(guardadoExitoso) {
        if (guardadoExitoso) {
            Toast.makeText(
                context,
                "Su solicitud ha sido enviada exitosamente",
                Toast.LENGTH_LONG
            ).show()
            viewModel.reiniciarEstadoGuardado()
        }
    }

    // Selector de fecha, estable entre recomposiciones
    val calendario = remember { Calendar.getInstance() }
    val datePickerDialog = remember(context, viewModel) {
        DatePickerDialog(
            context,
            { _, anioSeleccionado, mesSeleccionado, diaSeleccionado ->
                viewModel.onFechaChange("$diaSeleccionado/${mesSeleccionado + 1}/$anioSeleccionado")
            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Encabezado
        Text(
            text = "Solicitud de alianza",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Completa el formulario para solicitar una alianza o un programa de " +
                "inclusión con CORPALIV y Alba Lab. Todos los campos son obligatorios.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Nombre de la empresa
        OutlinedTextField(
            value = nombreEmpresa,
            onValueChange = { viewModel.onNombreChange(it) },
            label = { Text("Nombre de la empresa o institución") },
            modifier = Modifier.fillMaxWidth(),
            isError = nombreError != null,
            trailingIcon = {
                if (nombreError != null) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                nombreError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            singleLine = true
        )

        // Correo de contacto
        OutlinedTextField(
            value = emailContacto,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Correo electrónico de contacto") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = emailError != null,
            trailingIcon = {
                if (emailError != null) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            singleLine = true
        )

        // Teléfono de contacto
        OutlinedTextField(
            value = telefono,
            onValueChange = { viewModel.onTelefonoChange(it) },
            label = { Text("Teléfono de contacto") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = telefonoError != null,
            trailingIcon = {
                if (telefonoError != null) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                telefonoError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            singleLine = true
        )

        // Cantidad de trabajadores
        OutlinedTextField(
            value = cantidadTrabajadores,
            onValueChange = { viewModel.onCantidadChange(it) },
            label = { Text("Cantidad estimada de trabajadores") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = cantidadError != null,
            trailingIcon = {
                if (cantidadError != null) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                cantidadError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            singleLine = true
        )

        // Selector de fecha
        OutlinedTextField(
            value = fechaReunion,
            onValueChange = { },
            readOnly = true,
            label = { Text("Fecha de la reunión") },
            placeholder = { Text("Selecciona una fecha") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() },
            isError = fechaError != null,
            trailingIcon = {
                if (fechaError != null) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Abrir calendario",
                        modifier = Modifier.clickable { datePickerDialog.show() }
                    )
                }
            },
            supportingText = {
                fechaError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
        )

        // Switch de capacitaciones RSE
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "¿Requiere capacitaciones RSE?",
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Charlas de sensibilización y preparación para equipos",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = requiereCapacitacion,
                    onCheckedChange = { viewModel.onRequiereCapacitacionChange(it) }
                )
            }
        }

        // Checkbox de términos y condiciones
        Column(Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = aceptaTerminos,
                    onCheckedChange = { viewModel.onAceptaTerminosChange(it) }
                )
                Text(
                    text = "He leído y acepto los términos y condiciones",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
            AnimatedVisibility(
                visible = terminosError != null,
                enter = fadeIn(tween(150)) + expandVertically(tween(150)),
                exit = fadeOut(tween(150)) + shrinkVertically(tween(150))
            ) {
                Row(
                    modifier = Modifier.padding(start = 12.dp, top = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = terminosError.orEmpty(),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón enviar
        Button(
            onClick = { viewModel.guardarSolicitud() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Enviar solicitud de alianza")
        }

        // Acceso al listado de solicitudes guardadas
        OutlinedButton(
            onClick = onVerSolicitudes,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.List,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text("Ver solicitudes enviadas")
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}
