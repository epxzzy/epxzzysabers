# Overview

epxzzy's Sabers is a Minecraft Forge mod that adds multiple saber weapon types, combat mechanics (parry, deflection, stances, flourishes), custom renderers, entities, UI screens, and kyber-related items.

- Mod ID: `epxzzysabers`
- Main class: `com.epxzzy.epxzzysabers.epxzzySabers`
- Client init: `com.epxzzy.epxzzysabers.epxzzySabersClient`

Key subsystems:
- Items: custom saber items and kyber crystals
- Blocks: `KyberStation` (configuration/stance UI)
- Entities: thrown rotary blades, plasma bolts
- Rendering: custom item and player pose renderers
- Networking: `SaberMessages` + packet handlers
- UI: custom screens and creative tabs
