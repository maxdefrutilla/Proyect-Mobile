# Proyecto Corpaliv - Alba Lab

Aplicación móvil Android (Kotlin + Jetpack Compose) desarrollada para el caso **CORPALIV / Alba Lab (DSY1105)**. Su objetivo es presentar la labor institucional de inclusión social y laboral, los programas, los casos de impacto y el catálogo de productos, servicios e iniciativas RSE, además de permitir registrar solicitudes de vinculación empresarial.

## Tecnologías
- **Kotlin** y **Jetpack Compose** (Material Design 3)
- **Navigation Compose** (`androidx.navigation:navigation-compose:2.10.2`)
- **Room** (`room-runtime` / `room-ktx` / `room-compiler` con KSP) para persistencia local
- **ViewModel + StateFlow** con arquitectura MVVM
- **Material Icons Extended** para iconografía
- **Edge-to-Edge** con `enableEdgeToEdge()`

## Funcionalidades implementadas
- **Inicio**: Propósito, Misión, Visión, indicadores institucionales, programas destacados y modalidades de colaboración.
- **Programas**: Listado con filtros por categoría (Formación / Preparación para el empleo / Producción / Sensibilización), detalle de cada programa con objetivos y botón de contacto.
- **Impacto**: Casos de impacto con filtros por tipo (Inserción laboral / Donación / Voluntariado / Capacitación). Cada caso muestra relato, indicadores y aviso de datos ficticios.
- **Catálogo**: Grilla adaptativa con búsqueda por texto y filtros por tipo (Productos / Servicios / Iniciativas RSE). Detalle de cada oferta con precio y llamada a acción.
- **Vinculación empresarial**: Formulario con validaciones visuales por campo, iconos de error, validación de términos y condiciones, selector de fecha, switch de capacitaciones RSE. Al enviar, se guarda en **Room** y se muestra un `Toast` de confirmación.
- **Solicitudes enviadas**: Pantalla que lee el `StateFlow` `listaSolicitudes` del `SolicitudViewModel` y muestra los registros guardados en la BD local.
- **Navegación**: Un único `Scaffold` con `TopAppBar` y barra inferior. Transiciones animadas entre destinos. Rutas centralizadas.
- **Diseño**: Tema institucional CORPALIV (colores accesibles), componentes reutilizables, animaciones con `AnimatedVisibility` / `AnimatedContent` y diseño inclusivo.

## Estructura relevante
```text
app/src/main/java/com/example/proyecto_corpaliv/
├── data/content/      # Modelos, contenido estático ficticio y repositorio
├── data/local/        # Room (Database, DAO)
├── data/model/        # Entidades de Room
├── data/repository/   # Repositorio de solicitudes
├── ui/components/     # Componentes reutilizables y mapeo de iconos
├── ui/navigation/     # Rutas, NavHost, barra de navegación
├── ui/screens/        # Pantallas (inicio, programas, impacto, catálogo, formulario, solicitudes)
├── ui/theme/          # Colores, tema y tipografía
└── viewmodel/         # ViewModels con StateFlow
```

## Requisitos del caso
- **Datos 100% ficticios**: todos los programas, casos, productos y solicitudes son sintéticos. Se muestra un aviso permanente en las secciones de contenido.
- **Persistencia local**: las solicitudes se guardan con Room.
- **Sin integración REST**: el contenido institucional se sirve desde una fuente local con forma compatible a una futura API.

## Cómo ejecutar
1. Abrir el proyecto en Android Studio (o usar Gradle).
2. Sincronizar Gradle.
3. Ejecutar en emulador o dispositivo físico (API 24+).
4. Navegar por las pestañas e ir al formulario de vinculación para guardar solicitudes; luego abrir **"Ver solicitudes enviadas"** para comprobar la persistencia.

## Autores
- Tomás Mora
- Joakin Aenishanslins
- Florencia

## Repositorio
[https://github.com/maxdefrutilla/Proyect-Mobile](https://github.com/maxdefrutilla/Proyect-Mobile)
