package com.example.proyecto_corpaliv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_corpaliv.data.content.CategoriaPrograma
import com.example.proyecto_corpaliv.data.content.ContenidoRepository
import com.example.proyecto_corpaliv.data.content.ProgramaInclusional
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel de la pantalla de programas.
 *
 * Controla el filtro por categoría mediante un `StateFlow` y expone la lista
 * ya filtrada. Al vivir el filtro en el ViewModel, la vista no guarda estado
 * y la lógica puede probarse sin depender de la interfaz.
 */
class ProgramasViewModel(
    private val repositorio: ContenidoRepository = ContenidoRepository()
) : ViewModel() {

    private val _categoriaSeleccionada = MutableStateFlow<CategoriaPrograma?>(null)

    /** Categoría activa; `null` significa "todas". */
    val categoriaSeleccionada: StateFlow<CategoriaPrograma?> = _categoriaSeleccionada.asStateFlow()

    /** Programas que cumplen el filtro activo. */
    val programas: StateFlow<List<ProgramaInclusional>> =
        _categoriaSeleccionada
            .map { categoria ->
                repositorio.programas().filter { categoria == null || it.categoria == categoria }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = repositorio.programas()
            )

    /** Todas las categorías disponibles para construir los filtros. */
    val categorias: List<CategoriaPrograma> = CategoriaPrograma.entries

    /** Selecciona una categoría; volver a tocarla desactiva el filtro. */
    fun filtrarPor(categoria: CategoriaPrograma?) {
        _categoriaSeleccionada.value =
            if (_categoriaSeleccionada.value == categoria) null else categoria
    }
}
