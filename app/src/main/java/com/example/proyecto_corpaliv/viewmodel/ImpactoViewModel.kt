package com.example.proyecto_corpaliv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_corpaliv.data.content.CasoImpacto
import com.example.proyecto_corpaliv.data.content.ContenidoRepository
import com.example.proyecto_corpaliv.data.content.TipoCaso
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel de la pantalla de impacto.
 *
 * Aplica el mismo patrón de filtro controlado que el resto de pantallas de
 * listado: el estado del filtro vive en el ViewModel y la lista mostrada se
 * deriva de él.
 */
class ImpactoViewModel(
    private val repositorio: ContenidoRepository = ContenidoRepository()
) : ViewModel() {

    private val _tipoSeleccionado = MutableStateFlow<TipoCaso?>(null)

    /** Tipo de caso activo; `null` significa "todos". */
    val tipoSeleccionado: StateFlow<TipoCaso?> = _tipoSeleccionado.asStateFlow()

    /** Casos de impacto que cumplen el filtro activo. */
    val casos: StateFlow<List<CasoImpacto>> =
        _tipoSeleccionado
            .map { tipo ->
                repositorio.casos().filter { tipo == null || it.tipo == tipo }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = repositorio.casos()
            )

    /** Todos los tipos disponibles para construir los filtros. */
    val tipos: List<TipoCaso> = TipoCaso.entries

    /** Selecciona un tipo de caso; volver a tocarlo desactiva el filtro. */
    fun filtrarPor(tipo: TipoCaso?) {
        _tipoSeleccionado.value =
            if (_tipoSeleccionado.value == tipo) null else tipo
    }
}
