# epxzzysabers - RC Changelog

**September 17, 2025 - April 19, 2026**

## Requested
### Shader Support
- Updated saber renderers to use external shaders. 
- Updated rotary saber(entity) to use external shaders. 

### BetterCombat Compatibility
- Fixed BetterCombat falsely tagging sabers.
- Added Extraneous BetterCombat compatibility via datapack (provide link)
- Added data-driven config system (live reload supported) (provide docs link)

## KyberTable & HUD
- Updated Kyber Station Tinting screen visuals (UI redesign).
- Removed Kyber Station Stance Menu & Screen.
- Fixed HSL colour calculation being broken in some cases (link issue #31).
- Fixed RGB toggle bug in tint menu.
- Added stance preference menu (Shift+Stance keybind)
- Added saber ability HUD hints for Gauntlet & Rotary sabers

## Visuals
- Added proper left-handed attack animations (light weapon).
- Added proper left-handed block animations (light weapon). 
- Added proper left-handed flourish animations (light weapon). 
- Fixed projectile block not getting animated (light weapon).
- Reworked Crossguard Saber model & texture (Dead_Comedian).
- Reworked Saber Gauntlet model & texture (Dead_Comedian).
- Reworked Blaster Saber model & texture (Marmitex). 
- Reworked Saber Pike model & texture (Marmitex). 
- Reworked Plasma Bolt projectile model and texture.
- Fixed black colour (#000000) being overwritten with default colour (#00FF00) 
- Added a visual indicator for hanging saber block. (link issue, #1 projectile blocking)

## Functionality
- Added ability tooltip (Shift+hover in inventory) for Blaster Saber, Saber Gauntlet and Rotary Saber.
- Fixed a server crash relating to Saber Gauntlet.
- Fixed Rotary flight cooldown and duration synchronisation.
- Fixed Gauntlet client/server synchronisation.
- Fixed trident parrying.
- Fixed gauntlet ability on dedicated servers.
- Fine-tuned rotary parry range.
- Removed critical hits for sabers.
- Changed hanging saber block duration. (link issue, #1 projectile blocking)

## Miscellaneous
- Fixed broken recipe unlocks.
- Changed krystal recipe (lapis replaced with quartz).
- Fixed sound files (converted from stereo to mono).
- Fixed server-side packet synchronisation.
- Added ability to stance with any item.
- Fixed various bugs and crashes.
- Fixed rotary saber(entity) issues after chunk reload.
