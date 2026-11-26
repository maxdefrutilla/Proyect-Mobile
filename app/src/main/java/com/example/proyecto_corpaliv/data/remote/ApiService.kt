package com.example.proyecto_corpaliv.data.remote

import com.example.proyecto_corpaliv.data.remote.dto.LoginRequest
import com.example.proyecto_corpaliv.data.remote.dto.LoginResponse
import com.example.proyecto_corpaliv.data.remote.dto.ProductoDto
import com.example.proyecto_corpaliv.data.remote.dto.RegisterResponse
import com.example.proyecto_corpaliv.data.remote.dto.SolicitudDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // --- Solicitudes de alianza (alianza-service) ---

    @GET("api/solicitudes")
    suspend fun getSolicitudes(): Response<List<SolicitudDto>>

    @GET("api/solicitudes/{id}")
    suspend fun getSolicitud(@Path("id") id: Long): Response<SolicitudDto>

    @POST("api/solicitudes")
    suspend fun crearSolicitud(@Body solicitud: SolicitudDto): Response<SolicitudDto>

    @PUT("api/solicitudes/{id}")
    suspend fun actualizarSolicitud(
        @Path("id") id: Long,
        @Body solicitud: SolicitudDto
    ): Response<SolicitudDto>

    @DELETE("api/solicitudes/{id}")
    suspend fun eliminarSolicitud(@Path("id") id: Long): Response<ResponseBody>

    // --- Catálogo de productos (catalogo-service) ---

    @GET("api/productos")
    suspend fun getProductos(
        @Query("soloActivos") soloActivos: Boolean = false
    ): Response<List<ProductoDto>>

    @GET("api/productos/{id}")
    suspend fun getProducto(@Path("id") id: Long): Response<ProductoDto>

    @POST("api/productos")
    suspend fun crearProducto(@Body producto: ProductoDto): Response<ProductoDto>

    @PUT("api/productos/{id}")
    suspend fun actualizarProducto(
        @Path("id") id: Long,
        @Body producto: ProductoDto
    ): Response<ProductoDto>

    @DELETE("api/productos/{id}")
    suspend fun eliminarProducto(@Path("id") id: Long): Response<ResponseBody>

    // --- Autenticación (auth-service) ---

    @POST("auth/login")
    suspend fun login(@Body credenciales: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body credenciales: LoginRequest): Response<RegisterResponse>
}
