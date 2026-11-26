Actúa como un Arquitecto de Software Senior en Java. Necesito desarrollar un proyecto de aula universitario llamado **"Hotelix"**, un sistema integral de gestión hotelera construido en **Java** utilizando **JavaFX** para la interfaz gráfica.

### 1. Restricciones Técnicas y Arquitectura

- **Idioma del Código:** Todo el código fuente (paquetes, clases, interfaces, atributos, métodos, variables) debe estar escrito estrictamente en **inglés** (los comentarios en el codigo y los textos visibles en la interfaz de usuario para el usuario final pueden ir en español o inglés según corresponda, pero la arquitectura interna son 100% en inglés).
- **Arquitectura de Cuatro Capas:** Organiza el proyecto estrictamente en los siguientes paquetes:
  - `entities` (Modelos, clases abstractas, enums e interfaces de dominio).
  - `repositories` (Persistencia básica mediante lectura y escritura en archivos `.txt` planos con delimitadores claros).
  - `services` (Lógica de negocio, validaciones, estrategias de recomendación y cálculo de métricas).
  - `ui` / `controllers` (Vistas y controladores de JavaFX).

- **Pilares de POO y Java Avanzado:**
  - Implementa **Encapsulamiento, Abstracción, Herencia y Polimorfismo** de manera explícita y natural.
  - Implementa **Herencia Múltiple mediante Interfaces** (por ejemplo: `Billable`, `Maintainable`, `Recommendable`, `Persistable`).

### 2. Funcionalidades Core y Reglas de Negocio

1. **Gestión de Habitaciones, Huéspedes y Reservas:** CRUD completo con persistencia en archivos `.txt`.
2. **Detección de Conflictos de Fechas (Prioridad Crítica):** El sistema no debe validar disponibilidad solo por un estado booleano actual, sino evaluando solapamientos de rangos de fechas (`checkInDate` y `checkOutDate`). Debe permitir reservas consecutivas el mismo día (ej. una termina el 13 y otra empieza el 13), pero bloquear cualquier traslape parcial o total.
3. **Reglas de Negocio Estrictas:**
   - Prohibido hacer check-in a una reserva cancelada o ya activa.
   - Prohibido registrar dos veces el check-out de una misma estadía.
   - Prohibido liberar o eliminar una habitación con una estadía activa o en mantenimiento crítico.
4. **Facturación Detallada:** Cálculo automático del costo de alojamiento (noches × tarifa base) sumado a servicios adicionales consumidos (comidas, lavandería, estacionamiento), generando un desglose detallado por concepto.

### 3. Módulos Avanzados y Diferenciadores

1. **Recomendador Inteligente de Habitaciones (Patrón Strategy con Interfaces):**
   - Recibe criterios del huésped: número de personas, presupuesto máximo por noche, servicios deseados (ej. desayuno), nivel de comodidad y accesibilidad.
   - Filtra habitaciones disponibles en las fechas solicitadas y las ordena mediante puntuaciones de afinidad, mostrando primero las que cumplen el 100% y luego alternativas cercanas indicando qué requisito no cumplen.
2. **Calendario Visual de Ocupación (JavaFX Grid/Table):**
   - Matriz visual donde las **filas son las habitaciones** y las **columnas son los días** de un rango seleccionado.
   - Diferencia visualmente mediante códigos de color: Disponible, Ocupada, Reserva Futura, Llegada Programada (Check-in hoy), Salida Prevista (Check-out hoy) y Fuera de Servicio.
3. **Gestión de Incidencias y Mantenimiento:**
   - Registro de fallas asociadas a una habitación (descripción, fecha, nivel de prioridad, responsable, estado y costo de reparación).
   - Flujo de estado: al reportar una falla crítica, la habitación pasa automáticamente a _Out of Service_ (bloqueando reservas) y solo vuelve a _Available_ cuando se registra la reparación y se cierra la incidencia. Debe conservar historial de mantenimiento por habitación.
4. **Gestión de Preferencias de Huéspedes:**
   - Almacenamiento de preferencias históricas por huésped (ej. lejos del ascensor, desayuno incluido, cama específica, accesibilidad) que se muestran como alerta/sugerencia al crear una nueva reserva sin forzar asignación si no hay disponibilidad.
5. **Registro de Objetos Olvidados (Lost & Found):**
   - Registro post-check-out con descripción del objeto, habitación, fecha de hallazgo, empleado que reporta y estado de devolución (Pendiente / Entregado).
6. **Panel de Indicadores (KPIs):**
   - Cálculo dinámico de: porcentaje de ocupación real en un periodo (excluyendo habitaciones fuera de servicio del denominador de noches-habitación disponibles), ingresos por alojamiento, ingresos por servicios adicionales, tipo de habitación más reservado, total de cancelaciones y habitaciones en mantenimiento.

### 4. Forma de Trabajo

Construyamos el proyecto de forma incremental, ordenada y compilable paso a paso (empezando por la arquitectura de paquetes, `entities`, luego `repositories` en `.txt`, después `services` y finalmente la interfaz gráfica en `JavaFX`). No omitas lógica ni dejes métodos vacíos con comentarios `// TODO`.
