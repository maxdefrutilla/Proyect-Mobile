package com.example.proyecto_corpaliv.data.remote.dto

/**
 * Espejo de cl.duoc.corpaliv.catalogo.Model.ProductoModel (backend).
 * Ojo: el backend usa nombres con sufijo "Producto", no los del README.
 */
data class ProductoDto(
    val productoId: Long? = null,
    val nombreProducto: String,
    val descripcionProducto: String,
    val precioProducto: Double,
    val stockProducto: Int,
    val categoriaProducto: String,
    val activo: Boolean = true
)
