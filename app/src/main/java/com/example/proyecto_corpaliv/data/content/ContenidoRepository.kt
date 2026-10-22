package com.example.proyecto_corpaliv.data.content

/**
 * Repositorio de contenido institucional.
 *
 * Entrega la información desde una fuente local, de modo que las pantallas
 * siempre tengan datos disponibles ("disponibilidad constante") y nunca
 * puedan quedar en estado vacío o de carga.
 *
 * La interfaz es idéntica a la de un repositorio con API REST: los ViewModel
 * no saben de dónde vienen los datos, solo piden y reciben listas de modelos.
 * De ese modo, reemplazar la fuente local por un cliente HTTP en el futuro no
 * obliga a modificar la capa de presentación.
 */
class ContenidoRepository {

    /** Datos institucionales: propósito, misión, visión e indicadores. */
    fun indicadores(): List<IndicadorInstitucional> = ContenidoInstitucional.INDICADORES

    /** Programas de inclusión laboral. */
    fun programas(): List<ProgramaInclusional> = ContenidoInstitucional.PROGRAMAS

    /** Casos de impacto publicados. */
    fun casos(): List<CasoImpacto> = ContenidoInstitucional.CASOS

    /** Catálogo de productos, servicios e iniciativas RSE. */
    fun catalogo(): List<OfertaCatalogo> = ContenidoInstitucional.CATALOGO

    /** Busca un programa por su identificador. */
    fun programa(id: String): ProgramaInclusional? = programas().firstOrNull { it.id == id }

    /** Busca un caso de impacto por su identificador. */
    fun caso(id: String): CasoImpacto? = casos().firstOrNull { it.id == id }

    /** Busca una oferta del catálogo por su identificador. */
    fun oferta(id: String): OfertaCatalogo? = catalogo().firstOrNull { it.id == id }

    /** Propósito institucional. */
    val proposito: String get() = ContenidoInstitucional.PROPOSITO

    /** Misión institucional. */
    val mision: String get() = ContenidoInstitucional.MISION

    /** Visión institucional. */
    val vision: String get() = ContenidoInstitucional.VISION

    /** Nombre de la organización. */
    val organizacion: String get() = ContenidoInstitucional.NOMBRE_ORGANIZACION

    /** Nombre de la alianza que acompaña el programa. */
    val alianza: String get() = ContenidoInstitucional.NOMBRE_ALIANZA
}
