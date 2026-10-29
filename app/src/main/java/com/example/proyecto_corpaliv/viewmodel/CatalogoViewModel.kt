package com.example.proyecto_corpaliv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_corpaliv.data.content.ContenidoRepository
import com.example.proyecto_corpaliv.data.content.OfertaCatalogo
import com.example.proyecto_corpaliv.data.content.TipoOferta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel del catálogo institucional.
 *
 * Combina dos controles de estado —el texto de búsqueda y el tipo de oferta
 * seleccionado— para producir la lista final de resultados. Es el ejemplo
 * más completo de control de estado de la aplicación.
 */
class CatalogoViewModel(
    private val repositorio: ContenidoRepository = ContenidoRepository()
) : ViewModel() {

    private val _busqueda = MutableStateFlow("")
    private val _tipoSeleccionado = MutableStateFlow<TipoOferta?>(null)

    /** Texto de búsqueda actual. */
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    /** Tipo de oferta activo; `null` significa "todos". */
    val tipoSeleccionado: StateFlow<TipoOferta?> = _tipoSeleccionado.asStateFlow()

    /** Resultados de la búsqueda y el filtro combinados. */
    val resultados: StateFlow<List<OfertaCatalogo>> =
        combine(_busqueda, _tipoSeleccionado) { texto, tipo ->
            val normalizado = texto.trim().lowercase()
            repositorio.catalogo().filter { oferta ->
                val coincideTipo = tipo == null || oferta.tipo == tipo
                val coincideTexto = normalizado.isEmpty() ||
                    oferta.nombre.lowercase().contains(normalizado) ||
                    oferta.descripcion.lowercase().contains(normalizado)
                coincideTipo && coincideTexto
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = repositorio.catalogo()
        )

    /** Todos los tipos disponibles para construir los filtros. */
    val tipos: List<TipoOferta> = TipoOferta.entries

    /** Actualiza el texto de búsqueda. */
    fun buscar(texto: String) {
        _busqueda.value = texto
    }

    /** Selecciona un tipo de oferta; volver a tocarlo desactiva el filtro. */
    fun filtrarPor(tipo: TipoOferta?) {
        _tipoSeleccionado.value =
            if (_tipoSeleccionado.value == tipo) null else tipo
    }

    /** Limpia búsqueda y filtros. */
    fun limpiar() {
        _busqueda.value = ""
        _tipoSeleccionado.value = null
    }
}
