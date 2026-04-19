# Epxzzy Saber Mod - RC Changelog
**September 17, 2025 - April 19, 2026**

## KyberTable (UI & Configuration)
- Updated Kyber Station Tinting screen
- Added stance preference menu (Shift+Stance keybind)
- Removed Kyber Station Stance Menu & Screen
- Added animated ability hint displays for Gauntlet & Rotary sabers
- Fixed RGB toggle bug in tint menu

## Sabers
- Added light weapon attack animations with left-handed variants
- Added block animations with left-handed mirror variants
- Added mirrored left-handed flourish animations
- Fixed projectile blocking to use light weapon block animation

### Rotary
- Simplified cooldown and duration mechanics
- Fixed client/server synchronization
- Fixed sprite rendering bug
- Added proper left-handed animation states
- Updated cooldown UI texture
- Added ability tooltip (Shift+hover in inventory)

### Gauntlet
- Reworked gauntlet model
- Added new gauntlet texture
- Added animated cooldown display
- Fixed dedicated server compatibility
- Added ability tooltip (Shift+hover in inventory)

### Crossguard
- Reworked crossguard saber model
- Added new crossguard texture

### Blaster
- Added new blaster saber model by Marm
- Added new blaster saber texture
- Added ability tooltip (Shift+hover in inventory)

## Requested Features
### Shader Support
- Added shader support with custom render types
- Fixed rotary saber issues with shader support
- Updated all saber item renderers for shader compatibility

### Config System
- Added data-driven config system (with reload support)
- Added BetterCombat compatibility datapack (available if weapon tagging is required)

## Miscellaneous
- Fixed black color being overwritten with green
- Fixed HSL color calculation
- Fixed sound files (converted from stereo to mono)
- Fixed server-side packet synchronization
- Fixed gauntlet ability on dedicated servers
- Disabled critical hits for sabers
- Added ability to stance with any item
- Fixed trident parrying
- Fixed various crashes
- Fixed item stack issues after chunk reload
- Fine-tuned rotary parry range
- Reorganized saber tags (separated config tags from weapon tags)
