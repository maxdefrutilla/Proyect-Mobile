package com.example.proyecto_corpaliv

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.example.proyecto_corpaliv.viewmodel.AuthViewModel
import com.example.proyecto_corpaliv.viewmodel.SolicitudViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ViewModelsValidationTest {

    private lateinit var application: Application
    private lateinit var solicitudViewModel: SolicitudViewModel
    private lateinit var authViewModel: AuthViewModel

    @Before
    fun setUp() {
        application = ApplicationProvider.getApplicationContext()
        solicitudViewModel = SolicitudViewModel(application)
        authViewModel = AuthViewModel(application)
    }

    // =========================================================================
    // PRUEBAS UNITARIAS: SolicitudViewModel (Validaciones del formulario)
    // =========================================================================

    @Test
    fun `validarFormulario con campos vacios retorna false y asigna mensajes de error`() {
        val esValido = solicitudViewModel.validarFormulario()

        assertFalse("El formulario vacío no debe ser válido", esValido)
        assertEquals("El nombre de la empresa es obligatorio", solicitudViewModel.nombreError.value)
        assertEquals("El email es obligatorio", solicitudViewModel.emailError.value)
        assertEquals("El teléfono de contacto es obligatorio", solicitudViewModel.telefonoError.value)
        assertNotNull("Debe mostrar error de cantidad", solicitudViewModel.cantidadError.value)
        assertEquals("Seleccione una fecha para la reunion", solicitudViewModel.fechaError.value)
        assertEquals("Debes aceptar los términos y condiciones", solicitudViewModel.terminosError.value)
    }

    @Test
    fun `validarFormulario con nombre menor a 3 caracteres falla`() {
        solicitudViewModel.onNombreChange("AB")
        solicitudViewModel.onEmailChange("contacto@empresa.cl")
        solicitudViewModel.onTelefonoChange("912345678")
        solicitudViewModel.onCantidadChange("5")
        solicitudViewModel.onFechaChange("2026-12-01")
        solicitudViewModel.onAceptaTerminosChange(true)

        val esValido = solicitudViewModel.validarFormulario()

        assertFalse(esValido)
        assertEquals(
            "El nombre de la empresa debe tener al menos 3 caracteres",
            solicitudViewModel.nombreError.value
        )
    }

    @Test
    fun `validarFormulario con email invalido falla`() {
        solicitudViewModel.onNombreChange("Empresa Inclusiva SpA")
        solicitudViewModel.onEmailChange("correo-sin-arroba")
        solicitudViewModel.onTelefonoChange("912345678")
        solicitudViewModel.onCantidadChange("10")
        solicitudViewModel.onFechaChange("2026-12-01")
        solicitudViewModel.onAceptaTerminosChange(true)

        val esValido = solicitudViewModel.validarFormulario()

        assertFalse(esValido)
        assertEquals(
            "Formato de correo no válido (ej: Contact@dominio.cl)",
            solicitudViewModel.emailError.value
        )
    }

    @Test
    fun `validarFormulario con telefono corto o cantidad cero falla`() {
        solicitudViewModel.onNombreChange("Empresa Inclusiva SpA")
        solicitudViewModel.onEmailChange("contacto@empresa.cl")
        solicitudViewModel.onTelefonoChange("1234")
        solicitudViewModel.onCantidadChange("0")
        solicitudViewModel.onFechaChange("2026-12-01")
        solicitudViewModel.onAceptaTerminosChange(true)

        val esValido = solicitudViewModel.validarFormulario()

        assertFalse(esValido)
        assertEquals(
            "El teléfono de contacto debe tener al menos 9 caracteres",
            solicitudViewModel.telefonoError.value
        )
        assertEquals(
            "Ingresa una cantidad válida de trabajadores (mayor a 0)",
            solicitudViewModel.cantidadError.value
        )
    }

    @Test
    fun `validarFormulario con todos los datos validos retorna true y sin errores`() {
        solicitudViewModel.onNombreChange("Fundación Corpaliv Aliados")
        solicitudViewModel.onEmailChange("alianzas@corpaliv.cl")
        solicitudViewModel.onTelefonoChange("+56987654321")
        solicitudViewModel.onCantidadChange("15")
        solicitudViewModel.onFechaChange("2026-12-10")
        solicitudViewModel.onRequiereCapacitacionChange(true)
        solicitudViewModel.onAceptaTerminosChange(true)

        val esValido = solicitudViewModel.validarFormulario()

        assertTrue("El formulario con datos correctos debe ser válido", esValido)
        assertNull(solicitudViewModel.nombreError.value)
        assertNull(solicitudViewModel.emailError.value)
        assertNull(solicitudViewModel.telefonoError.value)
        assertNull(solicitudViewModel.cantidadError.value)
        assertNull(solicitudViewModel.fechaError.value)
        assertNull(solicitudViewModel.terminosError.value)
    }

    // =========================================================================
    // PRUEBAS UNITARIAS: AuthViewModel (Validaciones de Login y Registro)
    // =========================================================================

    @Test
    fun `iniciarSesion con email vacio asigna error esperado`() {
        authViewModel.onEmailChange("")
        authViewModel.onPasswordChange("123456")

        authViewModel.iniciarSesion()

        assertEquals("Ingresa tu correo electrónico", authViewModel.error.value)
    }

    @Test
    fun `iniciarSesion con formato de correo invalido asigna error`() {
        authViewModel.onEmailChange("usuario_invalido")
        authViewModel.onPasswordChange("123456")

        authViewModel.iniciarSesion()

        assertEquals("Formato de correo no válido", authViewModel.error.value)
    }

    @Test
    fun `registrar con password menor a 6 caracteres asigna error`() {
        authViewModel.alternarModo(true)
        authViewModel.onEmailChange("nuevo@corpaliv.cl")
        authViewModel.onPasswordChange("123")

        authViewModel.registrar()

        assertEquals("La contraseña debe tener al menos 6 caracteres", authViewModel.error.value)
    }
}