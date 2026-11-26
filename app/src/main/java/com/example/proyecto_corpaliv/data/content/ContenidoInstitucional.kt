package com.example.proyecto_corpaliv.data.content

/**
 * Fuente de contenido institucional de CORPALIV / Alba Lab.
 *
 * IMPORTANTE: todos los datos de este archivo son 100% FICTICIOS y sintéticos.
 * No corresponden a personas, empresas, beneficiarios ni casos reales, tal como
 * exige el caso DSY1105 (Protección de Datos).
 */
object ContenidoInstitucional {

    const val NOMBRE_ORGANIZACION = "CORPALIV"
    const val NOMBRE_ALIANZA = "Alba Lab"
    const val PROPOSITO =
        "Promovemos la autonomía, participación e inclusión social y laboral de personas " +
            "con discapacidad múltiple, junto a la Red Sociolaboral Alba Lab."
    const val MISION =
        "Acompañar a personas con discapacidad múltiple hacia la autonomía, la formación " +
            "y el trabajo, a través de la Red Sociolaboral Alba Lab."
    const val VISION =
        "Ser referente en la integración laboral de personas con discapacidad múltiple, " +
            "convirtiendo cada colaboración en impacto social medible."

    /** Indicadores institucionales (cifras ficticias). */
    val INDICADORES = listOf(
        IndicadorInstitucional(
            "1.240",
            "Personas acompañadas",
            "Histórico acumulado del programa de inclusión."
        ),
        IndicadorInstitucional(
            "86",
            "Empresas aliadas",
            "Organizaciones que colaboran con Alba Lab."
        ),
        IndicadorInstitucional(
            "32",
            "Programas activos",
            "Formación, empleo y producción en ejecución."
        ),
        IndicadorInstitucional(
            "74%",
            "Inserción laboral",
            "Participantes con contrato tras el proceso."
        )
    )

    /** Programas de inclusión laboral. */
    val PROGRAMAS = listOf(
        ProgramaInclusional(
            id = "prog-formacion-habilidades",
            nombre = "Taller de Habilidades Laborales",
            categoria = CategoriaPrograma.FORMACION,
            resumen = "Entrenamiento en habilidades blandas, comunicación y trabajo en equipo.",
            descripcion =
                "Programa de 60 horas que prepara a participantes en técnicas de comunicación, " +
                    "resolución de conflictos, puntualidad y trabajo colaborativo. Se dicta en " +
                    "modalidad grupal con acompañamiento individual y evaluación de progresos.",
            duracion = "60 horas",
            participantes = 48,
            modalidad = "Presencial y remota",
            objetivos = listOf(
                "Reforzar la comunicación en entornos laborales",
                "Practicar trabajo en equipo y resolución de problemas",
                "Entregar un certificado de participación",
                "Acompañar la transición hacia la siguiente etapa"
            )
        ),
        ProgramaInclusional(
            id = "prog-preparacion-empleo",
            nombre = "Preparación para el Empleo",
            categoria = CategoriaPrograma.EMPLEO,
            resumen = "Plan de capacitación técnica y práctica en empresas aliadas.",
            descripcion =
                "Proceso de 120 horas que combina formación técnica, simulación de entrevistas " +
                    "y práctica profesional en empresas aliadas. Cada participante avanza con un " +
                    "plan individual y seguimiento tutorial durante los primeros tres meses.",
            duracion = "120 horas",
            participantes = 32,
            modalidad = "Mixta con práctica en empresas",
            objetivos = listOf(
                "Certificar competencias técnicas específicas",
                "Simular procesos reales de selección",
                "Realizar práctica profesional supervisada",
                "Acompañar la firma del contrato"
            )
        ),
        ProgramaInclusional(
            id = "prog-produccion-accesible",
            nombre = "Laboratorio de Producción Accesible",
            categoria = CategoriaPrograma.PRODUCCION,
            resumen = "Producción y comercialización de productos elaborados por los participantes.",
            descripcion =
                "Taller de producción donde los participantes elaboran productos con protocolos " +
                    "de calidad y seguridad. El excedente se comercializa a través del catálogo " +
                    "de la organización, generando ingresos propios para el programa.",
            duracion = "Continuo, 6 meses por ciclo",
            participantes = 24,
            modalidad = "Taller presencial",
            objetivos = listOf(
                "Aprender un proceso productivo completo",
                "Aplicar protocolos de calidad e higiene",
                "Generar ingresos propios para el programa",
                "Integrarse al mercado mediante el catálogo"
            )
        ),
        ProgramaInclusional(
            id = "prog-charlas-sensibilizacion",
            nombre = "Charlas de Sensibilización RSE",
            categoria = CategoriaPrograma.SENSIBILIZACION,
            resumen = "Jornadas de inclusión para equipos y comisiones directivas.",
            descripcion =
                "Sesiones de entre 3 y 6 horas para empresas y organizaciones, con contenido " +
                    "sobre inclusión, derechos de las personas con discapacidad múltiple y " +
                    "diseño de lugares de trabajo accesibles.",
            duracion = "3 a 6 horas",
            participantes = 120,
            modalidad = "Presencial, remota o in situ",
            objetivos = listOf(
                "Comprender el enfoque de inclusión de la organización",
                "Identificar barreras de acceso en la propia organización",
                "Conocer buenas prácticas de contratación inclusiva",
                "Definir un plan de acción sostenible"
            )
        ),
        ProgramaInclusional(
            id = "prog-acompamiento-familias",
            nombre = "Acompañamiento a Familias",
            categoria = CategoriaPrograma.FORMACION,
            resumen = "Orientación y acompañamiento para redes de apoyo familiar.",
            descripcion =
                "Programa de acompañamiento psicosocial y laboral para familias y redes de " +
                    "apoyo, con talleres sobre autonomía, cuidados y preparación para el " +
                    "trabajo. El foco es fortalecer la autonomía de la persona.",
            duracion = "8 sesiones",
            participantes = 60,
            modalidad = "Presencial",
            objetivos = listOf(
                "Fortalecer la autonomía de las personas acompañadas",
                "Aclarar derechos y beneficios disponibles",
                "Practicar estrategias de autocuidado",
                "Conectar con la red de apoyo institucional"
            )
        ),
        ProgramaInclusional(
            id = "prog-taller-productivo",
            nombre = "Taller Productivo Junior",
            categoria = CategoriaPrograma.PRODUCCION,
            resumen = "Primera aproximación al trabajo para participantes de 18 a 25 años.",
            descripcion =
                "Programa de 40 horas dirigido a personas jóvenes con discapacidad múltiple " +
                    "que se inician en el mundo laboral. Combina módulos de producción, " +
                    "convivencia y autonomía, con cierre en una feria de productos del taller.",
            duracion = "40 horas",
            participantes = 18,
            modalidad = "Taller presencial",
            objetivos = listOf(
                "Introducirse en la dinámica laboral",
                "Adquirir autonomía en el taller",
                "Integrar un equipo de trabajo",
                "Participar en la feria de cierre"
            )
        )
    )

    /** Casos de impacto con datos 100% ficticios y anonimizados. */
    val CASOS = listOf(
        CasoImpacto(
            id = "caso-insercion-logistica",
            titulo = "Inserción en empresa de logística",
            tipo = TipoCaso.INSERCION,
            resumen = "Ocho participantes quedaron con contrato tras el proceso de preparación para el empleo.",
            relato =
                "Un grupo de ocho participantes del proceso Preparación para el Empleo ingresó " +
                    "a una empresa de distribución como operarios de bodega. La empresa " +
                    "participó del programa de charlas de sensibilización y ajustó sus procesos " +
                    "de selección junto al equipo técnico de Alba Lab. A seis meses del ingreso, " +
                    "la totalidad del grupo mantenía su contrato y tres personas asumieron " +
                    "nuevas responsabilidades.",
            indicadores = listOf(
                Indicador("8", "Contratos generados"),
                Indicador("6 meses", "Permanencia promedio"),
                Indicador("100%", "Permanencia al cierre")
            ),
            fecha = "2025"
        ),
        CasoImpacto(
            id = "caso-donacion-taller",
            titulo = "Donación para ampliación del taller",
            tipo = TipoCaso.DONACION,
            resumen = "Aporte empresarial que financió equipamiento para el laboratorio de producción.",
            relato =
                "Una empresa del rubro de alimentos aportó con los fondos necesarios para " +
                    "renovar el laboratorio de producción accesible. El aporte cubrió " +
                    "equipamiento de seguridad, optimización de espacio y capacitación de " +
                    "operadores. Tres meses después, la producción del taller se incrementó, " +
                    "lo que permitió aumentar el número de participantes.",
            indicadores = listOf(
                Indicador("3 meses", "Plazo de ejecución"),
                Indicador("14", "Participantes incorporados"),
                Indicador("100%", "Recursos aplicados al programa")
            ),
            fecha = "2025"
        ),
        CasoImpacto(
            id = "caso-voluntariado-jornadas",
            titulo = "Jornadas de voluntariado corporativo",
            tipo = TipoCaso.VOLUNTARIADO,
            resumen = "Jornadas de voluntariado con tres grupos de colaboradores de empresas aliadas.",
            relato =
                "Durante dos jornadas, equipos corporativos participaron voluntariamente en el " +
                    "empaquetado de productos del taller y en la mantención de áreas comunes. " +
                    "La experiencia incluyó una sesión de sensibilización previa, el " +
                    "acompañamiento de la Red Sociolaboral Alba Lab y un cierre con " +
                    "presentación de los productos elaborados por los participantes.",
            indicadores = listOf(
                Indicador("96", "Voluntarios"),
                Indicador("2", "Jornadas"),
                Indicador("3", "Empresas participantes")
            ),
            fecha = "2024"
        ),
        CasoImpacto(
            id = "caso-capacitacion-rse",
            titulo = "Plan de capacitación RSE para empresas",
            tipo = TipoCaso.CAPACITACION,
            resumen = "Programa de capacitación para 90 colaboradores de una organización del retail.",
            relato =
                "Una organización del retail solicitó un plan de capacitación para su equipo de " +
                    "tienda. La propuesta incluyó dos sesiones sobre inclusión y derechos, más " +
                    "un taller de diseño de procesos de contratación inclusivos. El plan " +
                    "finalizó con un compromiso de revisión semestral de sus indicadores.",
            indicadores = listOf(
                Indicador("90", "Colaboradores capacitados"),
                Indicador("6 horas", "Duración total"),
                Indicador("1", "Compromiso de seguimiento")
            ),
            fecha = "2024"
        ),
        CasoImpacto(
            id = "caso-feria-productos",
            titulo = "Feria de productos solidarios",
            tipo = TipoCaso.VOLUNTARIADO,
            resumen = "Venta de productos del taller en feria de temporada con destino a fondos del programa.",
            relato =
                "El programa de producción presentó su línea completa de productos en una " +
                    "feria de temporada. Todo el monto fue destinado al fondo de continuidad " +
                    "del programa. La instancia mejoró la visibilidad institucional y el " +
                    "contacto con potenciales donantes.",
            indicadores = listOf(
                Indicador("1.200", "Visitantes"),
                Indicador("22", "Productos vendidos"),
                Indicador("100%", "Destino social del monto")
            ),
            fecha = "2024"
        ),
        CasoImpacto(
            id = "caso-empleo-atencion",
            titulo = "Inserción en retail de atención",
            tipo = TipoCaso.INSERCION,
            resumen = "Cinco participantes se incorporan al área de atención al cliente.",
            relato =
                "Tras un proceso de preparación y práctica supervisada, cinco participantes " +
                    "se incorporaron al área de atención al cliente de un retail. Se " +
                    "implementaron ajustes de apoyo visual y pausas programadas, en " +
                    "coordinación con la organización. El equipo de Alba Lab realizó " +
                    "acompañamiento durante los primeros seis meses.",
            indicadores = listOf(
                Indicador("5", "Contratos generados"),
                Indicador("6 meses", "Acompañamiento"),
                Indicador("80%", "Permanencia al año")
            ),
            fecha = "2023"
        )
    )

    /** Catálogo de productos, servicios e iniciativas RSE. */
    val CATALOGO = listOf(
        OfertaCatalogo(
            id = "prod-vela-organica",
            nombre = "Colección Vela",
            tipo = TipoOferta.PRODUCTO,
            descripcion = "Bolsas de tela reutilizable producidas por el taller.",
            detalle =
                "Colección de bolsas de tela reutilizable diseñadas por el taller de producción " +
                    "con estampado protegido. Cada unidad se obtiene con materiales " +
                    "certificados y con listado de personas productoras que respeta la " +
                    "anonimización de los datos personales.",
            precio = "$12.000",
            destacado = true
        ),
        OfertaCatalogo(
            id = "prod-jabon-natural",
            nombre = "Jabones Naturales",
            tipo = TipoOferta.PRODUCTO,
            descripcion = "Jabones artesanales elaborados con insumos naturales.",
            detalle =
                "Línea de jabones artesanales elaborada con insumos naturales y envases " +
                    "reciclables. Es uno de los productos con mayor rotación del taller y " +
                    "contribuye al fondo de continuidad del programa de producción.",
            precio = "$4.500",
            destacado = true
        ),
        OfertaCatalogo(
            id = "prod-cuadernos",
            nombre = "Cuadernos Escolares",
            tipo = TipoOferta.PRODUCTO,
            descripcion = "Cuadernos y agendas producidos en el laboratorio.",
            detalle =
                "Cuadernos y agendas cosidos a mano, con diseño simple y buena " +
                    "accesibilidad. Se elaboran en el laboratorio de producción accesible y " +
                    "se distribuyen a escuelas y programas de educación inclusiva.",
            precio = "$5.000"
        ),
        OfertaCatalogo(
            id = "prod-plantas",
            nombre = "Vivero de Plantas",
            tipo = TipoOferta.PRODUCTO,
            descripcion = "Plantas nativas y de cuidado interior.",
            detalle =
                "Vivero con especies nativas de la región, cuidadas y etiquetadas por el " +
                    "taller. Contribuye a la generación de ingresos del programa de producción " +
                    "y a la mejora de los espacios verdes.",
            precio = "$3.500"
        ),
        OfertaCatalogo(
            id = "serv-capacitacion-rse",
            nombre = "Programa de Capacitación RSE",
            tipo = TipoOferta.SERVICIO,
            descripcion = "Jornadas de inclusión para organizaciones.",
            detalle =
                "Servicio de capacitación y sensibilización adaptado a la organización. Incluye " +
                    "diagnóstico previo, sesiones participativas, material de apoyo y " +
                    "propuesta de plan de acción con indicadores. Modalidades presencial, " +
                    "remota o in situ.",
            precio = "Desde $350.000",
            destacado = true
        ),
        OfertaCatalogo(
            id = "serv-asesoria-inclusion",
            nombre = "Asesoría en Inclusión",
            tipo = TipoOferta.SERVICIO,
            descripcion = "Acompañamiento a empresas en sus procesos de contratación.",
            detalle =
                "Consultoría que acompaña a la empresa en el diseño de procesos de selección " +
                    "accesibles, descripción de cargos, entrevistas adaptadas y seguimiento " +
                    "de la integración de las personas contratadas.",
            precio = "Desde $280.000"
        ),
        OfertaCatalogo(
            id = "serv-alquiler-espacios",
            nombre = "Alquiler de Espacios Accesibles",
            tipo = TipoOferta.SERVICIO,
            descripcion = "Salas accesibles para reuniones y jornadas.",
            detalle =
                "Espacios plenamente accesibles para reuniones, jornadas y actividades de " +
                    "difusión, ubicados en dependencias de la Red Sociolaboral Alba Lab. " +
                    "Incluye apoyo logístico y equipamiento básico.",
            precio = "Desde $45.000"
        ),
        OfertaCatalogo(
            id = "serv-taller-participativo",
            nombre = "Taller Participativo de Producto",
            tipo = TipoOferta.SERVICIO,
            descripcion = "Sesiones de co-creación con el taller de producción.",
            detalle =
                "Actividad de un día donde la organización participa del proceso creativo y " +
                    "productivo del taller. Se generan productos propios con acompañamiento " +
                    "del equipo técnico.",
            precio = "Desde $180.000"
        ),
        OfertaCatalogo(
            id = "rse-programa-donaciones",
            nombre = "Programa de Donaciones",
            tipo = TipoOferta.INICIATIVA_RSE,
            descripcion = "Destino claro y trazable de cada aporte.",
            detalle =
                "Iniciativa RSE que permite dirigir el aporte a una línea específica: " +
                    "equipamiento del taller, becas de formación o fondo de continuidad. " +
                    "La organización entrega un reporte de uso de recursos con indicadores " +
                    "de avance.",
            precio = "Aporte libre",
            destacado = true
        ),
        OfertaCatalogo(
            id = "rse-jornadas-voluntariado",
            nombre = "Jornadas de Voluntariado",
            tipo = TipoOferta.INICIATIVA_RSE,
            descripcion = "Participación de equipos corporativos en el taller.",
            detalle =
                "Programa de voluntariado corporativo con jornadas de trabajo en el taller, " +
                    "sensibilización inicial y cierre con showroom de productos. Se gestionan " +
                    "cupos según la capacidad de cada ciclo productivo.",
            precio = "Coordinación sin costo"
        ),
        OfertaCatalogo(
            id = "rse-bolsa-practicas",
            nombre = "Bolsa de Prácticas",
            tipo = TipoOferta.INICIATIVA_RSE,
            descripcion = "Prácticas profesionales para estudiantes.",
            detalle =
                "Convenio con instituciones educativas para que estudiantes en formación " +
                    "realicen prácticas profesionales en el área técnica, social o " +
                    "administrativa de la organización.",
            precio = "Coordinación sin costo"
        ),
        OfertaCatalogo(
            id = "rse-compras-sostenibles",
            nombre = "Programa de Compras Sostenibles",
            tipo = TipoOferta.INICIATIVA_RSE,
            descripcion = "Artículos del taller con proveedores responsables.",
            detalle =
                "Iniciativa que conecta a la organización con proveedores que incorporan " +
                    "criterios de compra responsable. Incluye un catálogo de productos del " +
                    "taller con trazabilidad de origen y condiciones laborales verificadas.",
            precio = "Sin costo para la empresa"
        )
    )
}
