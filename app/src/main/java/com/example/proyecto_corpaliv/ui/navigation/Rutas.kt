package com.example.proyecto_corpaliv.ui.navigation

object Rutas {

    // Destinos principales (visibles en la barra inferior)
    const val INICIO = "inicio"
    const val PROGRAMAS = "programas"
    const val IMPACTO = "impacto"
    const val CATALOGO = "catalogo"
    const val CONTACTO = "contacto"
    const val SOLICITUDES = "solicitudes"
    const val PERFIL = "perfil"

    // Destinos de detalle (con botón de retroceso en la barra superior)
    const val DETALLE_PROGRAMA = "detalle/programa/{programaId}"
    const val DETALLE_CASO = "detalle/caso/{casoId}"
    const val DETALLE_OFERTA = "detalle/oferta/{ofertaId}"

    /** Construye la ruta de detalle de un programa de inclusión. */
    fun detallePrograma(id: String): String = "detalle/programa/$id"

    /** Construye la ruta de detalle de un caso de impacto. */
    fun detalleCaso(id: String): String = "detalle/caso/$id"

    /** Construye la ruta de detalle de una oferta del catálogo. */
    fun detalleOferta(id: String): String = "detalle/oferta/$id"

    /** Indica si la ruta corresponde a una pantalla de detalle. */
    fun esDetalle(ruta: String?): Boolean = ruta?.startsWith("detalle/") == true
}
