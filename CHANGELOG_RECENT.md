# Epxzzy Sabers - Player Changelog
## September 17, 2025 - April 7, 2026

### Late March - April 2026: Major Overhaul Wave

**Gameplay Mechanics:**
- All attack animations completely rebuilt with new motion-plotting system for smoother, more natural movements
- Left-handed (offhand) saber animations now fully mirrored and computed correctly
- Flourish attacks now have proper "passthrough" - blocking doesn't interrupt flourishes unnecessarily

**Model Improvements:**
- **Crossguard Saber**: Complete visual rework with new textures and proportions
- **Gauntlet Saber (Katar)**: Completely redesigned model with improved visuals and new textures
- **Blaster Saber**: Updated model by Marm with refined details
- **Saber Pike**: Model improvements (still WIP)

**Cooldown & Duration Changes:**
- Rotary Saber cooldown system simplified and rebalanced
- Gauntlet Saber ability duration and cooldown adjustments
- On-screen cooldown indicators now animate smoothly

**Audio:**
- All sound effects converted from stereo to mono for consistency
- Includes: activation, deactivation, swing, and clash sounds

**UI Enhancements:**
- Redesigned stance preference menu with pentagon-shaped icons
- New background tints for visual polish
- Attack and block hint icons repositioned and restyled
- Animated cooldown displays for Gauntlet and Rotary sabers

**Compatibility:**
- Added full BetterCombat compatibility datapack
- Dedicated server support patched and improved
- Fixed various data syncing issues on multiplayer servers

### Mid-March 2026: UI & Animation Foundation

**Major UI Work:**
- Stance selection screen completely rebuilt with new pentagon-based button design
- New rotatable star/pentagon interface for stance cycling
- Mouse wheel, keyboard, and mouse button support for stance cycling
- Stance preference menu with styled icons (7 different stances)
- Icon sizing and visual polish pass

**Animation System:**
- New BlockBench animation environment set up
- Smooth saber hint animations for visual feedback
- Animation data now synced via network packets (more multiplayer friendly)

**Bug Fixes:**
- Stack empty errors after chunk reloading
- Stance screen synchronization issues
- Data syncing improvements for dedicated servers

### Early March 2026: Core Systems

**New Features:**
- Shift+Stance keybind now opens preference menu (closes issue #21)
- Can now enter stance with any item in hand (not just sabers)
- **Data-driven configs system**: Weapon attributes now customizable through config files

**Saber Mechanics:**
- **Rotary Saber**: Major overhaul with better deflection mechanics (now actually fun!)
- Projectile range and decay adjustments
- Parry range fine-tuning
- Rotary flight mode improvements and bug fixes

**Shader Support:**
- Partial shader support added for special effects
- Cleanup and optimization of shader integration

### February 2026: Foundation & Fixes

**Animation & Multiplayer:**
- Animation NBT data now synced via packets instead of entity NBT
- Player gauntlet data syncing for proper multiplayer detection
- Network packet rework for better data handling
- Various server-side synchronization fixes

**Saber Additions:**
- Saber chucks as new weapon variant
- Hilt and blade models in BlockBench format

**Polish:**
- Manual wiki updates and documentation
- Code refactoring and optimization
- Removed old/unused code residue

---

**Summary**: This period focused on major animation system overhaul, comprehensive model reworks, UI redesign with the new pentagon-based interface, improved multiplayer support, and audio standardization. The mod now has smoother combat animations, better visual feedback, and more stable dedicated server performance.
