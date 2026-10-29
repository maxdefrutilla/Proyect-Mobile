package com.example.proyecto_corpaliv.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.proyecto_corpaliv.CamaraUriFactory
import com.example.proyecto_corpaliv.data.AppDatabase
import com.example.proyecto_corpaliv.data.UsuarioEntity
import kotlinx.coroutines.launch

@Composable
fun PerfilScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val db = remember { AppDatabase.getDatabase(context) }

    var imagenUri by remember { mutableStateOf<Uri?>(null) }
    var uriTemporal by remember { mutableStateOf<Uri?>(null) }

    // Estados para los campos de texto
    var nombre by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    val lanzadorCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { fotoGuardada ->
        if (fotoGuardada) {
            imagenUri = uriTemporal
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registro de Empresa / Donante",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Vista previa de la foto tomada
        imagenUri?.let { uri ->
            AsyncImage(
                model = uri,
                contentDescription = "Logo o Foto subida",
                modifier = Modifier.size(130.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        Button(onClick = {
            val nuevaUri = CamaraUriFactory.crearUriParaFoto(context)
            uriTemporal = nuevaUri
            lanzadorCamara.launch(nuevaUri)
        }) {
            Text(text = if (imagenUri == null) "Tomar Foto / Logo" else "Cambiar Foto")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Campos de texto del formulario
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre / Razón Social") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = rut,
            onValueChange = { rut = it },
            label = { Text("RUT") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono de Contacto") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )



        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (nombre.isNotBlank() && rut.isNotBlank()) {
                    coroutineScope.launch {
                        val nuevoUsuario = UsuarioEntity(
                            nombre = nombre,
                            rut = rut,
                            telefono = telefono,
                            correo = correo,
                            fotoUri = imagenUri?.toString()
                        )
                        db.usuarioDao().insertarUsuario(nuevoUsuario)
                        Toast.makeText(context, "Registro guardado exitosamente", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Por favor completa al menos Nombre y RUT", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar Registro")
        }
    }
}