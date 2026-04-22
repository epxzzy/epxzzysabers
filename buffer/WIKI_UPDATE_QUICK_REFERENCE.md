# Wiki Update Quick Reference - Version 0.0.4
**Last Updated:** April 20, 2026

## Files Complete: ✓ WIKI_UPDATE_GUIDE_v0.0.3.md (renamed to v0.0.4 content)

---

## HOW TO USE THIS GUIDE

### For Wiki Editors:
1. **Open:** [WIKI_UPDATE_GUIDE_v0.0.3.md](WIKI_UPDATE_GUIDE_v0.0.3.md) (v0.0.4 content)
2. **Follow:** Step-by-step instructions organized by feature
3. **Each section includes:**
   - Which HTML file(s) to edit
   - What content to add/change
   - Exact HTML format for History section entries
   - Version number (0.0.4) for all new entries

### Quick Navigation:
- **Animation System:** Section 1 (Combat page)
- **Model/Texture Works:** Integrated into item pages
- **Audio Changes:** Section 2 (Rotary Saber only)
- **Cooldown Balancing:** Sections 3-4
- **UI Enhancements:** Sections 5-8
- **Mechanics Fixes:** Section 9 (Combat page)
- **Gameplay Updates:** Sections 10-12
- **Compatibility:** Section 14 (NEW file)
- **Shader Support:** Section 15 (All items)
- **Index Update:** Section 16

---
16 Update Sections)

### Item Pages (All 8):
- `docs/items/blastersaber.html` (ability tooltip + shader support)
- `docs/items/crossguardsaber.html` (model, shader support)
- `docs/items/dualbladed.html` (shader support)
- `docs/items/krystal.html` (shader support)
- `docs/items/rotarysaber.html` (audio, cooldown, HUD, shader support)
- `docs/items/sabergauntlet.html` (cooldown, HUD, fixes, shader support)
- `docs/items/saberpike.html` (model, shader support)
- `docs/items/singlebladed.html` (shader support)

### Block Pages:
- `docs/blocks/kyberstation.html` ⭐ (Tinting updates, color fixes)

### Misc Pages:
- `docs/misc/combat.html` ⭐ (Animation system, mechanics, stance)
- `docs/misc/compatibility.html` NEW (BetterCombat, config, shaders)

### Root:
- `docs/index.html` (Version 0.0.4 link
- `docs/index.html` (Version link only)

---

## KEY UPDATES AT A GLANCE

| Category | Count | Impact |
|----------|-------|--------|
| Animation System (Combat) | 1 | Comprehensive overhaul |
| Model/Texture Updates | 3 | Crossguard, Pike, Blaster |
| Audio (Rotary only) | 1 | Ability sounds |
| Cooldowns | 2 | Rotary, Gauntlet |
| Ability Tooltips & HUD | 3 | Rotary, Gauntlet, Blaster |
| Kyber Tinting/Colors | 1 | Tinting screen |
| Block & Parry Mechanics | 1 | Combat page |
| Stance System (Combat) | 1 | Comprehensive |
| Shader Support | 8 | All saber items |
| Compatibility File | 1 | NEW docs/misc/compatibility.html |
| Other | 1 | Version update |

---

## HISupdated page needs a new v0.0.4

Every saber item needs a new v0.0.3 entry in History table:

```html
<section id="History">
  <h2>History</h2>
  <table class="wikitable">
    <tbody>
      <!-- EXISTING ENTRIES H4: -->
      <tr>
        <td><b>0.0.4FOR v0.0.3: -->
      <tr>
        <td><b>0.0.3</b></td>
        <td>[SPECIFIC CHANGE DESCRIPTION]</td>
      </tr>
    </tbody>
  </table>
</section>
```

---

## IMPORTANT NOTES

1. **Comprehensive Changelog:**
   - See `buffer/CHANGELOG_TEMP.md` for feature list
   - See `buffer/CHANGELOG_RECENT.md` for detailed timeline

2. **GitHub Context:**
   - Original repo: https://github.com/epxzzy/epxzzysabers
   - Use for technical details if needed

3. **Testing Tips:**
   - After updates, verify all links work
   - Check that animations/abilities descriptions match coded behavior
   - Ensure no duplicate history entries
1. **Do First:** Root index + Combat page + Create compatibility.html
2. **Do Second:** Kyber Station + Rotary + Gauntlet
3. **Do Last:** Model updates + Shader support (all items) Animation updates
   - **Do Second:** Model changes + Mechanical updates
   - **Do Last:** Technical/compatibility notes

---

## VERSION RELEASES IN WIKI MD

**September 17, 2025 - April 19, 2026**
- **v0.0.1** - Initial Release (already in wiki)
- **v0.0.2** - First Balance Pass (already in wiki)
- **v0.0.3** - Previously planned
- **v0.0.4** - RC Release (CURRENT - needs update)

---

## EDITING STRATEGY

### For Each Page:
1. Copy the instruction section number
2. Locate the HTML file in `docs/`
3. Find the section mentioned (Usage, Description, History, etc.)
4. Add/modify content as specified
5. Add v0.0.3 entry to History table
6. Validate HTML is well-formed
7. Test link functionality

### Batch Updates Possible:
- All saber animations at once (items 1-4)
- All ability tooltips at once (items 20-22)
- All history entries at once

---

## ADDITIONAL RESOURCES
4 RC → Release
- **Period Covered:** 9/17/2025 - 4/19/2026
- **Total Update Sections:** 16
- **Estimated Update Time:** 1.5-2 hours for complete wiki refresh
- **New Files:** docs/misc/compatibility.html
- **Estimated Update Time:** 2-3 hours for complete wiki refresh

---

## QUESTIONS?
 (contains v0.0.4 updates)

Each section includes:
- Specific files to edit
- Exact text to add
- HTML formatting
- Priority level

---

**Generated:** April 20, 2026 | **Target Version:** 0.0.4

