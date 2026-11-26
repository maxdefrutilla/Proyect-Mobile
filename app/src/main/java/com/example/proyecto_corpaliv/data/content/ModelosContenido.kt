package com.example.proyecto_corpaliv.data.content

/**
 * Categorías de los programas de inclusión laboral que ofrece CORPALIV / Alba Lab.
 * Se mantienen como enum para que la capa de datos no dependa de la UI.
 */
enum class CategoriaPrograma(val etiqueta: String) {
    FORMACION("Formación"),
    EMPLEO("Preparación para el empleo"),
    PRODUCCION("Producción"),
    SENSIBILIZACION("Sensibilización")
}

/** Tipos de oferta disponibles en el catálogo institucional. */
enum class TipoOferta(val etiqueta: String) {
    PRODUCTO("Productos"),
    SERVICIO("Servicios"),
    INICIATIVA_RSE("Iniciativas RSE")
}

/** Tipos de caso de impacto publicados en la sección de impacto. */
enum class TipoCaso(val etiqueta: String) {
    INSERCION("Inserción laboral"),
    DONACION("Donación"),
    VOLUNTARIADO("Voluntariado"),
    CAPACITACION("Capacitación")
}

/** Programa de inclusión laboral ofrecidos por la organización. */
data class ProgramaInclusional(
    val id: String,
    val nombre: String,
    val categoria: CategoriaPrograma,
    val resumen: String,
    val descripcion: String,
    val duracion: String,
    val participantes: Int,
    val modalidad: String,
    val objetivos: List<String>
)

/** Caso de éxito o de impacto social, con datos 100% ficticios y anonimizados. */
data class CasoImpacto(
    val id: String,
    val titulo: String,
    val tipo: TipoCaso,
    val resumen: String,
    val relato: String,
    val indicadores: List<Indicador>,
    val fecha: String
)

/** Indicador cuantitativo asociado a un caso de impacto. */
data class Indicador(
    val valor: String,
    val etiqueta: String
)

/** Producto, servicio o iniciativa RSE del catálogo. */
data class OfertaCatalogo(
    val id: String,
    val nombre: String,
    val tipo: TipoOferta,
    val descripcion: String,
    val detalle: String,
    val precio: String,
    val destacado: Boolean = false
)

/** Cifra destacada de impacto mostrada en la pantalla de inicio. */
data class IndicadorInstitucional(
    val valor: String,
    val etiqueta: String,
    val descripcion: String
)
