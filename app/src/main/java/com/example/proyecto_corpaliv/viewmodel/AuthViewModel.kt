package com.example.proyecto_corpaliv.viewmodel

import android.app.Application
import android.content.Context
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_corpaliv.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencias = application.getSharedPreferences("corpaliv_auth", Context.MODE_PRIVATE)
    private val repository = AuthRepository()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _modoRegistro = MutableStateFlow(false)
    val modoRegistro: StateFlow<Boolean> = _modoRegistro.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    private val _token = MutableStateFlow(preferencias.getString(CLAVE_TOKEN, null))
    val token: StateFlow<String?> = _token.asStateFlow()

    fun onEmailChange(valor: String) {
        _email.value = valor
        limpiarMensajes()
    }

    fun onPasswordChange(valor: String) {
        _password.value = valor
        limpiarMensajes()
    }

    fun alternarModo(registro: Boolean) {
        _modoRegistro.value = registro
        limpiarMensajes()
    }

    /** POST /auth/login. Guarda el JWT en SharedPreferences si el servidor responde "ok". */
    fun iniciarSesion() {
        if (!validarCampos()) return

        viewModelScope.launch {
            _cargando.value = true
            limpiarMensajes()
            try {
                val respuesta = repository.login(_email.value.trim(), _password.value)
                val tokenRecibido = respuesta.token

                if (respuesta.status.equals("ok", ignoreCase = true) && !tokenRecibido.isNullOrBlank()) {
                    preferencias.edit().putString(CLAVE_TOKEN, tokenRecibido).apply()
                    _token.value = tokenRecibido
                    _mensaje.value = "Sesión iniciada correctamente"
                    _password.value = ""
                } else {
                    _error.value = "Credenciales inválidas"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "No se pudo conectar con el servidor"
            } finally {
                _cargando.value = false
            }
        }
    }

    /** POST /auth/register. Al tener éxito vuelve al modo login. */
    fun registrar() {
        if (!validarCampos()) return

        viewModelScope.launch {
            _cargando.value = true
            limpiarMensajes()
            try {
                val respuesta = repository.registrar(_email.value.trim(), _password.value)
                _mensaje.value = respuesta.message
                _modoRegistro.value = false
                _password.value = ""
            } catch (e: Exception) {
                _error.value = e.message ?: "No se pudo registrar el usuario"
            } finally {
                _cargando.value = false
            }
        }
    }

    fun cerrarSesion() {
        preferencias.edit().remove(CLAVE_TOKEN).apply()
        _token.value = null
        _password.value = ""
        _mensaje.value = "Sesión cerrada"
    }

    fun limpiarMensajes() {
        _error.value = null
        _mensaje.value = null
    }

    private fun validarCampos(): Boolean {
        when {
            _email.value.isBlank() -> {
                _error.value = "Ingresa tu correo electrónico"
                return false
            }
            !Patterns.EMAIL_ADDRESS.matcher(_email.value.trim()).matches() -> {
                _error.value = "Formato de correo no válido"
                return false
            }
            _password.value.length < 6 -> {
                _error.value = "La contraseña debe tener al menos 6 caracteres"
                return false
            }
            else -> return true
        }
    }

    private companion object {
        const val CLAVE_TOKEN = "jwt_token"
    }
}
