package com.example.proyecto_corpaliv.ui.screens.catalogo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_corpaliv.ui.components.EncabezadoSeccion
import com.example.proyecto_corpaliv.ui.components.EstadoVacio
import com.example.proyecto_corpaliv.ui.components.TarjetaOferta
import com.example.proyecto_corpaliv.viewmodel.CatalogoViewModel


@Composable
fun CatalogoScreen(
    onOpenOferta: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CatalogoViewModel = viewModel()
) {
    val ofertas by viewModel.resultados.collectAsStateWithLifecycle()
    val textoBusqueda by viewModel.busqueda.collectAsStateWithLifecycle()
    val tipoActivo by viewModel.tipoSeleccionado.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            EncabezadoSeccion(
                titulo = "Catálogo",
                subtitulo = "Productos del taller, servicios de inclusión e iniciativas RSE."
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = viewModel::buscar,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Buscar en el catálogo") },
                placeholder = { Text("Ej.: dispositivo auditivo, asesoría, voluntariado") },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = null)
                },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = textoBusqueda.isNotEmpty(),
                        enter = fadeIn(tween(150)),
                        exit = fadeOut(tween(150)) + shrinkVertically(tween(150))
                    ) {
                        IconButton(onClick = { viewModel.buscar("") }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Borrar búsqueda"
                            )
                        }
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
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
            Text(
                text = if (ofertas.size == 1) "1 resultado" else "${ofertas.size} resultados",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Box(Modifier.weight(1f)) {
            if (ofertas.isEmpty()) {
                EstadoVacio(
                    mensaje = "No encontramos ofertas con esos criterios. " +
                        "Prueba con otra palabra o quita los filtros."
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 260.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = ofertas,
                        key = { oferta -> oferta.id }
                    ) { oferta ->
                        TarjetaOferta(
                            oferta = oferta,
                            onClick = { onOpenOferta(oferta.id) },
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            }
        }
    }
}
