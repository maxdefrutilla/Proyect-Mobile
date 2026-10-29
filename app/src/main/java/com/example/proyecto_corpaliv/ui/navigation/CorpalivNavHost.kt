package com.example.proyecto_corpaliv.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyecto_corpaliv.data.content.ContenidoRepository
import com.example.proyecto_corpaliv.ui.screens.FormularioAlianzaScreen
import com.example.proyecto_corpaliv.ui.screens.PerfilScreen
import com.example.proyecto_corpaliv.ui.screens.catalogo.CatalogoScreen
import com.example.proyecto_corpaliv.ui.screens.catalogo.DetalleOfertaScreen
import com.example.proyecto_corpaliv.ui.screens.impacto.DetalleCasoScreen
import com.example.proyecto_corpaliv.ui.screens.impacto.ImpactoScreen
import com.example.proyecto_corpaliv.ui.screens.inicio.InicioScreen
import com.example.proyecto_corpaliv.ui.screens.programas.DetalleProgramaScreen
import com.example.proyecto_corpaliv.ui.screens.programas.ProgramasScreen
import com.example.proyecto_corpaliv.ui.screens.solicitudes.SolicitudesScreen

/**
 * Contenedor principal de la aplicación.
 *
 * Se usa un único `Scaffold` que aporta la barra superior y la barra de
 * navegación inferior a todas las pantallas. Esto evita duplicar barras e
 * insets, y mantiene la navegación y la jerarquía visual consistentes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CorpalivApp(
    navController: NavHostController = rememberNavController(),
    repositorio: ContenidoRepository = ContenidoRepository()
) {
    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route
    val mostrarBarraInferior = !Rutas.esDetalle(rutaActual) &&
        rutaActual != Rutas.CONTACTO &&
        rutaActual != Rutas.SOLICITUDES
    val mostrarRetroceso = Rutas.esDetalle(rutaActual) ||
        rutaActual == Rutas.CONTACTO ||
        rutaActual == Rutas.SOLICITUDES

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = tituloDeRuta(rutaActual),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    if (mostrarRetroceso) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            if (mostrarBarraInferior) {
                BarraNavegacion(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Rutas.PERFIL,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                fadeIn(tween(240)) + slideInHorizontally(tween(240)) { ancho -> ancho / 8 }
            },
            exitTransition = { fadeOut(tween(180)) },
            popEnterTransition = { fadeIn(tween(240)) },
            popExitTransition = {
                fadeOut(tween(180)) + slideOutHorizontally(tween(180)) { ancho -> ancho / 8 }
            }
        ) {
            composable(Rutas.PERFIL) {
                PerfilScreen()
            }

            composable(Rutas.INICIO) {
                InicioScreen(
                    onOpenPrograma = { id -> navController.navigate(Rutas.detallePrograma(id)) },
                    onAbrirCatalogo = {
                        navController.navigate(Rutas.CATALOGO) {
                            popUpTo(Rutas.INICIO) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onAbrirContacto = { navController.navigate(Rutas.CONTACTO) }
                )
            }

            composable(Rutas.PROGRAMAS) {
                ProgramasScreen(
                    onOpenPrograma = { id -> navController.navigate(Rutas.detallePrograma(id)) }
                )
            }


            composable(Rutas.IMPACTO) {
                ImpactoScreen(
                    onOpenCaso = { id -> navController.navigate(Rutas.detalleCaso(id)) }
                )
            }

            composable(Rutas.CATALOGO) {
                CatalogoScreen(
                    onOpenOferta = { id -> navController.navigate(Rutas.detalleOferta(id)) }
                )
            }

            composable(Rutas.CONTACTO) {
                FormularioAlianzaScreen(
                    onVerSolicitudes = { navController.navigate(Rutas.SOLICITUDES) }
                )
            }

            composable(Rutas.SOLICITUDES) {
                SolicitudesScreen()
            }

            composable(
                route = Rutas.DETALLE_PROGRAMA,
                arguments = listOf(
                    navArgument("programaId") { type = NavType.StringType }
                )
            ) { entrada ->
                val id = entrada.arguments?.getString("programaId").orEmpty()
                DetalleProgramaScreen(
                    programa = repositorio.programa(id),
                    onContactar = { navController.navigate(Rutas.CONTACTO) }
                )
            }

            composable(
                route = Rutas.DETALLE_CASO,
                arguments = listOf(
                    navArgument("casoId") { type = NavType.StringType }
                )
            ) { entrada ->
                val id = entrada.arguments?.getString("casoId").orEmpty()
                DetalleCasoScreen(caso = repositorio.caso(id))
            }

            composable(
                route = Rutas.DETALLE_OFERTA,
                arguments = listOf(
                    navArgument("ofertaId") { type = NavType.StringType }
                )
            ) { entrada ->
                val id = entrada.arguments?.getString("ofertaId").orEmpty()
                DetalleOfertaScreen(
                    oferta = repositorio.oferta(id),
                    onContactar = { navController.navigate(Rutas.CONTACTO) }
                )
            }


        }
    }
}

/**
 * Devuelve el título que corresponde a cada ruta.
 */
private fun tituloDeRuta(ruta: String?): String = when {
    ruta == Rutas.PERFIL -> "Registro de Empresa / Donante"
    ruta == Rutas.INICIO || ruta == null -> "CORPALIV"
    ruta == Rutas.PROGRAMAS -> "Programas"
    ruta == Rutas.IMPACTO -> "Impacto"
    ruta == Rutas.CATALOGO -> "Catálogo"
    ruta == Rutas.CONTACTO -> "Vinculación empresarial"
    ruta == Rutas.SOLICITUDES -> "Solicitudes enviadas"
    ruta.startsWith("detalle/programa") -> "Detalle del programa"
    ruta.startsWith("detalle/caso") -> "Caso de impacto"
    ruta.startsWith("detalle/oferta") -> "Detalle de la oferta"
    else -> "CORPALIV"
}
