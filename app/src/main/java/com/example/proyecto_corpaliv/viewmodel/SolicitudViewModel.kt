package com.example.proyecto_corpaliv.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_corpaliv.data.local.AppDatabase
import com.example.proyecto_corpaliv.data.model.SolicitudAlianza
import com.example.proyecto_corpaliv.data.remote.dto.SolicitudDto
import com.example.proyecto_corpaliv.data.repository.SolicitudRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SolicitudViewModel(application: Application) : AndroidViewModel(application)  {
    private val repository: SolicitudRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = SolicitudRepository(database.solicitudDao())
    }

    // --- Listado que viene del microservicio ---

    private val _listaSolicitudes = MutableStateFlow<List<SolicitudDto>>(emptyList())
    val listaSolicitudes: StateFlow<List<SolicitudDto>> = _listaSolicitudes.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // --- Estado del formulario ---

    private val _nombreEmpresa = MutableStateFlow("")
    val nombreEmpresa: StateFlow<String> = _nombreEmpresa.asStateFlow()

    private val _emailContacto = MutableStateFlow("")
    val emailContacto: StateFlow<String> = _emailContacto.asStateFlow()

    private val _telefono = MutableStateFlow("")
    val telefono: StateFlow<String> = _telefono.asStateFlow()

    private val _cantidadTrabajadores = MutableStateFlow("")
    val cantidadTrabajadores: StateFlow<String> = _cantidadTrabajadores.asStateFlow()

    private val _fechaReunion = MutableStateFlow("")
    val fechaReunion: StateFlow<String> = _fechaReunion.asStateFlow()

    private val _requiereCapacitacion = MutableStateFlow(false)
    val requiereCapacitacion: StateFlow<Boolean> = _requiereCapacitacion.asStateFlow()

    private val _aceptaTerminos = MutableStateFlow(false)
    val aceptaTerminos: StateFlow<Boolean> = _aceptaTerminos.asStateFlow()

    // Validacion de errores
    private val _nombreError = MutableStateFlow<String?>(null)
    val nombreError: StateFlow<String?> = _nombreError.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _telefonoError = MutableStateFlow<String?>(null)
    val telefonoError: StateFlow<String?> = _telefonoError.asStateFlow()

    private val _cantidadError = MutableStateFlow<String?>(null)
    val cantidadError: StateFlow<String?> = _cantidadError.asStateFlow()

    private val _fechaError = MutableStateFlow<String?>(null)
    val fechaError: StateFlow<String?> = _fechaError.asStateFlow()

    private val _terminosError = MutableStateFlow<String?>(null)
    val terminosError: StateFlow<String?> = _terminosError.asStateFlow()

    // Envio exitoso
    private val _guardadoExitoso = MutableStateFlow(false)
    val guardadoExitoso: StateFlow<Boolean> = _guardadoExitoso.asStateFlow()

    // Actualizacion de valores
    fun onNombreChange(valor: String) {
        _nombreEmpresa.value = valor
        if (_nombreError.value != null) _nombreError.value = null
    }

    fun onEmailChange(valor: String) {
        _emailContacto.value = valor
        if (_emailError.value != null) _emailError.value = null
    }

    fun onTelefonoChange(valor: String) {
        _telefono.value = valor
        if (_telefonoError.value != null) _telefonoError.value = null
    }

    fun onCantidadChange(valor: String) {
        _cantidadTrabajadores.value = valor
        if (_cantidadError.value != null) _cantidadError.value = null
    }

    fun onFechaChange(valor: String) {
        _fechaReunion.value = valor
        if (_fechaError.value != null) _fechaError.value = null
    }

    fun onRequiereCapacitacionChange(valor: Boolean) {
        _requiereCapacitacion.value = valor
    }

    fun onAceptaTerminosChange(valor: Boolean) {
        _aceptaTerminos.value = valor
        if (_terminosError.value != null) _terminosError.value = null
    }

    // Logica de las validaciones
    fun validarFormulario(): Boolean {
        var esValido = true

        if (_nombreEmpresa.value.trim().isBlank()) {
            _nombreError.value = "El nombre de la empresa es obligatorio"
            esValido = false
        } else if (_nombreEmpresa.value.trim().length < 3) {
            _nombreError.value = "El nombre de la empresa debe tener al menos 3 caracteres"
            esValido = false
        }

        if (_emailContacto.value.trim().isBlank()) {
            _emailError.value = "El email es obligatorio"
            esValido = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(_emailContacto.value.trim()).matches()) {
            _emailError.value = "Formato de correo no válido (ej: Contact@dominio.cl)"
            esValido = false
        }

        if (_telefono.value.trim().isBlank()) {
            _telefonoError.value = "El teléfono de contacto es obligatorio"
            esValido = false
        } else if (_telefono.value.trim().length < 9) {
            _telefonoError.value = "El teléfono de contacto debe tener al menos 9 caracteres"
            esValido = false
        }

        val cantidad = _cantidadTrabajadores.value.toIntOrNull()
        if (cantidad == null || cantidad <= 0) {
            _cantidadError.value = "Ingresa una cantidad válida de trabajadores (mayor a 0)"
            esValido = false
        }

        if (_fechaReunion.value.isBlank()) {
            _fechaError.value = "Seleccione una fecha para la reunion"
            esValido = false
        }

        if (!_aceptaTerminos.value) {
            _terminosError.value = "Debes aceptar los términos y condiciones"
            esValido = false
        }

        return esValido
    }

    /**
     * Valida el formulario, envía un POST al microservicio y guarda una copia en Room.
     * Si el servidor no responde, la solicitud queda guardada localmente como respaldo.
     */
    fun guardarSolicitud() {
        if (!validarFormulario()) return

        viewModelScope.launch {
            _error.value = null

            val solicitud = SolicitudDto(
                nombreEmpresa = _nombreEmpresa.value.trim(),
                emailContacto = _emailContacto.value.trim(),
                telefono = _telefono.value.trim(),
                cantidadTrabajadores = _cantidadTrabajadores.value.toInt(),
                fechaReunion = _fechaReunion.value,
                requiereCapacitacion = _requiereCapacitacion.value,
                aceptaTerminos = _aceptaTerminos.value,
                estado = "PENDIENTE"
            )

            try {
                repository.crearRemota(solicitud)
                guardarCopiaLocal(solicitud)
                _guardadoExitoso.value = true
                limpiarFormulario()
            } catch (e: Exception) {
                guardarCopiaLocal(solicitud)
                _error.value = "No se pudo conectar con el servidor. " +
                    "La solicitud quedó guardada en el dispositivo."
            }
        }
    }

    private suspend fun guardarCopiaLocal(solicitud: SolicitudDto) {
        repository.insertar(
            SolicitudAlianza(
                nombreEmpresa = solicitud.nombreEmpresa,
                emailContacto = solicitud.emailContacto,
                telefono = solicitud.telefono,
                cantidadTrabajadores = solicitud.cantidadTrabajadores,
                fechaReunion = solicitud.fechaReunion,
                requiereCapacitacion = solicitud.requiereCapacitacion,
                aceptaTerminos = solicitud.aceptaTerminos
            )
        )
    }

    // --- Operaciones de red para el listado ---

    /** GET /api/solicitudes: recarga la lista desde el microservicio. */
    fun cargarSolicitudes() {
        viewModelScope.launch { refrescar() }
    }

    private suspend fun refrescar() {
        _cargando.value = true
        _error.value = null
        try {
            _listaSolicitudes.value = repository.listarRemotas()
        } catch (e: Exception) {
            _error.value = e.message ?: "Error desconocido al cargar las solicitudes"
        } finally {
            _cargando.value = false
        }
    }

    /** PUT /api/solicitudes/{id}: cambia el estado y vuelve a consultar el listado. */
    fun cambiarEstado(solicitud: SolicitudDto, nuevoEstado: String) {
        val id = solicitud.id ?: return
        viewModelScope.launch {
            try {
                repository.actualizarRemota(id, solicitud.copy(estado = nuevoEstado))
                refrescar()
            } catch (e: Exception) {
                _error.value = e.message ?: "No se pudo actualizar el estado"
                _cargando.value = false
            }
        }
    }

    /** DELETE /api/solicitudes/{id}: elimina en el servidor y vuelve a consultar el listado. */
    fun eliminarSolicitud(solicitud: SolicitudDto) {
        val id = solicitud.id ?: return
        viewModelScope.launch {
            try {
                repository.eliminarRemota(id)
                refrescar()
            } catch (e: Exception) {
                _error.value = e.message ?: "No se pudo eliminar la solicitud"
                _cargando.value = false
            }
        }
    }

    fun limpiarError() {
        _error.value = null
    }

    fun reiniciarEstadoGuardado() {
        _guardadoExitoso.value = false
    }

    private fun limpiarFormulario() {
        _nombreEmpresa.value = ""
        _emailContacto.value = ""
        _telefono.value = ""
        _cantidadTrabajadores.value = ""
        _fechaReunion.value = ""
        _requiereCapacitacion.value = false
        _aceptaTerminos.value = false
    }
}
