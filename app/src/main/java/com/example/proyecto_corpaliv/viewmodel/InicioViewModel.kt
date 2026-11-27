package com.example.proyecto_corpaliv.viewmodel

import androidx.lifecycle.ViewModel
import com.example.proyecto_corpaliv.data.content.ContenidoRepository
import com.example.proyecto_corpaliv.data.content.IndicadorInstitucional
import com.example.proyecto_corpaliv.data.content.ProgramaInclusional
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Estado de la pantalla de inicio.
 *
 * Se modela la pantalla completa como un `data class` dentro de un
 * `StateFlow`, de modo que la vista solo tenga que observar un valor y
 * redibujarse. No existen estados de carga ni de error porque el contenido
 * está disponible de forma local y constante.
 */
data class EstadoInicio(
    val proposito: String = "",
    val mision: String = "",
    val vision: String = "",
    val indicadores: List<IndicadorInstitucional> = emptyList(),
    val destacados: List<ProgramaInclusional> = emptyList(),
    val organizacion: String = "",
    val alianza: String = ""
)

/**
 * ViewModel de la pantalla de inicio.
 *
 * Es un `ViewModel` plano, sin dependencias de Android, para que pueda
 * probarse de forma aislada y modificarse en vivo durante la defensa.
 */
class InicioViewModel(
    private val repositorio: ContenidoRepository = ContenidoRepository()
) : ViewModel() {

    private val _estado = MutableStateFlow(EstadoInicio())

    /** Estado observado por la pantalla. */
    val estado: StateFlow<EstadoInicio> = _estado.asStateFlow()

    init {
        _estado.value = EstadoInicio(
            proposito = repositorio.proposito,
            mision = repositorio.mision,
            vision = repositorio.vision,
            indicadores = repositorio.indicadores(),
            destacados = repositorio.programas().take(3),
            organizacion = repositorio.organizacion,
            alianza = repositorio.alianza
        )
    }
}
