package com.example.proyecto_corpaliv.ui.screens.impacto

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
import com.example.proyecto_corpaliv.ui.components.TarjetaCaso
import com.example.proyecto_corpaliv.viewmodel.ImpactoViewModel

@Composable
fun ImpactoScreen(
    onOpenCaso: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ImpactoViewModel = viewModel()
) {
    val casos by viewModel.casos.collectAsStateWithLifecycle()
    val tipoActivo by viewModel.tipoSeleccionado.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            EncabezadoSeccion(
                titulo = "Impacto",
                subtitulo = "Experiencias de inserción, donación, voluntariado y capacitación."
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Cada caso resume una colaboración concreta entre una organización " +
                    "aliada y el equipo de CORPALIV / Alba Lab.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = tipoActivo == null,
                    onClick = { viewModel.filtrarPor(null) },
                    label = { Text("Todos") }
                )
                viewModel.tipos.forEach { tipo ->
                    FilterChip(
                        selected = tipoActivo == tipo,
                        onClick = { viewModel.filtrarPor(tipo) },
                        label = { Text(tipo.etiqueta) }
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            AnimatedContent(
                targetState = casos.size,
                transitionSpec = { fadeIn(tween(220)) togetherWith fadeOut(tween(160)) },
                label = "conteo-casos"
            ) { total ->
                Text(
                    text = if (total == 1) "1 caso publicado" else "$total casos publicados",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(Modifier.height(8.dp))
            AvisoDatosFicticios()
        }

        if (casos.isEmpty()) {
            EstadoVacio(
                mensaje = "No hay casos de este tipo. Prueba con otro filtro."
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = casos,
                    key = { caso -> caso.id }
                ) { caso ->
                    TarjetaCaso(
                        caso = caso,
                        onClick = { onOpenCaso(caso.id) },
                        modifier = Modifier.animateItem()
                    )
                }
            }
        }
    }
}
