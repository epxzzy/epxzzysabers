# Epxzzy's Sabers - Release Candidate Changelog
**September 17, 2025 - April 19, 2026**

## ✨ New Features & Additions

### Animations
- **Attack Animations**: Added dynamic attack animations for light weapons, including left-handed variants
- **Block Animations**: Implemented blocking animations for all saber types, with proper left-handed support
- **Projectile Blocking Animations**: Blocking projectiles now plays dedicated animations instead of silently deflecting them
- **Flourish Animations**: Enhanced flourish animations that play during various combat scenarios
- **Stance Visuals**: Stances now display consistently with proper positioning

### UI & User Interface
- **Tint UI Redesign**: Completely redesigned the color tinting interface for easier customization
- **Stance Preference Menu**: Players can now open the stance preference menu using Shift+Stance to quickly select preferred forms
- **Item Tooltips**: Added helpful hints to item tooltips showing status information and tips about your equipped saber
- **RGB Color Toggles**: New option to toggle between RGB and other color modes in the tint menu

### Mechanics Improvements
- **Faster Tooltips**: Optimized tooltip display for snappier feedback
- **HSL Color Calculations**: Added HSL support for more accurate color representation in the tint system
- **Saber Status Hints**: Improved on-screen hints showing your saber's current status and capabilities

## 🛠️ Major Improvements & Reworks

### Saber Models
- **Crossguard Saber Rework**: Completely redesigned the crossguard saber model with improved aesthetics
- **Gauntlet Saber Model Rework**: Updated gauntlet saber design with better proportions and detail
- **Blaster Saber Updates**: New blaster saber model with enhanced visual appeal
- **Light Weapon Balance**: Improved animations and responsiveness for light weapons

### Animation System
- **Left-Handed Support**: Full left-handed animation support for all attack and block animations
- **Smooth Transitions**: Better animation transitions between different combat states
- **Stance-Specific Animations**: Each stance now has properly animated attacks, blocks, and flourishes

### Server & Compatibility
- **Dedicated Server Support**: Fixed multiple dedicated server issues for proper multiplayer functionality
- **BetterCombat Compatibility**: Added proper datapack compatibility with BetterCombat mod
- **Attribute Consistency**: Fixed attribute inconsistencies that were causing stat misalignment
- **Config Reloading**: Server configurations can now be reloaded without restart

### Parry & Combat
- **Rotary Saber Improvements**: Simplified and improved rotary saber cooldown system and deflection mechanics
- **Parry Range Fine-tuning**: Adjusted parry ranges for better balance across saber types
- **Projectile Deflection**: Enhanced projectile deflection mechanics with proper direction handling

## 🐛 Bug Fixes

### Critical Fixes
- **Stance Floating Bug**: Fixed stances that were hovering above the ground instead of aligning properly
- **Broken Recipes**: Repaired broken crafting recipes that were preventing item creation
- **Game Crashes**: Fixed critical crash issues that occurred during specific combat scenarios

### UI/Color Bugs
- **Color 0 Overwrite Fix**: Fixed the system defaulting to green and overwriting color 0 (black)
- **Tint Menu RGB Toggle**: Corrected RGB color toggle functionality in the tint menu
- **Block Animation Issues**: Resolved broken block animation rendering

### Compatibility
- **Gauntlet Ability Fix**: Patched gauntlet saber abilities for dedicated servers
- **Projectile Handling**: Fixed incorrect processing of special projectiles (shulkers, tridents)
- **Stance Synchronization**: Improved server-side synchronization of stance data

### Visual Polish
- **Animation Plotting**: Converted animation preview data into proper in-game animations
- **Rotary Sprite Issues**: Fixed visual glitches with rotary saber sprites

## 📊 Balance & Quality of Life

- **Flourish Adjustments**: Fine-tuned flourish animations and triggering conditions
- **Aimless Flourish Tweaks**: Improved animations for flourishes when not in combat
- **Offhand Mechanics**: Added compute support for mirrored left-handed offhand attacks
- **Mono Audio**: Fixed audio issues related to stereo/mono conversion

## 🎮 Core System Updates

- **Stance System Enhancements**: Players can now assume stances with any item without restrictions
- **Attack & Miss Handling**: Improved detection of attacks, misses, blocks, and parries for better animation triggering
- **Item Syncing**: Proper synchronization of saber nanotech data across server and client
- **Data-Driven Configs**: Implemented data-driven configuration system for easier balance adjustments

---

**Note**: This release includes significant polish and optimization work to deliver a smooth, responsive lightsaber combat experience across all saber types and combat situations. Many animations have been completely rewritten for improved fluidity and visual quality.
