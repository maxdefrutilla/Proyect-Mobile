package com.example.proyecto_corpaliv.ui.screens.programas

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_corpaliv.ui.components.AvisoDatosFicticios
import com.example.proyecto_corpaliv.ui.components.EncabezadoSeccion
import com.example.proyecto_corpaliv.ui.components.EstadoVacio
import com.example.proyecto_corpaliv.ui.components.TarjetaPrograma
import com.example.proyecto_corpaliv.viewmodel.ProgramasViewModel

/**
 * Pantalla con el listado completo de programas de inclusión laboral.
 *
 * El filtro por categoría se controla desde el ViewModel y la lista se
 * anima al cambiar de selección.
 */
@Composable
fun ProgramasScreen(
    onOpenPrograma: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProgramasViewModel = viewModel()
) {
    val programas by viewModel.programas.collectAsStateWithLifecycle()
    val categoriaActiva by viewModel.categoriaSeleccionada.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            EncabezadoSeccion(
                titulo = "Programas",
                subtitulo = "Formación, empleo, producción y sensibilización."
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = categoriaActiva == null,
                    onClick = { viewModel.filtrarPor(null) },
                    label = { Text("Todos") }
                )
                viewModel.categorias.forEach { categoria ->
                    FilterChip(
                        selected = categoriaActiva == categoria,
                        onClick = { viewModel.filtrarPor(categoria) },
                        label = { Text(categoria.etiqueta) }
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            AnimatedContent(
                targetState = programas.size,
                transitionSpec = { fadeIn(tween(220)) togetherWith fadeOut(tween(160)) },
                label = "conteo-programas"
            ) { total ->
                Text(
                    text = if (total == 1) "1 programa disponible" else "$total programas disponibles",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(Modifier.height(8.dp))
            AvisoDatosFicticios()
        }

        if (programas.isEmpty()) {
            EstadoVacio(
                mensaje = "No hay programas en esta categoría. Prueba con otro filtro."
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = programas,
                    key = { programa -> programa.id }
                ) { programa ->
                    TarjetaPrograma(
                        programa = programa,
                        onClick = { onOpenPrograma(programa.id) },
                        modifier = Modifier.animateItem()
                    )
                }
            }
        }
    }
}
