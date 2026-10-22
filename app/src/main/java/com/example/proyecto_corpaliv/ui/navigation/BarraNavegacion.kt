package com.example.proyecto_corpaliv.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

/** Ítem declarado en la barra inferior de navegación. */
private data class ItemMenu(
    val ruta: String,
    val etiqueta: String,
    val icono: ImageVector
)

private val ItemsMenu = listOf(
    ItemMenu(Rutas.INICIO, "Inicio", Icons.Filled.Home),
    ItemMenu(Rutas.PROGRAMAS, "Programas", Icons.Filled.Work),
    ItemMenu(Rutas.IMPACTO, "Impacto", Icons.Filled.VolunteerActivism),
    ItemMenu(Rutas.CATALOGO, "Catálogo", Icons.Filled.Storefront)
)

/**
 * Barra inferior de navegación principal.
 *
 * Se usa el patrón recomendado de Compose Navigation: al cambiar de sección se
 * conserva el estado previo de cada pestaña (`saveState` / `restoreState`) y
 * se evita apilar destinos duplicados (`launchSingleTop`).
 */
@Composable
fun BarraNavegacion(navController: NavHostController) {
    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route

    NavigationBar {
        ItemsMenu.forEach { item ->
            NavigationBarItem(
                selected = rutaActual == item.ruta,
                onClick = {
                    if (rutaActual != item.ruta) {
                        navController.navigate(item.ruta) {
                            popUpTo(Rutas.INICIO) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icono,
                        contentDescription = item.etiqueta
                    )
                },
                label = { Text(item.etiqueta) }
            )
        }
    }
}
