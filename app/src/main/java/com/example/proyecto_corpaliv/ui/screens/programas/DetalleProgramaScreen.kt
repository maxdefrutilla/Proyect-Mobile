package com.example.proyecto_corpaliv.ui.screens.programas

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Place
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
import com.example.proyecto_corpaliv.data.content.ProgramaInclusional
import com.example.proyecto_corpaliv.ui.components.AvisoDatosFicticios
import com.example.proyecto_corpaliv.ui.components.DatoConIcono
import com.example.proyecto_corpaliv.ui.components.EncabezadoSeccion
import com.example.proyecto_corpaliv.ui.components.EstadoVacio
import com.example.proyecto_corpaliv.ui.components.EtiquetaInformativa
import com.example.proyecto_corpaliv.ui.components.IconosContenido

@Composable
fun DetalleProgramaScreen(
    programa: ProgramaInclusional?,
    onContactar: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (programa == null) {
        EstadoVacio(
            mensaje = "No encontramos este programa. Vuelve al listado e inténtalo otra vez.",
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
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f),
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = IconosContenido.deCategoria(programa.categoria),
                        contentDescription = null,
                        modifier = Modifier.padding(10.dp).size(28.dp)
                    )
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    text = programa.nombre,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.semantics { heading() }
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = programa.categoria.etiqueta,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        AvisoDatosFicticios()

        Spacer(Modifier.height(16.dp))
        EncabezadoSeccion(titulo = "Ficha del programa")

        Spacer(Modifier.height(10.dp))
        DatoConIcono(icono = Icons.Filled.DateRange, texto = "Duración: ${programa.duracion}")
        Spacer(Modifier.height(8.dp))
        DatoConIcono(
            icono = Icons.Filled.Groups,
            texto = "${programa.participantes} participantes por ciclo"
        )
        Spacer(Modifier.height(8.dp))
        DatoConIcono(icono = Icons.Filled.Place, texto = "Modalidad: ${programa.modalidad}")

        Spacer(Modifier.height(18.dp))
        EncabezadoSeccion(titulo = "Descripción")
        Spacer(Modifier.height(8.dp))
        Text(
            text = programa.descripcion,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(18.dp))
        EncabezadoSeccion(titulo = "Objetivos del programa")
        Spacer(Modifier.height(10.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            programa.objetivos.forEach { objetivo ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = objetivo,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(Modifier.height(18.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EtiquetaInformativa(programa.duracion)
            EtiquetaInformativa("${programa.participantes} participantes")
        }

        Spacer(Modifier.height(22.dp))
        Button(
            onClick = onContactar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Quiero participar de este programa")
        }
        Spacer(Modifier.height(8.dp))
    }
}
