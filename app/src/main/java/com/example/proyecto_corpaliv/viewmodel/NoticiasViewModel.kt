package com.example.proyecto_corpaliv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_corpaliv.data.remote.NoticiasApiService
import com.example.proyecto_corpaliv.data.remote.dto.NoticiaExternaDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoticiasViewModel (

    private val api: NoticiasApiService = NoticiasApiService.instance

) : ViewModel() {

    private val _noticias = MutableStateFlow<List<NoticiaExternaDto>>(emptyList())
    val noticias: StateFlow<List<NoticiaExternaDto>> = _noticias.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        cargarNoticias()
    }

    fun cargarNoticias() {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null
            try {
                val response = api.getNoticias()
                if (response.isSuccessful) {
                    _noticias.value = response.body().orEmpty()
                } else {
                    _error.value = "Error al obtener comunicados: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "No se pudieron cargar los comunicados externos"
            } finally {
                _cargando.value = false

            }

        }
    }
}