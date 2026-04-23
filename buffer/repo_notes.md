# saberooniwikirc — Repo Notes

> **Repo path:** `/run/media/epxzzy/shared/stuff/repos/mcmods/saberooniwikirc`
> **Hosted at:** `https://epxzzy.github.io/epxzzysabers/`

---

## What is this?

Wiki for a Minecraft Forge 1.20.1 mod called **epxzzy's sabers** (`epxzzysabers`). The mod adds plasma saber/laser sword weapons (referred to as "saber weapons" to avoid DMCA issues) with stances, abilities, animations, custom HUD, and a colour-customisation block (Kyber Station). The wiki is a **static HTML site** — no build step, no JS framework, no Markdown-to-HTML pipeline. It's served directly from GitHub Pages.

---

## Top-Level Directory Layout

```
/
├── index.html                  ← Landing / about page (root, NOT the wiki root)
├── 404.html / 404.md           ← Custom 404
├── CHANGELOG_HISTORICAL.md     ← Old changelogs (pre-Sept 2025)
├── CHANGELOG_RECENT.md         ← Player-facing recent changelog (prose style)
├── README.md                   ← (Empty, leftover file to be ignored)
├── choreography_concept.png    ← Concept art (root level, unused in wiki)
├── docs/                       ← All wiki pages + shared assets
│   ├── index.html              ← Wiki root (links to items/, blocks/, misc/)
│   ├── style.css               ← Global stylesheet (shared by all pages)
│   ├── items.css               ← Item sprite CSS classes for crafting UIs
│   ├── items/                  ← One HTML page per weapon/item
│   ├── blocks/                 ← One HTML page per block
│   ├── misc/                   ← Getting started, combat, compatibility pages
│   └── mechanics/              ← Sub-section for mechanics (index.html recently added)
├── public/                     ← Public assets (scripts, media)
│   ├── media/                  ← Images / GIFs used by wiki pages
│   └── scripts/                ← JS/CSS for tooltips/drags
└── buffer/                     ← Working documents (NOT served)
    ├── CHANGELOG_TEMP.md       ← RC changelog (source of truth for v0.0.4 changes)
    ├── CHANGELOG_HISTORICAL.md ← Copy of root historical changelog
    ├── WIKI_UPDATE_GUIDE_v0.0.4.md   ← Per-page update instructions
    └── WIKI_UPDATE_QUICK_REFERENCE.md
```

*(Note: `src/main/` directory exists but is leftover from a Java/mod source tree and will be removed soon. Ignore it.)*

---

## docs/items/ — Item Pages

| File | Item | Notable State |
|---|---|---|
| `singlebladed.html` | Single-Bladed Saber | Basic page, minimal content |
| `dualbladed.html` | Dual-Bladed Saber | Basic page |
| `crossguardsaber.html` | Crossguard Saber | Model reworked v0.0.4 (Dead_Comedian) |
| `blastersaber.html` | Blaster Saber | Model reworked v0.0.4 (Marmitex); has Plasma Bolt section |
| `rotarysaber.html` | Rotary Saber | Most complete page; has HUD section, History up to v0.0.4 ✓ |
| `sabergauntlet.html` | Saber Gauntlet | Model reworked v0.0.4 (Dead_Comedian); HUD section ✓; History v0.0.4 ✓ |
| `saberpike.html` | Saber Pike | Model WIP, basic page |
| `krystal.html` | Krystal (crafting ingredient) | Recipe changed in v0.0.4 (Lapis→Quartz) ✓; History entry added ✓ |
| `index.html` | Items index | Lists all item pages |

## docs/blocks/ — Block Pages

| File | Block | State |
|---|---|---|
| `kyberstation.html` | Kyber Station | v0.0.4 tinting redesign noted ✓; History entry ✓ |
| `index.html` | Blocks index | Stub |

## docs/misc/ — Misc Pages

| File | Content | State |
|---|---|---|
| `getting_started.html` | Combat mechanics primer (attacking, blocking, parrying, stances) | v0.0.4 section (Miscellaneous) ✓; History ✓ |
| `combat.html` | Stancing page stub — very thin, likely being replaced by getting_started | Has v0.0.4 history entry for stance pref menu |
| `compatibility.html` | BetterCombat, shaders, data-driven config | Fully written for v0.0.4 ✓ |
| `index.html` | 105-byte stub | Empty |

## docs/mechanics/

- User recently populated `index.html` with basic HTML boilerplate.

---

## CSS System

### `docs/style.css` — Global Styles

- **Font:** `system-ui, sans-serif` everywhere. Custom `minefont` (Minecraft pixel font) loaded from `https://epxzzy.github.io/mcfont.woff` — used only for item slot counts.
- **Layout:** `max-width: 900px; margin: 0 auto;` on `main`. Pages use `.container` → `.sidebar` + `.content .main-content` two-column-ish layout (sidebar is `position: absolute; left: 75%`).
- **Key components:**
  - `.wikitable` / `.info-table` — Wikipedia-style data tables
  - `.toc-container` / `.toc-list` / `.toc-item` / `.toc-link` — Table of contents
  - `.sidebar` — Right-side infobox panel
  - `.block-image` — 100×100px pixelated item/block image
  - `.other-image` — 20vw-wide images (screenshots, GIFs)
  - `.craftingui` / `.craftinggrid` / `.slot` / `.slotbig` / `.craftingarrow` — Crafting table UI replica

### `docs/items.css` — Item Sprite Classes

CSS classes for each Minecraft item used in crafting grids (background-image from minecraft.wiki). Examples: `.KrystalItem`, `.DiamondItem`, `.NetheriteItem`, `.QuartzItem` (exists in krystal recipe but `.QuartzItem` class is **NOT** in items.css yet!), `.GoldItem`, etc.

> ⚠️ **Known gap**: `.QuartzItem` class is referenced in `krystal.html` but **not defined** in `items.css` — it uses an inline `onclick` reference but the visual class for Nether Quartz in the initial recipe may need adding.

---

## HTML Page Conventions

All item/block pages follow this structure:

```html
<html>
<head>
  <link rel="stylesheet" href="../style.css">
  <link rel="stylesheet" href="../items.css">        <!-- items only -->
  <link rel="stylesheet" href="/public/scripts/itemhover.css">
  <link rel="stylesheet" href="/public/scripts/itemdrag.css">
</head>
<body>
  <div id="minetip-tooltip" ...>  <!-- tooltip container for item hover -->

  <div class="container">
    <header></header>
    <nav class="navigation">
      <div class="breadcrumb">root > section > page</div>
      <a href="...github source link...">source</a>
    </nav>
    <h1 class="wiki-title">Page Title</h1>
    <hr>
    <div class="sidebar">
      <div class="block-image"><img src="..."></div>
      <table class="info-table"> ... </table>
    </div>
    <div class="content">
      <main class="main-content">
        <div id="Description">...</div>
        <div class="toc-container">...</div>
        <section id="Obtaining">...</section>
        <section id="Crafting">...</section>
        <section id="Usage">...</section>
        <section id="History">
          <table class="wikitable info-table">
            <tr><td><b>0.0.X</b></td><td>Change description.</td></tr>
          </table>
        </section>
      </main>
    </div>
  </div>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="/public/scripts/itemhover.js"></script>
</body>
</html>
```

---

## External & Local Dependencies

| Resource | Location / Usage |
|---|---|
| `itemhover.css` | `/public/scripts/itemhover.css` (Minecraft-style item tooltip styling) |
| `itemdrag.css` | `/public/scripts/itemdrag.css` (Drag visuals) |
| `itemhover.js` | `/public/scripts/itemhover.js` (Tooltip JS logic) |
| `jquery.min.js` | `https://ajax.googleapis.com/...` (jQuery for tooltip JS) |
| Minecraft item sprites | `https://minecraft.wiki/images/...` |
| `mcfont.woff` | `https://epxzzy.github.io/mcfont.woff` (Minecraft pixel font) |

*(Note: `epxzzy.github.io/` is permanently down. Files that used to be hosted there are now in `/public/scripts/` and need their references updated across the wiki).*

---

## Media Assets

Images are now stored locally in the `/public/media/` directory:

| File | Found In | Usage |
|---|---|---|
| `krystal.png` | `/public/media/` | Krystal item sprite, used in crafting grids |
| `kyber_stance.png` | `/public/media/` | (present, not yet spotted in pages) |
| `kyber_tint.png` | `/public/media/` | Kyber Station tinting menu screenshot |
| `stances.gif` | `/public/media/` | Getting started page — stance demo GIF |
| *New Item Assets* | `/public/media/` | Many new item sprites have been added (blaster_hilt.png, crossguard.png, gauntlet.png, etc.) |

---

## Conventions & Quirks to Know

1. **History tables ordering**: Currently, many history tables are in *descending* version order (newest first). **This is NOT intended and needs to be fixed** to standard chronological order.
2. **Missing `<!DOCTYPE html>` / `<html>`**: Several misc/block pages are missing root HTML tags. This needs to be fixed across all files.
3. **Tooltip Titles**: Tooltip titles use the `data-mctitle` attribute with HTML escape — e.g. `&lt;br&gt;&lt;span class='mcTextGrey'&gt;flavour text&lt;/span&gt;`. **We need to find workarounds for this HTML escaping if possible.**
4. **Crafting grids** use the custom `.craftingui` CSS component. Items are `<div class="slot"><a class="item" draggable="false"><span class="minetext item CLASSNAME" data-mctitle="..." onclick="..."></span></a></div>`.
5. **`buffer/` is NOT the wiki** — it's the working scratchpad. Don't reference it from wiki pages.
6. **No JS frameworks, no build tools** — edits are direct HTML. Just save and push.
7. **Breadcrumbs** are manually typed in each page's `<nav>` — there's no automatic generation.
