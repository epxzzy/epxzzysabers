# Wiki Update Guide for Version 0.0.4
**Date Generated:** April 20, 2026
**Target Version:** 0.0.4
**Scope:** September 17, 2025 - April 19, 2026

---

## Overview
This guide provides step-by-step instructions to update the epxzzysabers wiki based on changes documented in CHANGELOG_TEMP.md. Each entry specifies which wiki page(s) need updating, what changes to add, and the format for history sections. The structure of this guide matches the release changelog categories.

---

## 1. REQUESTED

### 1.1 Shader Support
**Files to Update:** All 8 saber item pages (`blastersaber.html`, `crossguardsaber.html`, `dualbladed.html`, `krystal.html`, `rotarysaber.html`, `sabergauntlet.html`, `saberpike.html`, `singlebladed.html`), `docs/misc/compatibility.html`
**What to Add:** 
- **Item Pages:** Add brief note about external shader support. Highlight that the Rotary Saber entity also supports external shaders.
- **Compatibility Page:** Document that shader support has been tested with the Oculus mod and the following popular shaderpacks: Photon, Complementary Unbound, Complementary Reimagined, Rethinking Voxels (Beta), and BSL.
**History Section Update (v0.0.4):**
```html
<tr>
  <td><b>0.0.4</b></td>
  <td>Added external shader support for saber rendering and rotary saber entity. Tested with Oculus and popular shaderpacks.</td>
</tr>
```

### 1.2 BetterCombat Compatibility
**File to Update:** `docs/misc/compatibility.html` (Link from `misc/index.html`)
**What to Add:** 
- Note that BetterCombat falsely tagging sabers is fixed.
- Document the Extraneous BetterCombat compatibility via datapack.
- Document the new data-driven config system (live reload supported) and link to docs.
**History Section Update (v0.0.4) for compatibility page:**
```html
<tr>
  <td><b>0.0.4</b></td>
  <td>Fixed BetterCombat compatibility. Added extraneous BetterCombat datapack and data-driven config system.</td>
</tr>
```

---

## 2. KYBERTABLE & HUD

### 2.1 Kyber Station Updates
**File to Update:** `docs/blocks/kyberstation.html`
**What to Add / Update:** 
- Note that Kyber Station Tinting screen visuals have been updated (UI redesign).
- Note the removal of the Kyber Station Stance Menu & Screen.
- Note bug fixes for HSL color calculation (Issue #31) and the RGB toggle bug in the tint menu.
**History Section Update (v0.0.4):**
```html
<tr>
  <td><b>0.0.4</b></td>
  <td>Updated tinting visuals (UI redesign), removed stance menu, and fixed HSL (Issue #31)/RGB calculation bugs.</td>
</tr>
```

### 2.2 HUD Hints & Stance Menu
**Files to Update:** `docs/items/sabergauntlet.html`, `docs/items/rotarysaber.html`, `docs/misc/combat.html`
**What to Add:**
- **Gauntlet & Rotary pages:** Add notes about new saber ability HUD hints. Explain that HUD hints are small sprites shown near the crosshair updating with the equipped saber status (e.g., cooldown, duration, ready to use).
- **Combat page:** Document the new stance preference menu accessible via Shift+Stance keybind.
**History Section Update (v0.0.4):**
```html
<tr>
  <td><b>0.0.4</b></td>
  <td>Added stance preference menu (Shift+Stance) and crosshair HUD hints for Gauntlet & Rotary sabers.</td>
</tr>
```

---

## 3. VISUALS

### 3.1 Animation Overhaul
**File to Update:** `docs/misc/combat.html`
**What to Add:** Document proper left-handed animations for attack, block, and flourish (light weapons). Clarify that projectile blocking will now actually play the mod's blocking animation instead of Minecraft's default blocking animation.
**History Section Update (v0.0.4):**
```html
<tr>
  <td><b>0.0.4</b></td>
  <td>Added proper left-handed attack, block, and flourish animations. Projectile blocking now uses the mod's block animation.</td>
</tr>
```

### 3.2 Model & Texture Reworks
**Files to Update:** 
- `docs/items/crossguardsaber.html` (Reworked by Dead_Comedian)
- `docs/items/sabergauntlet.html` (Reworked by Dead_Comedian)
- `docs/items/blastersaber.html` (Reworked by Marmitex)
- `docs/items/saberpike.html` (Reworked by epxzzy)
- `docs/items/blastersaber.html` (Regarding Plasma Bolt projectile model and texture rework)
**What to Add:** Update Descriptions to mention the reworked models/textures and credit the creators. Add info about the Plasma Bolt projectile model/texture rework.
**History Section Update (v0.0.4):**
```html
<tr>
  <td><b>0.0.4</b></td>
  <td>Model and texture reworks (including Plasma Bolt).</td>
</tr>
```

### 3.3 Color Fixes & Hanging Block Indicator
**Files to Update:** `docs/blocks/kyberstation.html`, All saber item pages
**What to Add:**
- **Kyber Station:** Document fix for black color (#000000) being overwritten with default color (#00FF00).
- **Saber items (Blocking section):** Add info about the new visual indicator for hanging saber block.

---

## 4. FUNCTIONALITY

### 4.1 Ability Tooltips
**Files to Update:** `docs/items/blastersaber.html`, `docs/items/sabergauntlet.html`, `docs/items/rotarysaber.html`
**What to Add:** Document new Shift+hover ability tooltips in the inventory.
**History Section Update (v0.0.4):**
```html
<tr>
  <td><b>0.0.4</b></td>
  <td>Added ability tooltips (Shift+hover in inventory).</td>
</tr>
```

### 4.2 Gameplay Tuning & Mechanics
**Files to Update:** All saber item pages, `docs/misc/combat.html`, `docs/items/rotarysaber.html`
**What to Add:**
- **Combat / Sabers:** Document the removal of critical hits for sabers, trident parrying fixes, and changes to hanging saber block duration.
- **Rotary Saber:** Document that the parry range was fine-tuned to a value more suited for the weapon.

### 4.3 Bug Fixes & Synchronization
**Files to Update:** `docs/items/sabergauntlet.html`, `docs/items/rotarysaber.html`
**What to Add:**
- **Gauntlet:** Document server crash fix, gauntlet ability dedicated server functionality fix, and client/server synchronization fix.
- **Rotary:** Document rotary flight cooldown and duration synchronization fix.

---

## 5. MISCELLANEOUS

### 5.1 General Changes & Fixes
**Files to Update:** `docs/misc/getting_started.html`, `docs/items/rotarysaber.html`, `docs/items/krystal.html`
*(Note: `getting_started.html` is a relatively newer draft of the v0.0.3 `combat.html`, so it will now include these miscellaneous updates and replace the older combat page details for getting started.)*
**What to Add:** 
- **Getting Started Page:** 
  - Audio files converted from stereo to mono.
  - Added ability to stance with any item.
  - Fixed server-side packet synchronization.
  - Fixed broken recipe unlocks.
- **Krystal Page:** Changed Krystal recipe (lapis replaced with quartz).
- **Rotary Saber:** Fixed rotary saber (entity) issues after chunk reload.
- General note for various bugs and crashes fixed.

---

## 6. ROOT INDEX PAGE UPDATE
**File to Update:** `docs/index.html`
**What to Update:** Update the Latest Version link to point to 0.0.4.

---

## SUMMARY BY PAGE

| Page | Updates Needed |
|------|----------------|
| **All Saber Pages** | Shader support, hanging block indicator/duration, critical hits removed, trident parry fix |
| **Rotary Saber** | HUD hints, tooltips, fine-tuned parry range, cooldown/duration sync fix, chunk reload fix |
| **Saber Gauntlet** | HUD hints, tooltips, model rework, server crash fix, dedicated server fix, sync fix |
| **Blaster Saber** | Model rework, tooltips, Plasma Bolt rework note |
| **Crossguard Saber**| Model rework |
| **Saber Pike** | Model rework (by epxzzy) |
| **Krystal** | Recipe change (lapis to quartz) |
| **Kyber Station** | Tinting screen UI redesign, removed stance menu, HSL (Issue #31)/RGB bug fixes, black color fix |
| **Combat Page** | Left-hand animations, proper projectile block animation, Shift+Stance menu |
| **Getting Started** | Replaces old combat info; ability to stance with any item, audio mono conversion, recipe unlock fixes, packet sync fixes |
| **Compatibility** | NEW file for BetterCombat, Data-Driven config, and Oculus/Shaderpacks testing info |
| **Root Index** | Version number update to 0.0.4 |

---

## VALIDATION CHECKLIST
- [ ] Root index points to v0.0.4
- [ ] New `compatibility.html` page created and linked
- [ ] Kyber Station page updated with visual changes and fixes
- [ ] Combat page updated with animations, projectile blocking, and stance changes
- [ ] Getting Started page populated with global mechanics and replaces old combat info where applicable
- [ ] Saber Gauntlet, Rotary, Blaster, Crossguard, Pike, and Krystal pages updated
- [ ] All saber pages updated with shader support and global mechanic changes
- [ ] History sections added (v0.0.4) for updated pages

---
**END OF GUIDE**
