# Hotelix — DESIGN.md

## Overview

Hotelix is a hotel management desktop application used by hotel administrators. Its look is calm, refined and timeless, closer to the library of a private country house than to a technology dashboard. Every screen should feel quiet and trustworthy: generous space, few colors, and no decoration that does not help the person using it.

Why: administrators spend long sessions reading dense information, such as reservations, calendars, invoices and occupancy figures. A restrained palette of deep navy and ivory keeps attention on the numbers and the status of each room, and stays comfortable to read for hours.

## Colors

Use these exact values. Each color has one role. Do not use a color for a role that is not listed here.

- **Deep navy, #1B2A41.** Primary color. Used for titles, primary buttons, the text of active navigation items and the focus outline.
- **Forest green, #1F3A2E.** Secondary color. Used for the sidebar background and for success messages.
- **Ivory, #F5F0E6.** Main background of every screen, and text on dark surfaces.
- **Camel, #C8A97E.** Soft accent. Used for links in the sidebar, the splash line and small highlights. Never as text on ivory.
- **Muted gold, #A88B4A.** Decorative icons and ornaments only. Never used for text.
- **Burgundy, #6D2A36.** Used for links, secondary buttons, errors and low-stock warnings.
- **Warm charcoal, #2B2622.** Body text and form input text.
- **Stone gray, #8A8580.** Borders, dividers and disabled elements. Never used for text.
- **Camel tint, #ECE2D1.** Camel at 20% on ivory. Used as the hover background of rows and secondary buttons, and as the background of the active sidebar item.

Contrast reference: charcoal on ivory is 13.2:1, navy on ivory is 12.7:1, burgundy on ivory is 9.1:1, ivory on forest green is 10.9:1, and camel on forest green is 5.5:1.

## Typography

- **Display titles:** Elsie Swash Caps, regular weight. Used for the splash wordmark and for the main title of a page. Never smaller than 48px.
- **Subtitles, navigation, buttons and table headers:** Arima Madurai, bold (700).
- **Body text, form text and descriptions:** Maitree, regular, 17px with a line height of 1.5.
- **Captions and footnotes:** Maitree, regular, 13px.
- **Uppercase:** only for button labels and navigation labels, with a slight letter spacing of 0.053em. All other text is in sentence case.
- Do not use more than three font sizes within one block of content.

## Shapes, borders and spacing

- **Corners:** 12px radius on every element: buttons, inputs, cards, tags and alerts.
- **Borders:** 2px solid for inputs, buttons and cards. 1px for table dividers.
- **No shadows, gradients or glow.** Depth comes from space and borders, never from effects.
- **Spacing:** built on a 4px grid. Cards have 24px of inner padding. Sections are separated by between 80px and 120px on desktop.
- **Focus:** a 2px navy outline with a 2px gap around the element. It must be visible on every interactive element, including with the keyboard.

## Layout

- The application runs in a desktop window, at least 1280 by 800 pixels.
- A left sidebar, 240px wide, in forest green. Its navigation items use ivory text. The active item has a camel tint background and a 2px camel edge on its left side.
- The content area sits on ivory, with a maximum width of 1200px and 16px of side margin.
- Content is organized in a single column. Cards and tables are arranged in grids.

## Components

- **Primary button:** navy fill, ivory uppercase text, 48px tall, 12px radius. On hover it turns forest green. When pressed it turns warm charcoal. When disabled it turns stone gray, with ivory text.
- **Secondary button:** transparent background, 2px stone-gray border, burgundy uppercase text. On hover it gets the camel tint background. When disabled, its border and text turn stone gray.
- **Text link:** burgundy, underlined, in sentence case.
- **Input field:** ivory fill, 2px stone-gray border, 48px tall, warm charcoal text in Maitree 17px. The label sits above the field in navy, Arima Madurai bold 15px. On hover the border becomes warm charcoal. On focus the border becomes navy and the focus outline appears. On error the border becomes burgundy and a message in burgundy appears below, explaining the problem in plain words.
- **Checkbox and radio button:** navy when selected, with a warm charcoal label.
- **Toggle switch:** a 48 by 28 pixel track. When off, it has a stone-gray outline and a stone-gray knob. When on, it has a navy fill and an ivory knob.
- **Statistic card:** ivory, 2px stone-gray border, 24px padding, no shadow. The label is in Maitree 13px. The value is in Arima Madurai bold, 32px, navy.
- **Status tag:** a small rectangle with a 12px radius. "Paid" is filled navy with ivory text. "Pending" is outlined in stone gray with warm charcoal text. Status is always shown with a word, never with color alone.
- **Alerts:** a full-width box with a 2px border in the status color and a bold title. Success uses forest green. Warning uses warm charcoal text on the camel tint with a muted gold border. Error uses burgundy.
- **Table:** the header row uses Arima Madurai bold in navy with a 2px warm-charcoal bottom border. Rows are separated by 1px stone-gray dividers. A hovered row takes the camel tint. Numbers are right-aligned.
- **Dialog:** ivory, 2px stone-gray border, 24px padding. The background behind it is dimmed with navy at 70% opacity.

## Imagery and icons

- The administration app uses no photographs. Use neutral ivory placeholders or flat line icons instead.
- Icons sit on a 24px grid, with a 2px rounded stroke, and inherit the text color. Decorative icons use muted gold.
- The splash screen uses the ivory background #F5F0E6, the Hotelix wordmark in deep navy #1B2A41, and a thin camel line below it. The splash follows the same palette as every other screen.

## Interface text

- Interface text is in Spanish, friendly and direct.
- Button labels are verbs that say what will happen, for example "Guardar pedido" instead of "Aceptar".
- Error messages say what went wrong and how to fix it.

## Do

- Keep one primary button per screen.
- Use navy for titles and for the most important action.
- Show status with a word and a color together.
- Leave generous space between groups of information.

## Do not

- Do not set camel or gold text on ivory.
- Do not use stone gray for text.
- Do not add shadows, gradients or decorative patterns.
- Do not use bright colors outside this palette.
