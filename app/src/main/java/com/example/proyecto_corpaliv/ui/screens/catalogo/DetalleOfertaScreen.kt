package com.example.proyecto_corpaliv.ui.screens.catalogo

import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.proyecto_corpaliv.data.content.OfertaCatalogo
import com.example.proyecto_corpaliv.ui.components.AvisoDatosFicticios
import com.example.proyecto_corpaliv.ui.components.EncabezadoSeccion
import com.example.proyecto_corpaliv.ui.components.EstadoVacio
import com.example.proyecto_corpaliv.ui.components.EtiquetaInformativa
import com.example.proyecto_corpaliv.ui.components.IconosContenido


@Composable
fun DetalleOfertaScreen(
    oferta: OfertaCatalogo?,
    onContactar: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (oferta == null) {
        EstadoVacio(
            mensaje = "No encontramos esta oferta. Vuelve al catálogo e inténtalo otra vez.",
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
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Column(Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f),
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Icon(
                            imageVector = IconosContenido.deOferta(oferta.tipo),
                            contentDescription = null,
                            modifier = Modifier.padding(10.dp).size(28.dp)
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    if (oferta.destacado) {
                        Surface(
                            shape = MaterialTheme.shapes.small,
                            color = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    text = "Destacado",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    text = oferta.nombre,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.semantics { heading() }
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = oferta.tipo.etiqueta,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        AvisoDatosFicticios()

        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = oferta.precio,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(12.dp))
            EtiquetaInformativa(oferta.tipo.etiqueta)
        }

        Spacer(Modifier.height(18.dp))
        EncabezadoSeccion(titulo = "Descripción")
        Spacer(Modifier.height(8.dp))
        Text(
            text = oferta.descripcion,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = oferta.detalle,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(22.dp))
        Button(
            onClick = onContactar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Solicitar información")
        }
        Spacer(Modifier.height(8.dp))
    }
}
