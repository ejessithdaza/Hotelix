# Old Money — Sistema de diseño v1.0.1

Este documento es la fuente única del sistema: principios, marca, color, tipografía, tokens, componentes, accesibilidad y gobernanza. El código del apéndice es el mismo que se usa en el proyecto, así que no hay que mantener archivos aparte.

## Índice

1. Principios
2. Marca y logotipo
3. Color
4. Tipografía
5. Espaciado, forma, foco, movimiento y layout
6. Componentes
7. Accesibilidad
8. Contenido y tono
9. Reglas de uso y prohibiciones
10. Gobernanza y versiones
11. Pendiente
- Apéndice A: código de tokens
- Apéndice B: código de componentes

---

## 1. Principios

- **Sobriedad.** Pocos colores, mucho aire y nada de decoración que no tenga función.
- **Claridad antes que ornamento.** Cada elemento debe ayudar a entender o a actuar.
- **Accesible por defecto.** Cada combinación de color cumple contraste, o se marca como decorativa y nunca lleva texto.
- **Una sola capa de uso.** Los componentes solo leen tokens semánticos. Los valores crudos viven en los primitivos.

---

## 2. Marca y logotipo

**Logotipo principal.** «Old Money» en Elsie Swash Caps 400, con un tamaño mínimo de 48px. Úsalo en portadas, hero y pie de página grande. Clase: `.om-wordmark--display`.

**Logotipo compacto.** «Old Money» en Arima Madurai 700, 19px. Úsalo en encabezados y en espacios pequeños. Clase: `.om-wordmark`. Elsie Swash Caps no se usa por debajo de 48px, ni siquiera en el logotipo.

**Área de protección.** Deja alrededor del logotipo un espacio igual a la altura de la O inicial, en los cuatro lados.

**Versiones de color.**
- Principal: azul marino sobre marfil.
- Inversa: marfil sobre azul marino o verde bosque.
- Monocroma: carbón cálido sobre marfil, para impresión en blanco y negro.
- Acento opcional: burdeos en un filete o detalle. El oro antiguo solo va en elementos grandes y decorativos.

**Archivos.** Pendiente: versión vectorial con el texto convertido a trazados. Mientras tanto, escribe el nombre con las fuentes indicadas.

**Nunca:** estirar, rotar, añadir sombras o contornos, cambiar la fuente, usar camel o gris piedra como color principal, ni poner azul marino sobre verde bosque.

---

## 3. Color

Ocho colores primitivos, con roles fijos. Los componentes nunca los usan directamente: usan los semánticos (Apéndice A).

| Primitivo | Hex | Rol |
|---|---|---|
| Azul marino | `#1B2A41` | Primario: títulos, botón primario, foco |
| Verde bosque | `#1F3A2E` | Secundario: pie de página, estado de éxito |
| Marfil | `#F5F0E6` | Fondo principal y texto sobre oscuros |
| Camel | `#C8A97E` | Decorativo, velo de resalte, enlaces del pie |
| Oro antiguo | `#A88B4A` | Iconos y ornamentos. Nunca texto |
| Burdeos | `#6D2A36` | Enlaces, acción secundaria, error |
| Carbón cálido | `#2B2622` | Texto de párrafo |
| Gris piedra | `#8A8580` | Bordes de control, divisores, estado deshabilitado |

**Contraste medido sobre marfil (claro):**

| Par | Ratio | Uso permitido |
|---|---|---|
| Carbón sobre marfil | 13.2:1 | Texto de párrafo y de interfaz |
| Azul marino sobre marfil | 12.7:1 | Títulos, foco, botón primario |
| Burdeos sobre marfil | 9.1:1 | Enlaces, error, botón secundario |
| Marfil sobre verde bosque | 10.9:1 | Texto del pie de página |
| Camel sobre verde bosque | 5.5:1 | Enlaces del pie de página |
| Gris piedra sobre marfil | 3.2:1 | Solo bordes de control, divisores y deshabilitado |
| Camel sobre marfil | 2.0:1 | Solo decorativo |
| Oro antiguo sobre marfil | 2.9:1 | Solo iconos y decorativo |


---

## 4. Tipografía

**Familias.**
- Elsie Swash Caps: titulares de 48px en adelante y logotipo principal. Solo pesos 400 y 900; se usa el 400.
- Maitree: párrafos y texto de lectura. Pesos 400 y 600.
- Arima Madurai: interfaz, etiquetas, botones, navegación y subtítulos. Peso 700 para énfasis.

**Escala.**

| Rol | Familia | Peso | Tamaño | Interlínea | Clase |
|---|---|---|---|---|---|
| Display | Elsie Swash Caps | 400 | 64px | 1.2 | `.om-display` |
| Título | Elsie Swash Caps | 400 | 48px | 1.2 | `.om-heading` |
| Título pequeño | Arima Madurai | 700 | 32px | 1.2 | `.om-heading-sm` |
| Subtítulo | Arima Madurai | 700 | 19px | 1.4 | `.om-subheading` |
| Cuerpo | Maitree | 400 | 17px | 1.5 | `.om-body` |
| Etiqueta y botón | Arima Madurai | 700 | 15px, mayúsculas | 1.33 | `.om-button`, `.om-nav-pill` |
| Pie | Maitree | 400 | 13px | 1.23 | `.om-caption` |

**Reglas.**
- Los párrafos no superan los 68 caracteres por línea.
- Las mayúsculas solo aparecen en botones, navegación y pills de idioma.
- Un bloque no usa más de tres tamaños distintos.
- Las etiquetas de campo usan Arima Madurai 700 en 15px, en minúsculas normales.

---

## 5. Espaciado, forma, foco, movimiento y layout

**Espaciado.** Escala base de 4px: `--space-1` (4px) a `--space-24` (96px). Semánticos: `--space-card` (24px, 16px en móvil), `--space-element` (12px) y `--space-section` (entre 80px y 120px, según el ancho).

**Forma.** Radio de 12px en todo: botones, campos, tarjetas, alertas y pills. Los bordes de control son de 2px; los divisores, de 1px.

**Foco.** Todo elemento interactivo muestra un contorno de 2px en azul marino, con 2px de separación. En el pie de página, el foco es marfil. Nunca quites el contorno sin poner otro.

**Movimiento.** Transiciones de 150ms con una curva estándar. Solo cambian color y borde. Con `prefers-reduced-motion`, las transiciones se anulan.

**Layout.** Contenedor máximo de 1200px con 16px de margen lateral. Breakpoint de referencia: 600px (móvil), donde el espaciado de tarjetas y secciones se reduce.

**Área táctil.** Mínimo 44px en controles. Los botones miden 48px.

---

## 6. Componentes

Cada componente incluye cuándo usarlo, sus estados y un ejemplo. Todos cumplen el área táctil mínima y el foco visible.

### 6.1 Botones

**Cuándo usarlo.** Primario: la acción principal de la vista, una por bloque. Secundario: una alternativa válida. Texto: acciones de bajo peso, como «Ver detalles».

**Estados.** Reposo, hover, foco visible, activo y deshabilitado.

```html
<button class="om-button om-button--primary">Empezar</button>
<button class="om-button om-button--secondary">Ver componentes</button>
<button class="om-button om-button--text">Ver detalles</button>
```

**Accesibilidad.** Un botón deshabilitado debe ser `disabled`, no solo visualmente gris. El texto del botón describe la acción, nunca «Aceptar» sin contexto.

### 6.2 Campos de texto

**Cuándo usarlo.** Siempre con etiqueta visible. No uses el placeholder como etiqueta: su contraste es insuficiente.

**Estados.** Reposo, hover (borde carbón), foco (borde azul marino y contorno), error (`aria-invalid="true"`), deshabilitado.

```html
<div class="om-field">
  <label class="om-label" for="correo">Correo</label>
  <input class="om-input" id="correo" aria-invalid="true" aria-describedby="correo-error">
  <p class="om-error" id="correo-error">Falta el dominio, por ejemplo @correo.com.</p>
</div>

```

**Accesibilidad.** Relaciona el mensaje de error con `aria-describedby`. El error se indica con texto además de color.

### 6.3 Pill de idioma y navegación

**Cuándo usarlo.** Selector de idioma o filtros que se pueden marcar. El elemento activo lleva `aria-current="true"`.

**Estados.** Reposo, hover (velo camel), foco, seleccionado, deshabilitado.

```html
<nav aria-label="Idioma">
  <button class="om-nav-pill" aria-current="true">ES</button>
  <button class="om-nav-pill">EN</button>
</nav>
```

### 6.4 Etiquetas

**Cuándo usarlo.** Para marcar estado o categoría en una sola palabra o dos. No son botones: si son clicables, usa un botón o un enlace.

```html
<span class="om-tag">Nuevo</span>
<span class="om-tag om-tag--inverse">Edición limitada</span>
```

### 6.5 Alertas

**Cuándo usarlo.** Mensajes que afectan a la vista actual. Un mensaje emergente de éxito que desaparece no es una alerta: usa un texto de confirmación cerca de la acción.

**Variantes.** Información, éxito, atención y error. El título y el texto explican el estado; el color solo refuerza.

```html
<div class="om-alert om-alert--error" role="alert">
  <p class="om-alert__title">No se pudo enviar</p>
  <p class="om-alert__text">Revisa la dirección e inténtalo de nuevo.</p>
</div>
```

Usa `role="alert"` solo para errores que requieren atención inmediata. Para el resto, omite el rol.

### 6.6 Tarjetas

**Cuándo usarlo.** Para agrupar contenido relacionado que se recorre en bloque. Si toda la tarjeta es un enlace, usa `.om-card--link` con un solo enlace dentro, no un enlace en cada elemento.

```html
<div class="om-card">
  <h3 class="om-subheading">Bolso de cuero</h3>
  <p class="om-body">Piel curtida al vegetal y herrajes de latón.</p>
</div>
```

### 6.7 Pie de página

**Estructura.** Fondo verde bosque, logotipo, enlaces agrupados por sección y una sola línea legal. Los enlaces van en camel y el foco en marfil.

### 6.8 Textos y encabezados

Usa las clases de la escala de la sección 4. Un solo `h1` por página, y los niveles de encabezado en orden, sin saltos.

---

## 7. Accesibilidad

Revisa esta lista antes de dar un componente por terminado:

- Cada texto cumple el contraste de su par (sección 3). Los decorativos no llevan texto.
- Todo elemento interactivo se puede usar con teclado, en un orden de tabulación lógico.
- El foco siempre es visible. No elimines `outline` sin reemplazarlo.
- Los controles miden al menos 44px de área táctil.
- Cada campo tiene etiqueta visible. Los errores se anuncian con `aria-invalid` y `aria-describedby`.
- El estado no se comunica solo con color: siempre hay texto o icono.
- La página sigue siendo usable al 200% de zoom, sin desplazamiento horizontal en el cuerpo del texto.
- Las animaciones respetan `prefers-reduced-motion`.

---

## 8. Contenido y tono

- Frases cortas, en tú, con un tono cercano y sin exceso de formalidad.
- Botones con verbo y destino claro: «Guardar cambios», no «Aceptar».
- Mensajes de error que dicen qué pasó y cómo solucionarlo.
- Sin signos de exclamación en la interfaz, salvo en un error grave que exija atención.
- Cifras con formato local, por ejemplo «120 €» y «1.250,00».

---

## 9. Reglas de uso y prohibiciones

**Sí.**
- Titulares en azul marino y párrafos en carbón cálido.
- Botones primarios en azul marino, uno por vista.
- Burdeos para enlaces y acciones secundarias.
- Verde bosque solo en el pie de página o en estados de éxito.
- Radio de 12px, bordes de 2px en controles y sin sombras.

**No.**
- No pongas texto en camel ni en oro sobre marfil.
- No uses gris piedra para texto esencial.
- No apliques sombras, degradados ni brillos.
- No uses Elsie Swash Caps por debajo de 48px.
- No uses mayúsculas fuera de botones, navegación y pills de idioma.
- No combines azul marino con verde bosque como fondo y texto.
- No inventes colores fuera de la paleta. Si hace falta uno, se añade a la paleta con una versión mayor del sistema.

---

## 10. Gobernanza y versiones

**Versionado semántico.** Cambiar un primitivo o renombrar un token es un cambio MAYOR. Añadir un componente o un tema es MENOR. Corregir un valor es PARCHE.

**Antes de cambiar un color:** comprueba el contraste sobre marfil, sobre verde bosque y sobre azul marino.

**Antes de añadir un componente:** define todos sus estados aplicables, incluye su código en el apéndice, añade un ejemplo en este documento y comprueba la lista de accesibilidad.

**Historial.**
- **1.0.0.** Base: tokens claros, componentes principales, página de muestra y playground.
- **1.0.1.** Correcciones: el logotipo compacto ya no usa Elsie Swash Caps por debajo de 48px, y las pills de idioma miden al menos 44px.

---

## 11. Pendiente

Lo que todavía no está incluido en la versión 1.0.1:

- Casillas, radios e interruptores.
- Selects y áreas de texto.
- Pestañas, tablas, menús y diálogos.
- Iconos y estados de carga (spinner, esqueleto y barra de progreso).
- Tema oscuro.
- Archivo vectorial del logotipo.
- Pruebas automáticas de contraste y de regresión visual.

---

## Apéndice A: código de tokens

Archivo `tokens.css`. Incluye primitivos, semánticos y reglas de espaciado responsivo y movimiento reducido.

```css
/*
 * Old Money — Tokens v1.0.0
 * Capas: primitivos → semánticos → componentes (components.css).
 * Los componentes solo usan tokens semánticos. Los primitivos se cambian en un único lugar.
 */

:root {
  /* ---------- 1. Primitivos ---------- */

  /* Color (paleta Old Money) */
  --palette-azul-marino: #1B2A41;
  --palette-verde-bosque: #1F3A2E;
  --palette-marfil: #F5F0E6;
  --palette-camel: #C8A97E;
  --palette-oro-antiguo: #A88B4A;
  --palette-burdeos: #6D2A36;
  --palette-carbon-calido: #2B2622;
  --palette-gris-piedra: #8A8580;

  /* Tipografía */
  --font-display: 'Elsie Swash Caps', 'Cinzel Decorative', Georgia, serif;
  --font-body: 'Maitree', 'Crimson Pro', Georgia, serif;
  --font-ui: 'Arima Madurai', 'Source Sans 3', system-ui, sans-serif;

  --text-caption: 13px;     --leading-caption: 1.23;
  --text-label: 15px;       --leading-label: 1.33;    --tracking-label: 0.053em;
  --text-body: 17px;        --leading-body: 1.5;
  --text-subheading: 19px;  --leading-subheading: 1.4;
  --text-heading-sm: 32px;  --leading-heading-sm: 1.2;
  --text-heading: 48px;     --leading-heading: 1.2;   --tracking-heading: -0.02em;
  --text-display: 64px;     --leading-display: 1.2;   --tracking-display: -0.02em;

  --weight-regular: 400;
  --weight-semibold: 600;
  --weight-bold: 700;

  /* Espaciado (base 4px) */
  --space-1: 4px;   --space-2: 8px;   --space-3: 12px;  --space-4: 16px;
  --space-6: 24px;  --space-8: 32px;  --space-10: 40px; --space-12: 48px;
  --space-16: 64px; --space-20: 80px; --space-24: 96px;

  /* Forma, borde, foco, movimiento, layout */
  --radius-md: 12px;
  --border-thin: 1px;
  --border-thick: 2px;
  --focus-ring-width: 2px;
  --focus-ring-offset: 2px;
  --duration-fast: 150ms;
  --ease-standard: cubic-bezier(0.2, 0.6, 0.2, 1);
  --layout-max: 1200px;

  /* ---------- 2. Semánticos (nombran la intención) ---------- */

  /* Texto (contraste sobre marfil entre paréntesis) */
  --color-text: var(--palette-carbon-calido);          /* 13.2:1 */
  --color-text-heading: var(--palette-azul-marino);    /* 12.7:1 */
  --color-text-link: var(--palette-burdeos);           /* 9.1:1 */
  --color-text-link-hover: var(--palette-azul-marino);
  --color-text-on-dark: var(--palette-marfil);         /* 12.7:1 sobre azul marino */
  --color-text-disabled: var(--palette-gris-piedra);   /* exento: estado inactivo */

  /* Superficies */
  --color-surface: var(--palette-marfil);
  --color-surface-inverse: var(--palette-azul-marino);
  --color-surface-brand: var(--palette-verde-bosque);
  --color-surface-accent: rgba(200, 169, 126, 0.2);    /* velo camel */

  /* Bordes */
  --color-border: var(--palette-gris-piedra);          /* 3.2:1, válido para bordes de control */
  --color-border-strong: var(--palette-carbon-calido);

  /* Acciones */
  --color-action-primary: var(--palette-azul-marino);
  --color-action-primary-hover: var(--palette-verde-bosque);
  --color-action-primary-active: var(--palette-carbon-calido);
  --color-action-primary-text: var(--palette-marfil);
  --color-action-secondary-text: var(--palette-burdeos);
  --color-action-secondary-border: var(--palette-gris-piedra);
  --color-action-secondary-hover: var(--color-surface-accent);
  --color-focus-ring: var(--palette-azul-marino);      /* 12.7:1 sobre marfil */

  /* Estados */
  --color-info: var(--palette-azul-marino);
  --color-info-surface: rgba(27, 42, 65, 0.08);
  --color-success: var(--palette-verde-bosque);
  --color-success-surface: rgba(31, 58, 46, 0.1);
  --color-warning: var(--palette-oro-antiguo);         /* solo bordes e iconos; el texto usa --color-text */
  --color-warning-surface: var(--color-surface-accent);
  --color-error: var(--palette-burdeos);
  --color-error-surface: rgba(109, 42, 54, 0.08);

  /* Footer */
  --color-footer-link: var(--palette-camel);           /* 5.5:1 sobre verde bosque */

  /* Espaciado semántico */
  --space-card: var(--space-6);
  --space-element: var(--space-3);
  --space-section: clamp(var(--space-20), 8vw, 120px);
}

@media (max-width: 600px) {
  :root {
    --space-card: var(--space-4);
    --space-section: var(--space-16);
  }
}

@media (prefers-reduced-motion: reduce) {
  :root {
    --duration-fast: 0ms;
  }
}
```

## Apéndice B: código de componentes

Archivo `components.css`. Requiere el apéndice A. Usa solo tokens semánticos.

```css
/*
 * Old Money — Componentes v1.0.0
 * Requiere tokens.css. Usa solo tokens semánticos.
 * Estados: reposo, hover, focus-visible, activo, deshabilitado y error.
 * Las clases .is-* son solo para documentar estados; en producción se usan :hover, :focus-visible, :active y :disabled.
 */

*, *::before, *::after { box-sizing: border-box; }

body {
  margin: 0;
  font: var(--weight-regular) var(--text-body)/var(--leading-body) var(--font-body);
  color: var(--color-text);
  background: var(--color-surface);
  -webkit-font-smoothing: antialiased;
}

:focus-visible {
  outline: var(--focus-ring-width) solid var(--color-focus-ring);
  outline-offset: var(--focus-ring-offset);
}

.is-focus {
  outline: var(--focus-ring-width) solid var(--color-focus-ring);
  outline-offset: var(--focus-ring-offset);
}

/* Layout */
.om-container { width: 100%; max-width: var(--layout-max); margin-inline: auto; padding-inline: var(--space-4); }
.om-section { padding-block: var(--space-section); }

/* Tipografía */
.om-display { margin: 0; font: var(--weight-regular) var(--text-display)/var(--leading-display) var(--font-display); letter-spacing: var(--tracking-display); color: var(--color-text-heading); }
.om-heading { margin: 0; font: var(--weight-regular) var(--text-heading)/var(--leading-heading) var(--font-display); letter-spacing: var(--tracking-heading); color: var(--color-text-heading); }
.om-heading-sm { margin: 0; font: var(--weight-bold) var(--text-heading-sm)/var(--leading-heading-sm) var(--font-ui); color: var(--color-text); }
.om-subheading { margin: 0; font: var(--weight-bold) var(--text-subheading)/var(--leading-subheading) var(--font-ui); color: var(--color-text-heading); }
.om-body { margin: 0; max-width: 68ch; }
.om-caption { margin: 0; font: var(--weight-regular) var(--text-caption)/var(--leading-caption) var(--font-body); }
.om-wordmark { font: var(--weight-bold) var(--text-subheading)/1 var(--font-ui); color: var(--color-text-heading); }
.om-wordmark--display { font: var(--weight-regular) var(--text-heading)/1 var(--font-display); letter-spacing: var(--tracking-heading); }

/* Botones */
.om-button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: var(--space-2);
  min-height: 48px;
  padding: var(--space-3) var(--space-4);
  border: var(--border-thick) solid transparent;
  border-radius: var(--radius-md);
  background: transparent;
  color: var(--color-text);
  font: var(--weight-bold) var(--text-label)/var(--leading-label) var(--font-ui);
  letter-spacing: var(--tracking-label);
  text-transform: uppercase;
  text-decoration: none;
  cursor: pointer;
  transition: background-color var(--duration-fast) var(--ease-standard),
              border-color var(--duration-fast) var(--ease-standard),
              color var(--duration-fast) var(--ease-standard);
}

.om-button--primary { background: var(--color-action-primary); color: var(--color-action-primary-text); }
.om-button--primary:hover, .om-button--primary.is-hover { background: var(--color-action-primary-hover); }
.om-button--primary:active, .om-button--primary.is-active { background: var(--color-action-primary-active); }

.om-button--secondary { border-color: var(--color-action-secondary-border); color: var(--color-action-secondary-text); }
.om-button--secondary:hover, .om-button--secondary.is-hover { background: var(--color-action-secondary-hover); }
.om-button--secondary:active, .om-button--secondary.is-active { border-color: var(--color-action-secondary-text); }

.om-button--text {
  min-height: 0;
  padding: var(--space-1) var(--space-2);
  color: var(--color-text-link);
  font: var(--weight-semibold) var(--text-body)/var(--leading-body) var(--font-body);
  letter-spacing: normal;
  text-transform: none;
  text-decoration: underline;
  text-decoration-thickness: 2px;
  text-underline-offset: 4px;
}
.om-button--text:hover, .om-button--text.is-hover { color: var(--color-text-link-hover); }

.om-button:disabled { cursor: not-allowed; }
.om-button--primary:disabled { background: var(--color-text-disabled); color: var(--color-surface); }
.om-button--secondary:disabled { background: transparent; border-color: var(--color-text-disabled); color: var(--color-text-disabled); }
.om-button--text:disabled { color: var(--color-text-disabled); text-decoration: none; }

/* Campos */
.om-field { display: grid; gap: var(--space-2); }
.om-label { font: var(--weight-bold) var(--text-label)/var(--leading-label) var(--font-ui); color: var(--color-text-heading); }
.om-input {
  width: 100%;
  min-height: 48px;
  padding: var(--space-3) var(--space-4);
  border: var(--border-thick) solid var(--color-border);
  border-radius: var(--radius-md);
  background: var(--color-surface);
  color: var(--color-text);
  font: var(--weight-regular) var(--text-body)/var(--leading-body) var(--font-body);
  transition: border-color var(--duration-fast) var(--ease-standard);
}
.om-input:hover, .om-input.is-hover { border-color: var(--color-border-strong); }
.om-input:focus-visible, .om-input.is-focus { border-color: var(--color-focus-ring); }
.om-input[aria-invalid="true"], .om-input.is-error { border-color: var(--color-error); }
.om-input:disabled { background: transparent; border-color: var(--color-border); color: var(--color-text-disabled); cursor: not-allowed; }
.om-hint, .om-error { margin: 0; font: var(--weight-regular) var(--text-caption)/var(--leading-caption) var(--font-body); }
.om-error { color: var(--color-error); font-weight: var(--weight-semibold); }

/* Navegación: pill de idioma */
.om-nav-pill {
  display: inline-flex;
  align-items: center;
  min-height: 44px;
  padding: var(--space-2) var(--space-4);
  border: var(--border-thick) solid var(--color-border);
  border-radius: var(--radius-md);
  background: transparent;
  color: var(--color-text);
  font: var(--weight-bold) var(--text-label)/var(--leading-label) var(--font-ui);
  letter-spacing: var(--tracking-label);
  text-transform: uppercase;
  cursor: pointer;
  transition: background-color var(--duration-fast) var(--ease-standard);
}
.om-nav-pill:hover, .om-nav-pill.is-hover { background: var(--color-surface-accent); }
.om-nav-pill[aria-current="true"] { border-color: var(--color-text-heading); color: var(--color-text-heading); }
.om-nav-pill:disabled { color: var(--color-text-disabled); cursor: not-allowed; }

/* Etiquetas */
.om-tag { display: inline-flex; padding: var(--space-1) var(--space-3); border-radius: var(--radius-md); background: var(--color-surface-accent); color: var(--color-text); font: var(--weight-bold) var(--text-caption)/var(--leading-caption) var(--font-ui); }
.om-tag--inverse { background: var(--color-surface-inverse); color: var(--color-text-on-dark); }

/* Alertas: el color cambia con las variables locales --_accent, --_surface y --_title */
.om-alert {
  --_accent: var(--color-border);
  --_surface: transparent;
  --_title: var(--color-text-heading);
  display: grid;
  gap: var(--space-1);
  padding: var(--space-4);
  border: var(--border-thick) solid var(--_accent);
  border-radius: var(--radius-md);
  background: var(--_surface);
  color: var(--color-text);
}
.om-alert__title { margin: 0; font: var(--weight-bold) var(--text-body)/1.4 var(--font-ui); color: var(--_title); }
.om-alert__text { margin: 0; }
.om-alert--info { --_accent: var(--color-info); --_surface: var(--color-info-surface); --_title: var(--color-info); }
.om-alert--success { --_accent: var(--color-success); --_surface: var(--color-success-surface); --_title: var(--color-success); }
.om-alert--warning { --_accent: var(--color-warning); --_surface: var(--color-warning-surface); }
.om-alert--error { --_accent: var(--color-error); --_surface: var(--color-error-surface); --_title: var(--color-error); }

/* Tarjetas */
.om-card {
  display: grid;
  gap: var(--space-element);
  padding: var(--space-card);
  border: var(--border-thick) solid var(--color-border);
  border-radius: var(--radius-md);
  background: var(--color-surface);
}
.om-card--link { color: inherit; text-decoration: none; }
.om-card--link:hover, .om-card--link.is-hover { border-color: var(--color-border-strong); }

/* Footer */
.om-footer { padding-block: var(--space-12); background: var(--color-surface-brand); color: var(--color-text-on-dark); }
.om-footer a { color: var(--color-footer-link); text-decoration: none; }
.om-footer a:hover { text-decoration: underline; }
.om-footer :focus-visible { outline-color: var(--color-text-on-dark); }
.om-footer .om-subheading { color: var(--color-text-on-dark); }
```
