package com.example.proyecto_corpaliv.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/** Esquema de color claro con la identidad institucional de CORPALIV. */
private val EsquemaClaro = lightColorScheme(
    primary = Verde40,
    onPrimary = Color.White,
    primaryContainer = Verde90,
    onPrimaryContainer = Verde10,
    secondary = Azul40,
    onSecondary = Color.White,
    secondaryContainer = Azul90,
    onSecondaryContainer = Azul10,
    tertiary = Naranja40,
    onTertiary = Color.White,
    tertiaryContainer = Naranja90,
    onTertiaryContainer = Naranja10,
    error = Error40,
    onError = Color.White,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,
    background = Neutro99,
    onBackground = Neutro10,
    surface = Neutro99,
    onSurface = Neutro10,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    surfaceContainer = Neutro95,
    surfaceContainerHigh = Neutro95,
    surfaceContainerHighest = SurfaceVariantLight,
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight,
    inverseSurface = InverseSurfaceLight,
    inverseOnSurface = InverseOnSurfaceLight,
    inversePrimary = Verde80
)

/** Esquema de color oscuro con la identidad institucional de CORPALIV. */
private val EsquemaOscuro = darkColorScheme(
    primary = Verde80,
    onPrimary = Verde20,
    primaryContainer = Verde20,
    onPrimaryContainer = Verde90,
    secondary = Azul80,
    onSecondary = Azul20,
    secondaryContainer = Azul20,
    onSecondaryContainer = Azul90,
    tertiary = Naranja80,
    onTertiary = Naranja20,
    tertiaryContainer = Naranja20,
    onTertiaryContainer = Naranja90,
    error = Error80,
    onError = OnErrorOscuro,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,
    background = FondoOscuro,
    onBackground = Neutro90,
    surface = FondoOscuro,
    onSurface = Neutro90,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    surfaceContainer = ContenedorOscuro,
    surfaceContainerHigh = ContenedorAltoOscuro,
    surfaceContainerHighest = SurfaceVariantDark,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    inverseSurface = Neutro90,
    inverseOnSurface = Neutro20,
    inversePrimary = Verde40
)

/**
 * Tema de la aplicación.
 *
 * El color dinámico viene desactivado a propósito: la identidad visual de
 * CORPALIV / Alba Lab debe verse igual en cualquier dispositivo, tanto en las
 * pantallas del caso evaluadas como en la defensa.
 */
@Composable
fun Proyecto_CorpalivTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> EsquemaOscuro
        else -> EsquemaClaro
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
