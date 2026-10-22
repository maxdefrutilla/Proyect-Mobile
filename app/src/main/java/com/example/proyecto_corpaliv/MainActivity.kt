package com.example.proyecto_corpaliv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.proyecto_corpaliv.ui.navigation.CorpalivApp
import com.example.proyecto_corpaliv.ui.theme.Proyecto_CorpalivTheme

/**
 * Punto de entrada de la aplicación.
 *
 * La actividad solo monta el tema institucional y el contenedor de navegación;
 * toda la lógica de pantallas vive en `ui/navigation` y `ui/screens`.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Proyecto_CorpalivTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CorpalivApp()
                }
            }
        }
    }
}
