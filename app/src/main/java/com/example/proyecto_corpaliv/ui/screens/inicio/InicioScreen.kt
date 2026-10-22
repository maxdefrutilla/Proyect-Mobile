package com.example.proyecto_corpaliv.ui.screens.inicio

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
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
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_corpaliv.ui.components.AvisoDatosFicticios
import com.example.proyecto_corpaliv.ui.components.EncabezadoSeccion
import com.example.proyecto_corpaliv.ui.components.IndicadorImpacto
import com.example.proyecto_corpaliv.ui.components.TarjetaModalidad
import com.example.proyecto_corpaliv.ui.components.TarjetaPrograma
import com.example.proyecto_corpaliv.viewmodel.InicioViewModel

@Composable
fun InicioScreen(
    onOpenPrograma: (String) -> Unit,
    onAbrirCatalogo: () -> Unit,
    onAbrirContacto: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InicioViewModel = viewModel()
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()

    // Las secciones aparecen escalonadamente para jerarquizar la lectura.
    var visibles by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visibles = true }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp,
            bottom = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(key = "cabecera") {
            AnimatedVisibility(
                visible = visibles,
                enter = fadeIn(tween(300)) + expandVertically(tween(300))
            ) {
                CabeceraInstitucional(
                    organizacion = estado.organizacion,
                    alianza = estado.alianza,
                    proposito = estado.proposito,
                    mision = estado.mision,
                    vision = estado.vision
                )
            }
        }

        item(key = "aviso") {
            AvisoDatosFicticios()
        }

        item(key = "titulo-indicadores") {
            EncabezadoSeccion(
                titulo = "Nuestro impacto",
                subtitulo = "Cifras acumuladas del programa de inclusión."
            )
        }

        items(
            items = estado.indicadores.chunked(2),
            key = { fila -> "indicadores-${fila.first().etiqueta}" }
        ) { fila ->
            AnimatedVisibility(
                visible = visibles,
                enter = fadeIn(tween(350)) + expandVertically(tween(350))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    fila.forEach { indicador ->
                        IndicadorImpacto(
                            valor = indicador.valor,
                            etiqueta = indicador.etiqueta,
                            descripcion = indicador.descripcion,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (fila.size == 1) {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }

        item(key = "titulo-programas") {
            EncabezadoSeccion(
                titulo = "Programas destacados",
                subtitulo = "Líneas de trabajo abiertas a la comunidad y a las empresas."
            )
        }

        items(
            items = estado.destacados,
            key = { programa -> programa.id }
        ) { programa ->
            TarjetaPrograma(
                programa = programa,
                onClick = { onOpenPrograma(programa.id) },
                modifier = Modifier.animateItem()
            )
        }

        item(key = "boton-programas") {
            OutlinedButton(
                onClick = onAbrirCatalogo,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver el catálogo completo")
            }
        }

        item(key = "titulo-colaborar") {
            EncabezadoSeccion(
                titulo = "Cómo colaborar",
                subtitulo = "Elige la alternativa que mejor se adapte a tu organización."
            )
        }

        item(key = "modalidades") {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                TarjetaModalidad(
                    titulo = "Donaciones",
                    descripcion = "Aporta recursos a una línea específica del programa y recibe " +
                        "el reporte de uso de los fondos.",
                    icono = Icons.Filled.Campaign
                )
                TarjetaModalidad(
                    titulo = "Alianzas empresariales",
                    descripcion = "Diseña un plan de inclusión, capacitación o contratación " +
                        "accesible junto a nuestro equipo.",
                    icono = Icons.Filled.Handshake
                )
                TarjetaModalidad(
                    titulo = "Voluntariado",
                    descripcion = "Participa en jornadas de trabajo en el taller y en las " +
                        "actividades de sensibilización.",
                    icono = Icons.Filled.VolunteerActivism
                )
            }
        }

        item(key = "cta") {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text(
                        text = "¿Te interesa una alianza?",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Completa el formulario de vinculación y nuestro equipo te " +
                            "contactará para avanzar.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(14.dp))
                    Button(
                        onClick = onAbrirContacto,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Handshake,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Ir al formulario de alianza")
                    }
                }
            }
        }
    }
}

/**
 * Bloque de presentación con propósito, misión y visión.
 */
@Composable
private fun CabeceraInstitucional(
    organizacion: String,
    alianza: String,
    proposito: String,
    mision: String,
    vision: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                text = organizacion,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "en alianza con $alianza",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
            )
            Spacer(Modifier.height(14.dp))
            Text(
                text = proposito,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.semantics { heading() }
            )
            Spacer(Modifier.height(14.dp))
            BloqueDestacado(etiqueta = "Misión", texto = mision)
            Spacer(Modifier.height(8.dp))
            BloqueDestacado(etiqueta = "Visión", texto = vision)
        }
    }
}

/**
 * Etiqueta destacada para misión o visión.
 */
@Composable
private fun BloqueDestacado(etiqueta: String, texto: String) {
    Column {
        Text(
            text = etiqueta.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = texto,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
