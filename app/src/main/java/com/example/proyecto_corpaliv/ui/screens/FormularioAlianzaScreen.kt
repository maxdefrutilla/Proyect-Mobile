package com.example.proyecto_corpaliv.ui.screens

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_corpaliv.viewmodel.SolicitudViewModel
import java.util.Calendar

@Composable
fun FormularioAlianzaScreen(
    viewModel: SolicitudViewModel = viewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    //Estados desde el ViewModel
    val nombreEmpresa by viewModel.nombreEmpresa.collectAsState()
    val emailContacto by viewModel.emailContacto.collectAsState()
    val telefono by viewModel.telefono.collectAsState()
    val cantidadTrabajadores by viewModel.cantidadTrabajadores.collectAsState()
    val fechaReunion by viewModel.fechaReunion.collectAsState()
    val requiereCapacitacion by viewModel.requiereCapacitacion.collectAsState()
    val aceptaTerminos by viewModel.aceptaTerminos.collectAsState()

    //Errores desde el ViewModel
    val nombreError by viewModel.nombreError.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val telefonoError by viewModel.telefonoError.collectAsState()
    val cantidadError by viewModel.cantidadError.collectAsState()
    val fechaError by viewModel.fechaError.collectAsState()
    val terminosError by viewModel.terminosError.collectAsState()

    val guardadoExitoso by viewModel.guardadoExitoso.collectAsState()

    //Feedback visual al momento de guardar exitosamente
    LaunchedEffect(guardadoExitoso) {
        if (guardadoExitoso) {
            Toast.makeText(context, "Su solicitud a sido enviada exitosamente", Toast.LENGTH_LONG).show()
            viewModel.reiniciarEstadoGuardado()
        }
    }

    //Dialogo selector de fecha
    val calendario = Calendar.getInstance()
    val anio = calendario.get(Calendar.YEAR)
    val mes = calendario.get(Calendar.MONTH)
    val dia = calendario.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, y, m, d ->
            viewModel.onFechaChange("$d/${m + 1}/$y")
        }, anio, mes, dia
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        //Encabezado
        Text(
            text = "Vinculación Empresarial",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Completa el formulario para solicitar una alianza o programa de inclusión con CORPALIV y Alba Lab.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(4.dp))

        //Nombre de la empresa
        OutlinedTextField(
            value = nombreEmpresa,
            onValueChange = { viewModel.onNombreChange(it) },
            label = { Text("Nombre de la empresa o Institución") },
            modifier = Modifier.fillMaxWidth(),
            isError = nombreError != null,
            trailingIcon = {
            if (nombreError != null) Icon(
                Icons.Default.Warning,
                contentDescription = "Error",
                tint = MaterialTheme.colorScheme.error
            )
            },
            supportingText = {
                nombreError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            singleLine = true
        )

        //Correo de contacto
        OutlinedTextField(
            value = emailContacto,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Correo electrónico de contacto") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = emailError != null,
            trailingIcon = {
                if (emailError != null) Icon(
                    Icons.Default.Warning,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error
                )
            },
            supportingText = {
                emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            singleLine = true
        )

        // Telefono de contacto
        OutlinedTextField(
            value = telefono,
            onValueChange = { viewModel.onTelefonoChange(it) },
            label = { Text("Teléfono de contacto") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = telefonoError != null,
            trailingIcon = {
                if (telefonoError != null) Icon(
                    Icons.Default.Warning,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error
                )
            },
            supportingText = {
                telefonoError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            singleLine = true
        )

        //Cantidad de trabajadores
        OutlinedTextField(
            value = cantidadTrabajadores,
            onValueChange = { viewModel.onCantidadChange(it) },
            label = { Text("Cantidad estimada de trabajadores") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = cantidadError != null,
            trailingIcon = {
                if (cantidadError != null) Icon(
                    Icons.Default.Warning,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error
                )
            },
            supportingText = {
                cantidadError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            singleLine = true
        )

        //Selector de fecha
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
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Calendario",
                    modifier = Modifier.clickable { datePickerDialog.show() }
                )
            },

            supportingText = {
                fechaError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
        )

        //Switch Capacitaciones RSE
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
                    Text("¿Requiere capacitaciones RSE?", fontWeight = FontWeight.SemiBold)
                    Text(
                        "Charlas de sensibilibilización y preparación para equipos",
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

        //Checkbox terminos y condiciones
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

        Spacer(modifier = Modifier.height(8.dp))

        //Boton enviar
        Button(
            onClick = { viewModel.guardarSolicitud() },
            modifier = Modifier
                .fillMaxSize()
                .height(50.dp)
        ) {
            Text("Enviar Solicitud de Alianza", style = MaterialTheme.typography.bodyMedium)
        }
    }
}