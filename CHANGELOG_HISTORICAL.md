# Epxzzy Sabers - Historical Changelog

## Historical Release (Project Inception to September 17, 2025)

### Items
- **Krystal**: Renewable crafting component used in all saber recipes, stackable to 64.
- **Single Bladed Saber**: Standard lightsaber balanced for versatile melee combat.
- **Dual Bladed Saber**: High-parry weapon that excels at ranged deflection and melee defense.
- **Rotary Saber**: Unique spinning blade with boomerang throw for ranged attacks and temporary aerial flight for mobility.
- **Crossguard Saber**: Sith-inspired design, weaker in raw power but with identical mechanics to single-bladed for thematic play.
- **Blaster Saber**: Hybrid weapon with strong melee capabilities and weak integrated blaster for limited ranged use.
- **Saber Pike**: Polearm-style weapon with planned unique combat system for heavy weapons (currently WIP).
- **Saber Gauntlet**: Hardcore melee gauntlet with disruption for stunning and supercharge for burst damage.

### Blocks
- **Kyber Station**: Color customization station with spacious UI, tab system, tab button rendering, and positioning; converted to regular block with menu functionality

### Mechanics
- **Combat System**: Lightsaber stances allowing players to switch between combat styles (Shi-Cho, Makashi, Soresu, Ataru, Shien/Djem So, Niman, Juyo/Vaapad), with stance mode immobilizing movement for focused combat
- **Attack & Block**: Full 8-directional attack and block animations, directional blocking, saber-to-saber blocking, smooth transitions, block interpolation, and synchronization across multiplayer
- **Flourish System**: Single-bladed and dual-bladed flourish animations with variants, double-click activation, and multiplayer synchronization
- **Gauntlet Abilities**: Basic gauntlet abilities (prototype phase)
- **Parry & Defense**: Parry system for deflecting projectiles, blocking mechanics for melee and timed projectile defense, blade clash sounds and effects

### Audio & Visual
- **Audio**: Blade clash sounds, additional sound effects, rotary saber sound adjustments, and language file updates
- **Visual**: Custom rendered model support, stance visual tweaks, graphics optimizations, animation system with sequential notation, prototype animations, and server-client synchronization

### Multiplayer & Technical
- **Synchronization**: Attack, block, and stance synchronization, custom attack tracker, desynchronization fixes, proper random number generation for deterministic behavior, and network packet handling
- **Framework**: Mixin-based combat system, attack tracker system, expanded saber capabilities, code organization, and stability improvements

### Misc
- **Balance & Polish**: Final balance tweaks for rotary saber, flourish system, and stance mechanics; crafting recipes fixed and documented
- **Documentation**: Wiki updates, images, and documentation additions

### Technical Details
- **Attacking**: Randomized 8-directional attacks on hit; deterministic control methods in development; animations for applicable sabers
- **Blocking**: Right-click to block saber attacks and projectiles (up to 50 ticks); automatic synchronization for proper blocking
- **Parrying**: Arm swings deflect projectiles within parry range (saber-type dependent); conditional animations based on player state (default cross, ground-facing spin, sprinting spin)
- **Posing (Stances)**: Crouch while blocking to assume stance; 8 stances available via kyber station, each with planned unique attack/block animations for light and heavy weapons
