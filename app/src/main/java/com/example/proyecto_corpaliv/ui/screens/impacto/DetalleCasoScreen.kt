package com.example.proyecto_corpaliv.ui.screens.impacto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.proyecto_corpaliv.data.content.CasoImpacto
import com.example.proyecto_corpaliv.ui.components.AvisoDatosFicticios
import com.example.proyecto_corpaliv.ui.components.EncabezadoSeccion
import com.example.proyecto_corpaliv.ui.components.EstadoVacio
import com.example.proyecto_corpaliv.ui.components.IconosContenido
import com.example.proyecto_corpaliv.ui.components.IndicadorImpacto

/**
 * Detalle de un caso de impacto.
 */
@Composable
fun DetalleCasoScreen(
    caso: CasoImpacto?,
    modifier: Modifier = Modifier
) {
    if (caso == null) {
        EstadoVacio(
            mensaje = "No encontramos este caso de impacto. Vuelve al listado e inténtalo otra vez.",
            modifier = modifier.fillMaxSize()
        )
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            Column(Modifier.padding(20.dp)) {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.15f),
                    contentColor = MaterialTheme.colorScheme.onTertiary
                ) {
                    Icon(
                        imageVector = IconosContenido.deCaso(caso.tipo),
                        contentDescription = null,
                        modifier = Modifier.padding(10.dp).size(28.dp)
                    )
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    text = caso.titulo,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.semantics { heading() }
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${caso.tipo.etiqueta} · ${caso.fecha}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.9f)
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        AvisoDatosFicticios()

        Spacer(Modifier.height(16.dp))
        EncabezadoSeccion(titulo = "Resultados del caso")
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            caso.indicadores.forEach { indicador ->
                IndicadorImpacto(
                    valor = indicador.valor,
                    etiqueta = indicador.etiqueta,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(Modifier.height(20.dp))
        EncabezadoSeccion(titulo = "Resumen")
        Spacer(Modifier.height(8.dp))
        Text(
            text = caso.resumen,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(18.dp))
        EncabezadoSeccion(titulo = "Relato de la experiencia")
        Spacer(Modifier.height(8.dp))
        Text(
            text = caso.relato,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(24.dp))
    }
}
