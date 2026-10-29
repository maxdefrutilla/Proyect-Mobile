package com.example.proyecto_corpaliv.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_corpaliv.data.local.AppDatabase
import com.example.proyecto_corpaliv.data.model.SolicitudAlianza
import com.example.proyecto_corpaliv.data.repository.SolicitudRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SolicitudViewModel(application: Application) : AndroidViewModel(application)  {
    private val repository: SolicitudRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = SolicitudRepository(database.solicitudDao())
    }

    val listaSolicitudes = repository.todasLasSolicitudes.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
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

    //Validacion de errores
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

    //Envio exitosos
    private val _guardadoExitoso = MutableStateFlow(false)
    val guardadoExitoso: StateFlow<Boolean> = _guardadoExitoso.asStateFlow()

    //Actualizacion de valores
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

    //Logica de las validaciones
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

    fun guardarSolicitud() {
        if (!validarFormulario()) return

        viewModelScope.launch {
            val nuevaSolicitud = SolicitudAlianza(
                nombreEmpresa = _nombreEmpresa.value,
                emailContacto = _emailContacto.value,
                telefono = _telefono.value,
                cantidadTrabajadores = _cantidadTrabajadores.value.toInt(),
                fechaReunion = _fechaReunion.value,
                requiereCapacitacion = _requiereCapacitacion.value,
                aceptaTerminos = _aceptaTerminos.value
            )

            repository.insertar(nuevaSolicitud)
            _guardadoExitoso.value = true
            limpiarFormulario()
        }
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