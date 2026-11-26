package com.example.proyecto_corpaliv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_corpaliv.data.content.ContenidoRepository
import com.example.proyecto_corpaliv.data.content.OfertaCatalogo
import com.example.proyecto_corpaliv.data.content.TipoOferta
import com.example.proyecto_corpaliv.data.remote.dto.ProductoDto
import com.example.proyecto_corpaliv.data.repository.CatalogoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

/**
 * ViewModel del catálogo institucional.
 *
 * Mantiene la búsqueda y el filtro por tipo, pero la fuente de datos es el
 * microservicio (GET /api/productos). Si la API no responde, se conserva el
 * catálogo local para que la pantalla nunca quede vacía.
 */
class CatalogoViewModel(
    private val repositorio: ContenidoRepository = ContenidoRepository(),
    private val apiRepositorio: CatalogoRepository = CatalogoRepository()
) : ViewModel() {

    private val _busqueda = MutableStateFlow("")
    private val _tipoSeleccionado = MutableStateFlow<TipoOferta?>(null)
    private val _ofertasRemotas = MutableStateFlow<List<OfertaCatalogo>>(emptyList())
    private val _cargando = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    /** Texto de búsqueda actual. */
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    /** Tipo de oferta activo; `null` significa "todos". */
    val tipoSeleccionado: StateFlow<TipoOferta?> = _tipoSeleccionado.asStateFlow()

    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()
    val error: StateFlow<String?> = _error.asStateFlow()

    /** Resultados de la búsqueda y el filtro combinados. */
    val resultados: StateFlow<List<OfertaCatalogo>> =
        combine(_busqueda, _tipoSeleccionado, _ofertasRemotas) { texto, tipo, remotas ->
            // Mientras no llegue la API (o si falla), se mantiene el catálogo local.
            val fuente = remotas.ifEmpty { repositorio.catalogo() }
            val normalizado = texto.trim().lowercase(Locale.getDefault())

            fuente.filter { oferta ->
                val coincideTipo = tipo == null || oferta.tipo == tipo
                val coincideTexto = normalizado.isEmpty() ||
                    oferta.nombre.lowercase(Locale.getDefault()).contains(normalizado) ||
                    oferta.descripcion.lowercase(Locale.getDefault()).contains(normalizado)
                coincideTipo && coincideTexto
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = repositorio.catalogo()
        )

    /** Todos los tipos disponibles para construir los filtros. */
    val tipos: List<TipoOferta> = TipoOferta.entries

    /** GET /api/productos: reemplaza el catálogo local por el del servidor. */
    fun cargarCatalogo() {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null
            try {
                _ofertasRemotas.value = apiRepositorio.listarProductos().map(::mapearProducto)
            } catch (e: Exception) {
                _error.value = e.message ?: "No se pudo cargar el catálogo desde el servidor"
            } finally {
                _cargando.value = false
            }
        }
    }

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

    fun limpiarError() {
        _error.value = null
    }

    /** Último recurso para el detalle si el producto ya no está en la lista remota. */
    fun ofertaFallback(id: String): OfertaCatalogo? = repositorio.oferta(id)

    private fun mapearProducto(producto: ProductoDto): OfertaCatalogo = OfertaCatalogo(
        id = producto.productoId?.toString().orEmpty(),
        nombre = producto.nombreProducto,
        tipo = tipoDe(producto.categoriaProducto),
        descripcion = producto.descripcionProducto,
        detalle = "Categoría: ${producto.categoriaProducto}. " +
            "Stock disponible: ${producto.stockProducto} unidades.",
        precio = formatearPrecio(producto.precioProducto),
        destacado = producto.activo && producto.stockProducto > 0
    )

    private fun tipoDe(categoria: String): TipoOferta {
        val valor = categoria.uppercase(Locale.getDefault())
        return when {
            valor.contains("SERVICIO") || valor.contains("ASESOR") -> TipoOferta.SERVICIO
            valor.contains("RSE") || valor.contains("INICIATIVA") ||
                valor.contains("VOLUNTAR") -> TipoOferta.INICIATIVA_RSE
            else -> TipoOferta.PRODUCTO
        }
    }

    private fun formatearPrecio(valor: Double): String =
        "$" + NumberFormat.getNumberInstance(Locale.forLanguageTag("es-CL")).format(valor)
}
